package com.vaadin.peter.addon.beangrid.editorprovider;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.TextField;

/**
 * BeanGridBigDecimalFieldComponentProvider
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridBigDecimalFieldComponentProvider
		extends AbstractGridNumberValueEditorComponentProvider<BigDecimal> {

	public BeanGridBigDecimalFieldComponentProvider() {
		super(BigDecimal.class);
	}

	@Override
	public TextField provideEditorComponent(ColumnDefinition columnDefinition) {
		return new TextField();
	}
}
