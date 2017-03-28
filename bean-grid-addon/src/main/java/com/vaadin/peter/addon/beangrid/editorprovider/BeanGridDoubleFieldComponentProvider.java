package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridDoubleFieldComponentProvider
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridDoubleFieldComponentProvider extends AbstractGridNumberValueEditorComponentProvider<Double> {

	public BeanGridDoubleFieldComponentProvider() {
		super(Double.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
