package com.vaadin.peter.addon.beangrid.summary;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.peter.addon.beangrid.converter.ConfigurableConverter;

/**
 * AbstractSummarizer is abstract implementation of {@link Summarizer} which
 * sets up converter beans if necessary.
 * 
 * @author Peter / Vaadin
 *
 * @param <PROPERTY_TYPE>
 */
public abstract class AbstractSummarizer<PROPERTY_TYPE> implements Summarizer<PROPERTY_TYPE> {

	private GridConfigurationProvider configurationProvider;

	@Autowired
	private ApplicationContext appContext;

	private Class<PROPERTY_TYPE> propertyType;

	private ConfigurableConverter<PROPERTY_TYPE> converter;

	public AbstractSummarizer(Class<PROPERTY_TYPE> propertyType) {
		this.propertyType = propertyType;
	}

	@PostConstruct
	@SuppressWarnings("unchecked")
	protected void initialize() {
		ResolvableType converterResolvable = ResolvableType.forClassWithGenerics(ConfigurableConverter.class,
				propertyType);
		String converterBeanName = Arrays.asList(appContext.getBeanNamesForType(converterResolvable)).iterator().next();
		converter = appContext.getBean(converterBeanName, ConfigurableConverter.class);
	}

	@Autowired
	protected void setGridConfigurationProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public String summarize(ColumnDefinition definition, Collection<PROPERTY_TYPE> allAvailableValues) {
		PROPERTY_TYPE summaryValue = doSummarize(definition, allAvailableValues);
		converter.configureWithPattern(definition.getFormat().orElse(null));
		return converter.convertToPresentation(summaryValue, new ValueContext(configurationProvider.getLocale()));
	}

	protected abstract PROPERTY_TYPE doSummarize(ColumnDefinition definition,
			Collection<PROPERTY_TYPE> allAvailableValues);
}
