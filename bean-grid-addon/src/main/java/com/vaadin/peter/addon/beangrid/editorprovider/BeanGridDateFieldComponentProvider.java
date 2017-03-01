package com.vaadin.peter.addon.beangrid.editorprovider;

import java.time.LocalDate;
import java.util.Date;

import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.DateField;

@SpringComponent
public class BeanGridDateFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<LocalDate, Date> {

	@Override
	public LocalDateToDateConverter getConverter() {
		return new LocalDateToDateConverter();
	}

	@Override
	public DateField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new DateField();
	}
}
