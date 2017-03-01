package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;

@SpringComponent
public class BeanGridTextFieldComponentProvider implements BeanGridEditorComponentProvider<String> {

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
