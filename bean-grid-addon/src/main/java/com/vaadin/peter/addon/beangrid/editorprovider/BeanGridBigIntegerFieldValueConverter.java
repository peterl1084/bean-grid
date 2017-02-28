package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigInteger;

import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToBigIntegerConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridBigIntegerFieldValueConverter
		implements BeanGridValueConvertingEditorComponentProvider<String, BigInteger> {

	@Override
	public Converter<String, BigInteger> getConverter() {
		return new StringToBigIntegerConverter("Failed converting value");
	}

	@Override
	public HasValue<String> provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
