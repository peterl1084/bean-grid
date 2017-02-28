package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridLongFieldValueConverter implements BeanGridValueConvertingEditorComponentProvider<String, Long> {

	@Override
	public Converter<String, Long> getConverter() {
		return new StringToLongConverter("Failed converting value");
	}

	@Override
	public HasValue<String> provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
