package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToCharacterConverter;
import com.vaadin.ui.TextField;

@Component
public class BeanGridCharacterComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Character> {

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}

	@Override
	public StringToCharacterConverter getConverter(ColumnDefinition definition) {
		return new StringToCharacterConverter();
	}
}
