package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridIntegerFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Integer> {

	@Override
	public StringToIntegerConverter getConverter() {
		return new StringToIntegerConverter("Failed converting value");
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
