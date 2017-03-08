package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.converter.BooleanToFontIconHtmlBeanConverter;

/**
 * BeanGridBooleanValueProvider is capable of converting the Boolean property
 * type to HTML String representation that can be visualized as font icon.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridBooleanValueProvider implements BeanGridHtmlValueProvider<Boolean> {

	private final BooleanToFontIconHtmlBeanConverter converter;

	public BeanGridBooleanValueProvider(BooleanToFontIconHtmlBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public BooleanToFontIconHtmlBeanConverter getConverter() {
		return converter;
	}
}
