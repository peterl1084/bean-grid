package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CheckBox;

@SpringComponent
public class BeanGridBooleanComponentProvider implements BeanGridEditorComponentProvider<Boolean> {

	@Override
	public CheckBox provideEditorComponent(ColumnDefinition columnDefinition) {
		return new CheckBox();
	}
}
