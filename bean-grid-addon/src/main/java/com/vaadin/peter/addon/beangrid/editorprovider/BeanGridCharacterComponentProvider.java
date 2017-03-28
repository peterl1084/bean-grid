package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToCharacterBeanConverter;
import com.vaadin.ui.TextField;

/**
 * BeanGridCharacterComponentProvider allows creating Character to String to
 * Character capable editor components and associated {@link Converter}s.
 * 
 * This bean is immutable and singleton safe.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridCharacterComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Character> {

	private final StringToCharacterBeanConverter converter;

	@Autowired
	public BeanGridCharacterComponentProvider(StringToCharacterBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}

	@Override
	public StringToCharacterBeanConverter getConverter() {
		return converter;
	}
}
