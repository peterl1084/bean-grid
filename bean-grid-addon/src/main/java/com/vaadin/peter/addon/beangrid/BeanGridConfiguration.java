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
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.peter.addon.beangrid.converter.ConfigurableConverter;
import com.vaadin.peter.addon.beangrid.editorprovider.BeanGridEditorComponentProvider;
import com.vaadin.peter.addon.beangrid.editorprovider.ConfigurableBeanGridValueConvertingEditorComponentProvider;
import com.vaadin.peter.addon.beangrid.summary.Summarizer;
import com.vaadin.peter.addon.beangrid.valueprovider.BeanGridDefaultValueProvider;
import com.vaadin.peter.addon.beangrid.valueprovider.BeanGridValueProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.renderers.AbstractRenderer;

/**
 * BeanGridConfiguration defines Vaadin's Grid component as Spring Bean with
 * ability to configure it on runtime with provided ITEM generic type.
 * 
 * @author Peter / Vaadin
 */
@Configuration
@ComponentScan(basePackageClasses = { BeanGridEditorComponentProvider.class, BeanGridValueProvider.class,
		Summarizer.class, ConfigurableConverter.class })
public class BeanGridConfiguration implements ApplicationContextAware {

	private ApplicationContext appContext;

	private static Logger logger = LoggerFactory.getLogger(BeanGridConfiguration.class);

	@Autowired(required = false)
	private GridConfigurationProvider gridConfigurationProvider;

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

			List<ColumnDefinition> columnDefinitions = GridConfigurationTools.discoverColumnDefinitions(itemType);
			SelectionMode selectionMode = GridConfigurationTools.discoverSelectionMode(itemType);

			Grid<ITEM> grid = new Grid<ITEM>() {
				@Override
				public void setDataProvider(DataProvider<ITEM, ?> dataProvider) {
					super.setDataProvider(dataProvider);
					dataProvider.addDataProviderListener(e -> refreshSummaryFooter(this, columnDefinitions));
					dataProvider.refreshAll();
				}
			};

			grid.setSelectionMode(selectionMode);
			grid.getEditor().addSaveListener(e -> refreshSummaryFooter(grid, columnDefinitions));
			grid.getEditor().setBinder(new BeanValidationBinder<>(itemType));
			grid.addStyleName("bean-grid");

			if (GridConfigurationTools.isFooterRowRequired(columnDefinitions)) {
				grid.appendFooterRow();
			}

			columnDefinitions.forEach(definition -> {
				BeanGridValueProvider<?> valueProviderInstance = findValueProvider(definition.getPropertyType());

				Column<ITEM, Object> column = (Column<ITEM, Object>) grid.addColumn(
						valueProviderInstance.getGetter(definition),
						(AbstractRenderer<ITEM, Object>) valueProviderInstance.getRenderer(definition));

				column.setId(definition.getPropertyName());
				column.setStyleGenerator((item) -> {
					return definition.getColumnAlignment().getAlignmentName();
				});

				if (gridConfigurationProvider != null) {
					column.setCaption(gridConfigurationProvider.resolveTranslationKey(definition.getTranslationKey()));
				}

				if (definition.isDefaultHidable()) {
					column.setHidable(definition.isDefaultHidable());
					column.setHidden(!definition.isDefaultVisible());
				}

				if (definition.isEditable()) {
					setupEditableColumn(grid, definition, column);
				}
			});

