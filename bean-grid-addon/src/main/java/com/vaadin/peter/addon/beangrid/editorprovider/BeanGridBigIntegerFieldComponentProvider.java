package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridBigDecimalFieldComponentProvider is component provider capable of
 * BigInteger <-> String <-> BigInteger conversion. As this bean implements
 * {@link ConfigurableBeanGridValueConvertingEditorComponentProvider} it's NOT
 * immutable and hence not singleton safe and should always be declared as
 * prototype scoped.
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
