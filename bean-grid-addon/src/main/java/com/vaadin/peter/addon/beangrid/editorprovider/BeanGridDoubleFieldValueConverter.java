package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridDoubleFieldValueConverter
		implements BeanGridValueConvertingEditorComponentProvider<String, Double> {

	@Override
	public Converter<String, Double> getConverter() {
		return new StringToDoubleConverter("Failed converting value");
	}

	@Override
	public HasValue<String> provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
