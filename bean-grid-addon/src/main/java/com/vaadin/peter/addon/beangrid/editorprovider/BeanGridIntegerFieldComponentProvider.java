package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridIntegerFieldComponentProvider
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridIntegerFieldComponentProvider extends AbstractGridNumberValueEditorComponentProvider<Integer> {

	public BeanGridIntegerFieldComponentProvider() {
		super(Integer.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
