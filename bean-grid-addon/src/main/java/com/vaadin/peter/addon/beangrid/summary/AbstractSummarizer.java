package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Converter;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

public abstract class AbstractSummarizer<BEAN_FIELD_TYPE> implements Summarizer<BEAN_FIELD_TYPE> {

	private GridConfigurationProvider configurationProvider;

	public AbstractSummarizer() {
		System.out.println("Instantiating: " + getClass().getSimpleName() + " " + this);
	}

	@Autowired
	protected void setGridConfigurationProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public String summarize(ColumnDefinition definition, Collection<BEAN_FIELD_TYPE> allAvailableValues) {
		BEAN_FIELD_TYPE summaryValue = doSummarize(definition, allAvailableValues);
		return getStringConverter(definition).convertToPresentation(summaryValue,
				new ValueContext(configurationProvider.getLocale()));
	}

	protected abstract Converter<String, BEAN_FIELD_TYPE> getStringConverter(ColumnDefinition definition);

	protected abstract BEAN_FIELD_TYPE doSummarize(ColumnDefinition definition,
			Collection<BEAN_FIELD_TYPE> allAvailableValues);
}
