package com.vaadin.peter.addon.beangrid.editorprovider;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.DateField;

@SpringComponent
public class BeanGridLocalDateComponentProvider implements BeanGridEditorComponentProvider<LocalDate> {

	private GridConfigurationProvider configurationProvider;

	@Autowired
	public BeanGridLocalDateComponentProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public DateField provideEditorComponent(ColumnDefinition columnDefinition) {
		DateField dateField = new DateField();

		if (columnDefinition.getFormat().isPresent()) {
			dateField.setDateFormat(columnDefinition.getFormat().get());
		} else if (configurationProvider.getDateFormatPattern().isPresent()) {
			dateField.setDateFormat(configurationProvider.getDateFormatPattern().get());
		}

		return dateField;
	}
}
