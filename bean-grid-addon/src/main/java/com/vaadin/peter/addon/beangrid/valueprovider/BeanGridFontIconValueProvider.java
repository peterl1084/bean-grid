package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.renderers.HtmlRenderer;

/**
 * BeanGridFontIconValueProvider is used for showing FontIcons in Grid cells.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridFontIconValueProvider implements BeanGridConvertingValueProvider<String, FontIcon> {

	@Override
	public HtmlRenderer getRenderer(ColumnDefinition definition) {
		return new HtmlRenderer();
	}

	@Override
	public FontIconToStringConverter getConverter() {
		return new FontIconToStringConverter();
	}

	private static class FontIconToStringConverter implements Converter<String, FontIcon> {

		@Override
		public Result<FontIcon> convertToModel(String value, ValueContext context) {
			throw new UnsupportedOperationException("Conversion from HTML to FontIcon not supported");
		}

		@Override
		public String convertToPresentation(FontIcon value, ValueContext context) {
			if (value == null) {
				return null;
			}

			return value.getHtml();
		}
	}
}
