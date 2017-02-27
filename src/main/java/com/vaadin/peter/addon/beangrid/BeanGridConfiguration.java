package com.vaadin.peter.addon.beangrid;

import java.util.Arrays;
import java.util.List;

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
			List<ColumnDefinition> columnDefinitions = ColumnDefinitionTools.discoverColumnDefinitions(itemType);
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
