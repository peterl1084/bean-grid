package com.vaadin.peter.addon.beangrid.editorprovider;

import java.time.LocalDate;

import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.DateField;

@SpringComponent
public class BeanGridDatePickerEditorProvider implements BeanGridEditorComponentProvider<LocalDate> {

	@Override
	public HasValue<LocalDate> provideEditorComponent(ColumnDefinition columnDefinition) {
		return new DateField();
	}
}
