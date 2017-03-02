package com.vaadin.peter.addon.beangrid;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
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
import com.vaadin.peter.addon.beangrid.editorprovider.BeanGridEditorComponentProvider;
import com.vaadin.peter.addon.beangrid.summary.Summarizer;
import com.vaadin.peter.addon.beangrid.valueprovider.BeanGridValueProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.components.grid.FooterCell;

/**
 * BeanGridConfiguration defines Vaadin's Grid component as Spring Bean with
 * ability to configure it on runtime with provided ITEM generic type.
 * 
 * @author Peter / Vaadin
 */
@Configuration
@ComponentScan(basePackageClasses = { BeanGridEditorComponentProvider.class, BeanGridValueProvider.class,
		Summarizer.class })
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

	private <ITEM> Grid<ITEM> configureGridInstance(Class<ITEM> itemType) {
		try {

			List<ColumnDefinition> columnDefinitions = ColumnDefinitionTools.discoverColumnDefinitions(itemType);

			Grid<ITEM> grid = new Grid<ITEM>() {
				@Override
				public void setDataProvider(DataProvider<ITEM, ?> dataProvider) {
					super.setDataProvider(dataProvider);
					dataProvider.addDataProviderListener(e -> refreshSummaryFooter(this, columnDefinitions));
				}

				@Override
				public void setItems(Collection<ITEM> items) {
					super.setItems(items);
					refreshSummaryFooter(this, columnDefinitions);
				}
			};

			grid.getEditor().setBinder(new BeanValidationBinder<>(itemType));
			grid.getEditor().addSaveListener(e -> refreshSummaryFooter(grid, columnDefinitions));

			if (ColumnDefinitionTools.isFooterRowRequired(columnDefinitions)) {
				grid.appendFooterRow();
			}

			columnDefinitions.forEach(definition -> {
				Column<ITEM, Object> column = grid.addColumn(item -> provideColumnValue(definition, item));
				definition.setColumn(column);

				if (i18NProvider != null) {
					column.setCaption(i18NProvider.provideTranslation(definition.getTranslationKey()));
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
		} catch (Exception e) {
			throw new RuntimeException("Failed to configure Vaadin Grid for type " + itemType.getCanonicalName(), e);
		}
	}

	protected <ITEM> void refreshSummaryFooter(Grid<ITEM> grid, List<ColumnDefinition> definitions) {
		definitions.stream().filter(definition -> definition.isSummarizable())
				.forEach(definition -> refreshColumnSummary(grid, definition));
	}

	@SuppressWarnings("unchecked")
	private <ITEM, PROPERTY> void refreshColumnSummary(Grid<ITEM> grid, ColumnDefinition definition) {
		Summarizer<PROPERTY> summarizerFor = getSummarizerFor(definition);
		if (ListDataProvider.class.isAssignableFrom(grid.getDataProvider().getClass())) {
			ListDataProvider<ITEM> dataProvider = ListDataProvider.class.cast(grid.getDataProvider());

			FooterCell footerCell = grid.getFooterRow(0).getCell(definition.getColumn());
			List<PROPERTY> propertyValues = (List<PROPERTY>) dataProvider.getItems().stream()
					.map(item -> invokeRead(definition.getReadMethod(), item)).collect(Collectors.toList());

			if (summarizerFor.canSummarize(propertyValues)) {
				PROPERTY summaryValue = summarizerFor.getSummary(propertyValues);
				BeanGridValueProvider<PROPERTY> cellValueProvider = (BeanGridValueProvider<PROPERTY>) findValueProvider(
						definition.getType());
				footerCell.setText(cellValueProvider.provideValue(summaryValue));
			} else {
				footerCell.setText("-");
			}
		}
	}

	protected <TYPE> Summarizer<TYPE> getSummarizerFor(ColumnDefinition definition) {
		Class<? extends Summarizer<TYPE>> summarizerType = null;
		String summarizerBeanName = null;

		if (definition.isSpecificSummarizerDefined()) {
			summarizerType = definition.getSummarizerType();
		} else {
			ResolvableType resolvableSummarizerType = ResolvableType.forClassWithGenerics(Summarizer.class,
					definition.getType());
			List<String> availableSummarizerBeanNames = Arrays
					.asList(appContext.getBeanNamesForType(resolvableSummarizerType));

			if (availableSummarizerBeanNames.isEmpty()) {
				throw new ColumnDefinitionException(
						"No summarizer available for property type " + definition.getType().getSimpleName() + ".");
			} else if (availableSummarizerBeanNames.size() > 1) {
				throw new ColumnDefinitionException("There are more than one summarizer available for property type "
						+ definition.getType().getSimpleName() + ": "
						+ availableSummarizerBeanNames.stream().collect(Collectors.joining(", ")));
			}

			summarizerBeanName = availableSummarizerBeanNames.iterator().next();
			summarizerType = (Class<? extends Summarizer<TYPE>>) appContext.getType(summarizerBeanName);
		}

		if (summarizerType == null) {
			throw new ColumnDefinitionException("Could not determine type of the summarizer for column "
					+ definition.getPropertyName() + " with type " + definition.getType().getSimpleName());
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

	@SuppressWarnings("unchecked")
	protected <ITEM> void setupEditableColumn(Grid<ITEM> grid, ColumnDefinition definition,
			Column<ITEM, Object> column) {
		ResolvableType editorProviderType = ResolvableType.forClassWithGenerics(BeanGridEditorComponentProvider.class,
				definition.getType());

		List<String> editorProviderNames = Arrays.asList(appContext.getBeanNamesForType(editorProviderType));

		if (editorProviderNames.isEmpty()) {
			throw new ColumnDefinitionException("Could not find editor component provider for "
					+ definition.getType().getSimpleName() + ", please implement appropriate "
					+ BeanGridEditorComponentProvider.class.getSimpleName() + " as Spring bean.");
		}

		if (editorProviderNames.size() > 1) {
			throw new ColumnDefinitionException(
					"More than one component provider exists for " + definition.getType().getSimpleName() + ": "
							+ editorProviderNames.stream().collect(Collectors.joining(", ")));
		}

		BeanGridEditorComponentProvider<Object> editorProvider = appContext
				.getBean(editorProviderNames.iterator().next(), BeanGridEditorComponentProvider.class);

		HasValue<?> editorComponent = editorProvider.provideEditorComponent(definition);

		BindingBuilder<ITEM, Object> bindingBuilder = (BindingBuilder<ITEM, Object>) grid.getEditor().getBinder()
				.forField(editorComponent);

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

	private <ITEM> String provideColumnValue(ColumnDefinition definition, ITEM item) {
		BeanGridValueProvider<?> valueProviderInstance = findValueProvider(definition.getType());
		return valueProviderInstance.provideValue(invokeRead(definition.getReadMethod(), item));
	}

	@SuppressWarnings("unchecked")
	private <PROPERTY> BeanGridValueProvider<PROPERTY> findValueProvider(Class<PROPERTY> propertyType) {
		if (Number.class.isAssignableFrom(propertyType)) {
			propertyType = (Class<PROPERTY>) Number.class;
		}

		ResolvableType valueProviderType = ResolvableType.forClassWithGenerics(BeanGridValueProvider.class,
				propertyType);

		List<String> valueProviderNames = Arrays.asList(appContext.getBeanNamesForType(valueProviderType));

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
