package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridDoubleFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Double> {

	@Override
	public StringToDoubleConverter getConverter() {
		return new StringToDoubleConverter("Failed converting value");
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
