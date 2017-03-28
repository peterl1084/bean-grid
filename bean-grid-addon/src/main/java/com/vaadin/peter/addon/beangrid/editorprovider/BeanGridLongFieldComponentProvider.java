package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridLongFieldComponentProvider
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridLongFieldComponentProvider extends AbstractGridNumberValueEditorComponentProvider<Long> {

	public BeanGridLongFieldComponentProvider() {
		super(Long.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
