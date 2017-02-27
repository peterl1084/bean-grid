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
	 * @throws IntrospectionException
	 */
	public static List<ColumnDefinition> discoverColumnDefinitions(Class<?> itemType) throws IntrospectionException {
		logger.debug("Introspecting " + itemType.getCanonicalName());
		BeanInfo beanInfo = Introspector.getBeanInfo(Objects.requireNonNull(itemType));

		if (beanInfo == null) {
			logger.warn("No introspection information available, not detecting Grid columns for "
					+ itemType.getCanonicalName());
			return Collections.emptyList();
		}

		List<ColumnDefinition> columnDefinitions = new ArrayList<>();
		List<PropertyDescriptor> propertyDescriptors = Arrays.asList(beanInfo.getPropertyDescriptors());
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			Method readMethod = descriptor.getReadMethod();
			if (readMethod == null) {
				logger.warn("Did not find read method for property descriptor of " + descriptor.getName() + " from "
						+ itemType.getCanonicalName());
			} else {
				Optional.ofNullable(readMethod.getAnnotation(GridColumn.class)).ifPresent(columnDefinition -> {
					ColumnDefinition definition = new ColumnDefinition(columnDefinition, descriptor);
					logger.debug("Found column definition from getter method: " + descriptor.getReadMethod().getName()
							+ " with: " + definition);
					columnDefinitions.add(definition);
				});
			}
		}

		columnDefinitions.addAll(discoverFieldsWithGridColumnAnnotations(propertyDescriptors, itemType));
		Collections.sort(columnDefinitions);

		return columnDefinitions;
	}

	static List<ColumnDefinition> discoverFieldsWithGridColumnAnnotations(List<PropertyDescriptor> propertyDescriptors,
			Class<?> itemType) {
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
							logger.warn(itemType.getCanonicalName() + " has property " + fieldName
									+ " with @GridColumn annotation that specifies a read method "
									+ readMethod.getName()
									+ " having also @GridColumn annotation defined. Only the field OR the method should have the annotation, not both.");
						} else {
							fieldBasedColumnDefinitions.add(new ColumnDefinition(columnDefinition, propertyDescriptor));
						}
					} else {
						logger.warn("Did not find read method for property descriptor of " + fieldName + " from "
								+ itemType.getCanonicalName());
					}
				} else {
					logger.warn("Did not find property descriptor for property " + fieldName + " from "
							+ itemType.getCanonicalName());
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
