package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToIntegerBeanConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridIntegerFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Integer> {

	private StringToIntegerBeanConverter converter;

	@Autowired
	public BeanGridIntegerFieldComponentProvider(StringToIntegerBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public StringToIntegerBeanConverter getConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
