package com.vaadin.peter.addon.beangrid.valueprovider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.ui.renderers.DateRenderer;

/**
 * BeanGridDateValueProvider
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridDateValueProvider implements BeanGridValueProvider<Date> {

	private final GridConfigurationProvider configurationProvider;

	@Autowired
	public BeanGridDateValueProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public DateRenderer getRenderer(ColumnDefinition definition) {
		if (definition.getFormat().isPresent()) {
			return new DateRenderer(new SimpleDateFormat(definition.getFormat().get()));
		}

		if (configurationProvider.getDateFormatPattern().isPresent()) {
			return new DateRenderer(new SimpleDateFormat(configurationProvider.getDateFormatPattern().get()));
		}

		return new DateRenderer(configurationProvider.getLocale());
	}
}
