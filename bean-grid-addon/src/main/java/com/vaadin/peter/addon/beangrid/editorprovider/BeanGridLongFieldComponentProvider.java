package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToLongBeanConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridLongFieldComponentProvider
		implements BeanGridValueConvertingEditorComponentProvider<String, Long> {

	private StringToLongBeanConverter converter;

	@Autowired
	public BeanGridLongFieldComponentProvider(StringToLongBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public StringToLongBeanConverter getConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
