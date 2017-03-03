package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToBigIntegerBeanConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridBigIntegerFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, BigInteger> {

	private StringToBigIntegerBeanConverter converter;

	@Autowired
	public BeanGridBigIntegerFieldComponentProvider(StringToBigIntegerBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public StringToBigIntegerBeanConverter getConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
