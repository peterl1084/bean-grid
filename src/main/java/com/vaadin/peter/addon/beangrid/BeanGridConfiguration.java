package com.vaadin.peter.addon.beangrid;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;
import org.springframework.util.StringUtils;

import com.vaadin.peter.addon.beangrid.valueprovider.BeanGridTextualValueProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

@Configuration
public class BeanGridConfiguration implements ApplicationContextAware {

	private ApplicationContext appContext;

	@Bean
	@Scope(scopeName = "prototype")
	@SuppressWarnings("unchecked")
	public <ITEM> Grid<ITEM> configureBeanGrid(DependencyDescriptor dependencyDescriptor) {
		ResolvableType injectionPointType = dependencyDescriptor.getResolvableType();
		if (!injectionPointType.hasGenerics()) {
			throw new IllegalStateException("Grid injection point is expected to declare a static item type");
		}

		ResolvableType genericType = injectionPointType.getGeneric();
		Class<ITEM> itemType = (Class<ITEM>) genericType.resolve();

		return configureGridInstance(itemType);
	}

	private <ITEM> Grid<ITEM> configureGridInstance(Class<ITEM> itemType) {
		List<ColumnDefinition> columnDefinitions = discoverColumnDefinitions(itemType);
		Grid<ITEM> beanGrid = new Grid<ITEM>();

		columnDefinitions.forEach(definition -> {
			Column<ITEM, String> column = beanGrid.addColumn(item -> provideColumnValue(definition, item));
			column.setCaption(definition.getTranslationKey());
		});

		return beanGrid;
	}

	private <ITEM> String provideColumnValue(ColumnDefinition definition, ITEM item) {

		ResolvableType textualValueProviderType = ResolvableType
				.forClassWithGenerics(BeanGridTextualValueProvider.class, definition.getType());
		List<String> valueProviderNames = Arrays.asList(appContext.getBeanNamesForType(textualValueProviderType));

		if (!valueProviderNames.isEmpty()) {
			BeanGridTextualValueProvider valueProviderInstance = appContext
					.getBean(valueProviderNames.iterator().next(), BeanGridTextualValueProvider.class);

			return valueProviderInstance.provideTextualValue(item);
		}

		return "no-provider";
	}

	private List<ColumnDefinition> discoverColumnDefinitions(Class<?> itemType) {
		itemType = Objects.requireNonNull(itemType);

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(itemType);

			return Arrays.asList(beanInfo.getPropertyDescriptors()).stream().map(descriptor -> {
				List<GridColumn> definitions = Arrays
						.asList(descriptor.getReadMethod().getAnnotationsByType(GridColumn.class));
				if (definitions.size() == 1) {
					return new ColumnDefinition(definitions.iterator().next(), descriptor);
				}

				return null;
			}).filter(Objects::nonNull).sorted().collect(Collectors.toList());

		} catch (IntrospectionException e) {
			throw new RuntimeException("Failed to introspect bean of type " + itemType.getCanonicalName(), e);
		}
	}

	private static class ColumnDefinition implements Comparable<ColumnDefinition> {

		private GridColumn columnDefinitionAnnotation;
		private PropertyDescriptor descriptor;

		public ColumnDefinition(GridColumn columnDefinitionAnnotation, PropertyDescriptor descriptor) {
			this.columnDefinitionAnnotation = Objects.requireNonNull(columnDefinitionAnnotation);
			this.descriptor = Objects.requireNonNull(descriptor);
		}

		public String getPropertyName() {
			if (StringUtils.hasText(columnDefinitionAnnotation.propertyName())) {
				return columnDefinitionAnnotation.propertyName();
			}

			return descriptor.getName();
		}

		public Class<?> getType() {
			return descriptor.getPropertyType();
		}

		public String getTranslationKey() {
			return columnDefinitionAnnotation.translationKey();
		}

		public int getDefaultOrderNumber() {
			return columnDefinitionAnnotation.defaultOrder();
		}

		public boolean isDefaultVisible() {
			return columnDefinitionAnnotation.defaultVisible();
		}

		@Override
		public String toString() {
			return "property: " + getPropertyName() + ", key: " + getTranslationKey() + ", default order: "
					+ getDefaultOrderNumber() + " default visible: " + isDefaultVisible();
		}

		@Override
		public int compareTo(ColumnDefinition other) {
			return this.getDefaultOrderNumber() - other.getDefaultOrderNumber();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext = appContext;
	}
}
