package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridBigIntegerFieldComponentProvider
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridBigIntegerFieldComponentProvider
		extends AbstractGridNumberValueEditorComponentProvider<BigInteger> {

	public BeanGridBigIntegerFieldComponentProvider() {
		super(BigInteger.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
