package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToDoubleBeanConverter;
import com.vaadin.ui.TextField;

@Component
public class BeanGridDoubleFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Double> {

	private StringToDoubleBeanConverter converter;

	@Autowired
	public BeanGridDoubleFieldComponentProvider(StringToDoubleBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public StringToDoubleBeanConverter getConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
