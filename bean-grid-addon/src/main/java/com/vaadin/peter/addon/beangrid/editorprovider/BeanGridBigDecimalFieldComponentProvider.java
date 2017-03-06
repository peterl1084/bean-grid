package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridBigDecimalFieldComponentProvider is component provider capable of
 * BigDecimal <-> String <-> BigDecimal conversion. As this bean implements
 * {@link ConfigurableBeanGridValueConvertingEditorComponentProvider} it's NOT
 * immutable and hence not singleton safe and should always be declared as
 * prototype scoped.
 * 
 * @author Peter / Vaadin
 */
@Component
@Scope(scopeName = "prototype")
public class BeanGridBigDecimalFieldComponentProvider
		extends AbstractGridValueConfigurableConvertingEditorComponentProvider<BigDecimal> {

	public BeanGridBigDecimalFieldComponentProvider() {
		super(BigDecimal.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
