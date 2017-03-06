package com.vaadin.peter.addon.beangrid.editorprovider;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.ui.DateField;

/**
 * BeanGridDateFieldComponentProvider is component provider capable of producing
 * components and conversion between Date <-> LocalDate <-> Date types.
 * 
 * This bean is immutable and singleton safe.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridDateFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<LocalDate, Date> {

	private GridConfigurationProvider configurationProvider;

	@Autowired
	public BeanGridDateFieldComponentProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public LocalDateToDateConverter getConverter() {
		return new LocalDateToDateConverter(configurationProvider.getTimeZoneId());
	}

	@Override
	public DateField provideEditorComponent(ColumnDefinition columnDefinition) {
		DateField dateField = new DateField();

		if (columnDefinition.getFormat().isPresent()) {
			dateField.setDateFormat(columnDefinition.getFormat().get());
		} else if (configurationProvider.getDateFormatPattern().isPresent()) {
			dateField.setDateFormat(configurationProvider.getDateFormatPattern().get());
		}

		return dateField;
	}
}
