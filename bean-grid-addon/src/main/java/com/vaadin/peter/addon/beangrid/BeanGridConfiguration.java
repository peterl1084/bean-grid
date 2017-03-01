package com.vaadin.peter.addon.beangrid;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.Binder.BindingBuilder;
import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.editorprovider.BeanGridEditorComponentProvider;
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
@ComponentScan(basePackageClasses = { BeanGridEditorComponentProvider.class, BeanGridValueProvider.class })
public class BeanGridConfiguration implements ApplicationContextAware {

	private ApplicationContext appContext;

	private static Logger logger = LoggerFactory.getLogger(BeanGridConfiguration.class);

	@Autowired(required = false)
	private I18NProvider i18NProvider;

	@Bean
	@Scope(scopeName = "prototype")
	@SuppressWarnings("unchecked")
	public <ITEM> Grid<ITEM> configureBeanGrid(DependencyDescriptor dependencyDescriptor) {
		logger.debug("Configuring Vaadin Grid as bean");

		long timestamp = System.currentTimeMillis();

		ResolvableType injectionPointType = dependencyDescriptor.getResolvableType();
		if (!injectionPointType.hasGenerics()) {
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

	@SuppressWarnings("unchecked")
	private <ITEM> Grid<ITEM> configureGridInstance(Class<ITEM> itemType) {
		try {

			List<ColumnDefinition> columnDefinitions = ColumnDefinitionTools.discoverColumnDefinitions(itemType);
			Grid<ITEM> grid = new Grid<>();
			grid.getEditor().setBinder(new BeanValidationBinder<>(itemType));

			columnDefinitions.forEach(definition -> {
				Column<ITEM, Object> column = grid.addColumn(item -> provideColumnValue(definition, item));

				if (i18NProvider != null) {
					column.setCaption(i18NProvider.provideTranslation(definition.getTranslationKey()));
				}

				if (definition.isDefaultHidable()) {
					column.setHidable(definition.isDefaultHidable());
					column.setHidden(!definition.isDefaultVisible());
				}

				if (definition.isEditable()) {
					ResolvableType editorProviderType = ResolvableType
							.forClassWithGenerics(BeanGridEditorComponentProvider.class, definition.getType());

					List<String> editorProviderNames = Arrays
							.asList(appContext.getBeanNamesForType(editorProviderType));

					if (editorProviderNames.isEmpty()) {
						throw new ColumnDefinitionException("Could not find editor component provider for "
								+ definition.getType().getSimpleName() + ", please implement appropriate "
								+ BeanGridEditorComponentProvider.class.getSimpleName() + " as Spring bean.");
					}

					if (editorProviderNames.size() > 1) {
						throw new ColumnDefinitionException(
								"More than one component provider exists for " + definition.getType().getSimpleName()
										+ ": " + editorProviderNames.stream().collect(Collectors.joining(", ")));
					}

					BeanGridEditorComponentProvider<Object> editorProvider = appContext
							.getBean(editorProviderNames.iterator().next(), BeanGridEditorComponentProvider.class);

					HasValue<?> editorComponent = editorProvider.provideEditorComponent(definition);

					BindingBuilder<ITEM, Object> bindingBuilder = (BindingBuilder<ITEM, Object>) grid.getEditor()
							.getBinder().forField(editorComponent);

					bindingBuilder = bindingBuilder.withNullRepresentation(editorComponent.getEmptyValue());

					if (editorProvider.requiresConversion()) {
						Converter<Object, Object> converter = (Converter<Object, Object>) editorProvider.asConvertable()
								.getConverter();
						bindingBuilder = bindingBuilder.withConverter(converter);
					}

					Binding<ITEM, ?> binding = bindingBuilder.bind((item) -> {
						return invokeRead(definition.getReadMethod(), item);
					}, (item, value) -> {
						invokeWrite(definition, item, value);
					});

					column.setEditorBinding(binding);
					column.setEditable(true);
				}
			});

			return grid;
		} catch (Exception e) {
			throw new RuntimeException("Failed to configure Vaadin Grid for type " + itemType.getCanonicalName(), e);
		}
	}

	private <ITEM> void invokeWrite(ColumnDefinition columnDefinition, ITEM item, Object value) {
		try {
			if (value == null) {
				if (columnDefinition.isPrimitiveTypeWriteMethod()) {
					if (Boolean.class.equals(columnDefinition.getType())) {
						value = false;
					} else if (Character.class.equals(columnDefinition.getType())) {
						value = Character.MIN_VALUE;
					} else {
						value = 0;
					}
				}
			}
			columnDefinition.getWriteMethod().invoke(item, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <ITEM, TYPE> TYPE invokeRead(Method readMethod, ITEM item) {
		try {
			return (TYPE) readMethod.invoke(item);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <ITEM> String provideColumnValue(ColumnDefinition definition, ITEM item) {
		Class<?> columnDataType = definition.getType();
		if (Number.class.isAssignableFrom(columnDataType)) {
			columnDataType = Number.class;
		}

		ResolvableType textualValueProviderType = ResolvableType.forClassWithGenerics(BeanGridValueProvider.class,
				columnDataType);

		List<String> valueProviderNames = Arrays.asList(appContext.getBeanNamesForType(textualValueProviderType));

		if (!valueProviderNames.isEmpty()) {
			BeanGridValueProvider valueProviderInstance = appContext.getBean(valueProviderNames.iterator().next(),
					BeanGridValueProvider.class);

			return valueProviderInstance.provideValue(invokeRead(definition.getReadMethod(), item));
		}

		return "no-provider";
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext = appContext;
	}
}
