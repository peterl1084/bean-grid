package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridIntegerFieldEditorProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Integer> {

	@Override
	public Converter<String, Integer> getConverter() {
		return new StringToIntegerConverter("Failed converting value");
	}

	@Override
	public HasValue<String> provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
