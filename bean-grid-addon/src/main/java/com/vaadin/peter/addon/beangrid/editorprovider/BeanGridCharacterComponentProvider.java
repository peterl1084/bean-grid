package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToCharacterConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridCharacterComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Character> {

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}

	@Override
	public StringToCharacterConverter getConverter() {
		return new StringToCharacterConverter();
	}
}
