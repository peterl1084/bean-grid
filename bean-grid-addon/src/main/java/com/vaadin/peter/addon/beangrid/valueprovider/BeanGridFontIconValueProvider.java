package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;
import com.vaadin.peter.addon.beangrid.converter.FontIconToHtmlBeanConverter;
import com.vaadin.server.FontIcon;

/**
 * BeanGridFontIconValueProvider is used for showing FontIcons in Grid cells.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridFontIconValueProvider implements BeanGridHtmlValueProvider<FontIcon> {

	private FontIconToHtmlBeanConverter converter;

	@Autowired
	public BeanGridFontIconValueProvider(FontIconToHtmlBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public Converter<String, FontIcon> getConverter() {
		return converter;
	}
}
