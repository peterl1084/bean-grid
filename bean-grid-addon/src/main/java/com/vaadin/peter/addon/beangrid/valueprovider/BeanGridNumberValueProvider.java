package com.vaadin.peter.addon.beangrid.valueprovider;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.renderers.NumberRenderer;

/**
 * BeanGridNumberValueProvider
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridNumberValueProvider implements BeanGridValueProvider<Number> {

	@Override
	public NumberRenderer getRenderer(ColumnDefinition definition) {
		if (definition.getFormat().isPresent()) {
			return new NumberRenderer(new DecimalFormat(definition.getFormat().get()));
		}

		return new NumberRenderer();
	}
}
