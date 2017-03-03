package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToBigDecimalBeanConverter;
import com.vaadin.ui.TextField;

@Component
public class BeanGridBigDecimalFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, BigDecimal> {

	private StringToBigDecimalBeanConverter converter;

	@Autowired
	public BeanGridBigDecimalFieldComponentProvider(StringToBigDecimalBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public StringToBigDecimalBeanConverter getConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
