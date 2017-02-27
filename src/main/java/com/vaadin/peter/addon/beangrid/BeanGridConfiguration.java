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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;

import com.vaadin.peter.addon.beangrid.valueprovider.BeanGridValueProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

/**
 * BeanGridConfiguration defines Vaadin's Grid component as Spring Bean with
 * ability to configure it on runtime with provided ITEM generic type.
 * 
 * @author Peter / Vaadin
 */
@Configuration
public class BeanGridConfiguration implements ApplicationContextAware {

	private ApplicationContext appContext;

	private static Logger logger = LoggerFactory.getLogger(BeanGridConfiguration.class);

	@Bean
	@Scope(scopeName = "prototype")
	@SuppressWarnings("unchecked")
	public <ITEM> Grid<ITEM> configureBeanGrid(DependencyDescriptor dependencyDescriptor) {
		logger.debug("Configuring Vaadin Grid as bean");

		long timestamp = System.currentTimeMillis();

		ResolvableType injectionPointType = dependencyDescriptor.getResolvableType();
		if (!injectionPointType.hasGenerics()) {
			logger.error("Grid injection point does not declare generic type, aborting");
			throw new IllegalStateException("Grid injection point is expected to declare a static item type");
		}

		ResolvableType genericType = injectionPointType.getGeneric();
		Class<ITEM> itemType = (Class<ITEM>) genericType.resolve();

		logger.debug("Vaadin Grid will use " + itemType.getCanonicalName() + " as item type");

		Grid<ITEM> grid = configureGridInstance(itemType);
		long configTime = System.currentTimeMillis() - timestamp;
		logger.debug("Done configuring Grid for " + itemType.getName() + " in " + configTime + "ms");

		return grid;
	}

	private <ITEM> Grid<ITEM> configureGridInstance(Class<ITEM> itemType) {
		try {
			List<ColumnDefinition> columnDefinitions = discoverColumnDefinitions(itemType);
			Grid<ITEM> beanGrid = new Grid<ITEM>();

			columnDefinitions.forEach(definition -> {
				Column<ITEM, String> column = beanGrid.addColumn(item -> provideColumnValue(definition, item));
				column.setCaption(definition.getTranslationKey());
			});

			return beanGrid;
		} catch (Exception e) {
			throw new RuntimeException("Failed to configure Vaadin Grid for type " + itemType.getCanonicalName(), e);
		}
	}

	private static List<ColumnDefinition> discoverColumnDefinitions(Class<?> itemType) throws IntrospectionException {
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

	private static List<ColumnDefinition> discoverFieldsWithGridColumnAnnotations(
			List<PropertyDescriptor> propertyDescriptors, Class<?> itemType) {
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

	private static List<Field> getDeclaredFields(Class<?> itemType) {
		List<Field> fields = new ArrayList<>(Arrays.asList(itemType.getDeclaredFields()));

		if (itemType.getSuperclass() != null) {
			fields.addAll(getDeclaredFields(itemType.getSuperclass()));
		}

		return fields;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <ITEM> String provideColumnValue(ColumnDefinition definition, ITEM item) {
		ResolvableType textualValueProviderType = ResolvableType.forClassWithGenerics(BeanGridValueProvider.class,
				definition.getType());
		List<String> valueProviderNames = Arrays.asList(appContext.getBeanNamesForType(textualValueProviderType));

		if (!valueProviderNames.isEmpty()) {
			BeanGridValueProvider valueProviderInstance = appContext.getBean(valueProviderNames.iterator().next(),
					BeanGridValueProvider.class);

			return valueProviderInstance.apply(item);
		}

		return "no-provider";
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext = appContext;
	}
}
