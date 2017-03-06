package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToCharacterConverter;
import com.vaadin.ui.TextField;

/**
 * BeanGridCharacterComponentProvider allows creating Character <-> String <->
 * Character capable editor components and associated {@link Converter}s.
 * 
 * This bean is immutable and singleton safe.
 * 
 * @author Peter / Vaadin
 */
@Component
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