			return grid;
		} catch (

		Exception e) {
			throw new RuntimeException("Failed to configure Vaadin Grid for type " + itemType.getCanonicalName(), e);
		}
	}

	protected <ITEM> void refreshSummaryFooter(Grid<ITEM> grid, List<ColumnDefinition> definitions) {
		definitions.stream().filter(definition -> definition.isSummarizable())
				.forEach(definition -> refreshColumnSummary(grid, definition));
	}

	@SuppressWarnings("unchecked")
	private <ITEM, RENDERER_TYPE, PROPERTY_TYPE> void refreshColumnSummary(Grid<ITEM> grid,
			ColumnDefinition definition) {
		FooterCell footerCell = grid.getFooterRow(0).getCell(definition.getPropertyName());
		footerCell.setStyleName(definition.getColumnAlignment().getAlignmentName());

		if (definition.isStaticTextSummarizable()) {
			String translatedStaticText = gridConfigurationProvider
					.resolveTranslationKey(definition.getStaticTextSummarizerTranslationKey());
			footerCell.setText(translatedStaticText);
		} else {
			Summarizer<PROPERTY_TYPE> summarizerFor = getSummarizerFor(definition);
			if (ListDataProvider.class.isAssignableFrom(grid.getDataProvider().getClass())) {
				ListDataProvider<ITEM> dataProvider = ListDataProvider.class.cast(grid.getDataProvider());

				List<PROPERTY_TYPE> propertyValues = (List<PROPERTY_TYPE>) dataProvider.getItems().stream()
						.map(item -> invokeRead(definition.getReadMethod(), item)).collect(Collectors.toList());

				if (summarizerFor.canSummarize(definition, propertyValues)) {
					String summaryText = summarizerFor.summarize(definition, propertyValues);
					footerCell.setText(summaryText);
				} else {
					footerCell.setText("-");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected <TYPE> Summarizer<TYPE> getSummarizerFor(ColumnDefinition definition) {
		Class<? extends Summarizer<TYPE>> summarizerType = null;
		String summarizerBeanName = null;

		ResolvableType resolvableSummarizerType = ResolvableType.forClassWithGenerics(Summarizer.class,
				definition.getPropertyType());
		List<String> availableSummarizerBeanNames = Arrays
				.asList(appContext.getBeanNamesForType(resolvableSummarizerType));

		if (availableSummarizerBeanNames.isEmpty()) {
			throw new ColumnDefinitionException(
					"No summarizer available for property type " + definition.getPropertyType().getSimpleName() + ".");
		} else if (availableSummarizerBeanNames.size() > 1) {
			throw new ColumnDefinitionException("There are more than one summarizer available for property type "
					+ definition.getPropertyType().getSimpleName() + ": "
					+ availableSummarizerBeanNames.stream().collect(Collectors.joining(", ")));
		}

		summarizerBeanName = availableSummarizerBeanNames.iterator().next();
		summarizerType = (Class<? extends Summarizer<TYPE>>) appContext.getType(summarizerBeanName);

		if (summarizerType == null) {
			throw new ColumnDefinitionException("Could not determine type of the summarizer for column "
					+ definition.getPropertyName() + " with type " + definition.getPropertyType().getSimpleName());
		}

		return appContext.getBean(summarizerBeanName, summarizerType);
	}

	/**
	 * Sets up the editability of the column
	 * 
	 * @param grid
	 * @param definition
	 * @param column
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <ITEM> void setupEditableColumn(Grid<ITEM> grid, ColumnDefinition definition,
			Column<ITEM, Object> column) {
		ResolvableType editorProviderType = ResolvableType.forClassWithGenerics(BeanGridEditorComponentProvider.class,
				definition.getPropertyType());

		List<String> editorProviderNames = Arrays.asList(appContext.getBeanNamesForType(editorProviderType));

		if (editorProviderNames.isEmpty()) {
			throw new ColumnDefinitionException("Could not find editor component provider for "
					+ definition.getPropertyType().getSimpleName() + ", please implement appropriate "
					+ BeanGridEditorComponentProvider.class.getSimpleName() + " as Spring bean.");
		}

		if (editorProviderNames.size() > 1) {
			throw new ColumnDefinitionException(
					"More than one component provider exists for " + definition.getPropertyType().getSimpleName() + ": "
							+ editorProviderNames.stream().collect(Collectors.joining(", ")));
		}

		BeanGridEditorComponentProvider<Object> editorProvider = appContext
				.getBean(editorProviderNames.iterator().next(), BeanGridEditorComponentProvider.class);

		HasValue<?> editorComponent = editorProvider.provideEditorComponent(definition);

		BindingBuilder<ITEM, Object> bindingBuilder = (BindingBuilder<ITEM, Object>) grid.getEditor().getBinder()
				.forField(editorComponent);

		bindingBuilder = bindingBuilder.withNullRepresentation(editorComponent.getEmptyValue());

		if (editorProvider instanceof ConfigurableBeanGridValueConvertingEditorComponentProvider) {
			ConfigurableBeanGridValueConvertingEditorComponentProvider configurableEditorProvider = (ConfigurableBeanGridValueConvertingEditorComponentProvider) editorProvider;
			configurableEditorProvider.configureConverter(definition);
		}

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

	private <ITEM> void invokeWrite(ColumnDefinition columnDefinition, ITEM item, Object value) {
		try {
			if (value == null) {
				if (columnDefinition.isPrimitiveTypeWriteMethod()) {
					if (Boolean.class.equals(columnDefinition.getPropertyType())) {
						value = false;
					} else if (Character.class.equals(columnDefinition.getPropertyType())) {
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

	@SuppressWarnings("unchecked")
	private <PROPERTY_TYPE> BeanGridValueProvider<PROPERTY_TYPE> findValueProvider(Class<PROPERTY_TYPE> propertyType) {
		if (Number.class.isAssignableFrom(propertyType)) {
			propertyType = (Class<PROPERTY_TYPE>) Number.class;
		}

		ResolvableType valueProviderType = ResolvableType.forClassWithGenerics(BeanGridValueProvider.class,
				propertyType);

		List<String> valueProviderNames = Arrays.asList(appContext.getBeanNamesForType(valueProviderType));

		if (valueProviderNames.isEmpty()) {
			return (BeanGridValueProvider<PROPERTY_TYPE>) appContext.getBean(BeanGridDefaultValueProvider.class);
		}
		if (valueProviderNames.size() == 1) {
			return appContext.getBean(valueProviderNames.iterator().next(), BeanGridValueProvider.class);
		}

		throw new ColumnDefinitionException("Could not find unique " + BeanGridValueProvider.class.getSimpleName()
				+ " for type " + propertyType.getSimpleName());
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext = appContext;
	}
}
