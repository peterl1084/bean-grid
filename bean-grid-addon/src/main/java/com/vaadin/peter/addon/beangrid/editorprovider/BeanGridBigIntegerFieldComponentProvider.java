package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigInteger;

import com.vaadin.data.converter.StringToBigIntegerConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridBigIntegerFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, BigInteger> {

	@Override
	public StringToBigIntegerConverter getConverter() {
		return new StringToBigIntegerConverter("Failed converting value");
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
