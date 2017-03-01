package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridLongFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Long> {

	@Override
	public StringToLongConverter getConverter() {
		return new StringToLongConverter("Failed converting value");
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
