package com.vaadin.peter.addon.beangrid.valueprovider;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.renderers.HtmlRenderer;

public interface BeanGridHtmlValueProvider<PROPERTY_TYPE>
		extends BeanGridConvertingValueProvider<String, PROPERTY_TYPE> {

	@Override
	default HtmlRenderer getRenderer(ColumnDefinition definition) {
		return new HtmlRenderer();
	}
}
