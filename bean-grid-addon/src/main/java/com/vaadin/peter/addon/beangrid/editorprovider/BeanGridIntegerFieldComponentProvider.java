package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridBigDecimalFieldComponentProvider is component provider capable of
 * Integer <-> String <-> Integer conversion. As this bean implements
 * {@link ConfigurableBeanGridValueConvertingEditorComponentProvider} it's NOT
 * immutable and hence not singleton safe and should always be declared as
 * prototype scoped.
 * 
 * @author Peter / Vaadin
 */
@Component
@Scope(scopeName = "prototype")
public class BeanGridIntegerFieldComponentProvider
		extends AbstractGridValueConfigurableConvertingEditorComponentProvider<Integer> {

	public BeanGridIntegerFieldComponentProvider() {
		super(Integer.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
