package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigDecimal;

import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridBigDecimalFieldValueConverter
		implements BeanGridValueConvertingEditorComponentProvider<String, BigDecimal> {

	@Override
	public Converter<String, BigDecimal> getConverter() {
		return new StringToBigDecimalConverter("Failed converting value");
	}

	@Override
	public HasValue<String> provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
