package com.vaadin.peter.addon.beangrid.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.SingletonConverter;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.renderers.HtmlRenderer;

/**
 * FontIconToHtmlBeanConverter is {@link Converter} capable of converting
 * {@link FontIcon} to Html representation commonly used with
 * {@link HtmlRenderer}. This converter is immutable and hence singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class FontIconToHtmlBeanConverter implements Converter<String, FontIcon> {

	@Override
	public Result<FontIcon> convertToModel(String value, ValueContext context) {
		throw new UnsupportedOperationException("Conversion is not supported from HTML to FontIcon");
	}

	@Override
	public String convertToPresentation(FontIcon value, ValueContext context) {
		if (value == null) {
			return null;
		}

		return value.getHtml();
	}
}
