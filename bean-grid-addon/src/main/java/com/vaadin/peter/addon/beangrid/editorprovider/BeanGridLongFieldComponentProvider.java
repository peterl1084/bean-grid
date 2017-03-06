package com.vaadin.peter.addon.beangrid.editorprovider;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridBigDecimalFieldComponentProvider is component provider capable of
 * Long <-> String <-> Long conversion. As this bean implements
 * {@link ConfigurableBeanGridValueConvertingEditorComponentProvider} it's NOT
 * immutable and hence not singleton safe and should always be declared as
 * prototype scoped.
 * 
 * @author Peter / Vaadin
 */
@Component
@Scope(scopeName = "prototype")
public class BeanGridLongFieldComponentProvider
		extends AbstractGridValueConfigurableConvertingEditorComponentProvider<Long> {

	public BeanGridLongFieldComponentProvider() {
		super(Long.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
