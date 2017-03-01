package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigDecimal;

import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridBigDecimalFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, BigDecimal> {

	@Override
	public StringToBigDecimalConverter getConverter() {
		return new StringToBigDecimalConverter("Failed converting value");
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
