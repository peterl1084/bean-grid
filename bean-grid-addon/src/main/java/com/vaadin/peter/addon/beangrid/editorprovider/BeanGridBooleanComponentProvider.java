package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.CheckBox;

@Component
public class BeanGridBooleanComponentProvider implements BeanGridEditorComponentProvider<Boolean> {

	@Override
	public CheckBox provideEditorComponent(ColumnDefinition columnDefinition) {
		return new CheckBox();
	}
}
