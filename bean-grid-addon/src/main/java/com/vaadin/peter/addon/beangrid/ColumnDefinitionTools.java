package com.vaadin.peter.addon.beangrid;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ColumnDefinitionTools is used to build {@link ColumnDefinition}s from any
 * bean having {@link GridColumn} annotations placed on fields or methods.
 * 
 * @author Peter / Vaadin
 */
public class ColumnDefinitionTools {
	private static Logger logger = LoggerFactory.getLogger(ColumnDefinitionTools.class);

	/**
	 * Finds {@link GridColumn} definitions from given itemType.
	 * 
	 * @param itemType
	 * @return List of {@link ColumnDefinition}s describing the
	 *         {@link GridColumn}s on given itemType. The order of the returned
	 *         list will be the order as defined in the {@link GridColumn}
	 *         annotation's order.
	 * @throws ColumnDefinitionException
	 *             if introspection of given itemType fails or if there are
	 *             misconfigurations with {@link GridColumn} definitions.
	 */
	public static List<ColumnDefinition> discoverColumnDefinitions(Class<?> itemType) {
		logger.debug("Introspecting " + itemType.getCanonicalName());

		BeanInfo beanInfo = null;

		try {
			beanInfo = Introspector.getBeanInfo(Objects.requireNonNull(itemType));
		} catch (IntrospectionException e) {
			throw new ColumnDefinitionException(
					"Failed to introspect bean of type " + itemType.getCanonicalName() + ".", e);
		}

		if (beanInfo == null) {
			throw new ColumnDefinitionException(
					"No introspection information available for " + itemType.getCanonicalName() + ".");
		}

		List<ColumnDefinition> columnDefinitions = new ArrayList<>();
		List<PropertyDescriptor> propertyDescriptors = Arrays.asList(beanInfo.getPropertyDescriptors());
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			Method readMethod = descriptor.getReadMethod();
			if (readMethod != null) {
				Optional.ofNullable(readMethod.getAnnotation(GridColumn.class)).ifPresent(columnDefinition -> {
					ColumnDefinition definition = new ColumnDefinition(columnDefinition,
							readMethod.getAnnotation(EditableColumn.class), descriptor);
					logger.debug("Found column definition from read method: " + descriptor.getReadMethod().getName()
							+ " with: " + definition);
					columnDefinitions.add(definition);
				});
			}
		}

		columnDefinitions.addAll(discoverFieldsWithGridColumnAnnotations(itemType, propertyDescriptors));
		Collections.sort(columnDefinitions);

		return columnDefinitions;
	}

	static List<ColumnDefinition> discoverFieldsWithGridColumnAnnotations(Class<?> itemType,
			List<PropertyDescriptor> propertyDescriptors) {
		List<Field> allDeclaredFields = getDeclaredFields(itemType);

		List<ColumnDefinition> fieldBasedColumnDefinitions = new ArrayList<>();
		allDeclaredFields.stream().forEach(field -> {
			Optional.ofNullable(field.getAnnotation(GridColumn.class)).ifPresent(columnDefinition -> {
				String fieldName = field.getName();
				Optional<PropertyDescriptor> descriptorMatchingField = propertyDescriptors.stream()
						.filter(descriptor -> descriptor.getName().equals(fieldName)).findAny();

				if (descriptorMatchingField.isPresent()) {
					PropertyDescriptor propertyDescriptor = descriptorMatchingField.get();
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null) {
						if (readMethod.getAnnotation(GridColumn.class) != null) {
							throw new ColumnDefinitionException("Found @GridColumn annotation from " + fieldName
									+ " and " + propertyDescriptor.getReadMethod().getName()
									+ ". The annotation should only be defined in either one.");
						} else {
							fieldBasedColumnDefinitions.add(new ColumnDefinition(columnDefinition,
									field.getAnnotation(EditableColumn.class), propertyDescriptor));
						}
					} else {
						throw new ColumnDefinitionException("Found @GridColumn annotation from '" + fieldName + "' in "
								+ itemType.getCanonicalName()
								+ " and corresponding write method (setter) but not corresponding read method (getter), please add getter method for the property.");
					}
				} else {
					throw new ColumnDefinitionException("Found @GridColumn annotation from '" + fieldName + "' in "
							+ itemType.getCanonicalName()
							+ " but not corresponding read method (getter), please add getter method for the property.");
				}
			});
		});

		return fieldBasedColumnDefinitions;
	}

	static List<Field> getDeclaredFields(Class<?> itemType) {
		List<Field> fields = new ArrayList<>(Arrays.asList(itemType.getDeclaredFields()));

		if (itemType.getSuperclass() != null) {
			fields.addAll(getDeclaredFields(itemType.getSuperclass()));
		}

		return fields;
	}
}
