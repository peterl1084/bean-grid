package com.vaadin.peter.addon.beangrid.editorprovider;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.peter.addon.beangrid.converter.LocalDateToDateConverterBean;
import com.vaadin.ui.DateField;

/**
 * BeanGridDateFieldComponentProvider is component provider capable of producing
 * components and conversion between Date to LocalDate to Date types.
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
	private LocalDateToDateConverterBean converter;

	@Autowired
	public BeanGridDateFieldComponentProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public LocalDateToDateConverterBean getConverter() {
		return converter;
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
