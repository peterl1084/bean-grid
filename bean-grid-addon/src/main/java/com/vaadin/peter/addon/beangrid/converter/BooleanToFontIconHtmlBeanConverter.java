package com.vaadin.peter.addon.beangrid.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.peter.addon.beangrid.SingletonConverter;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.renderers.HtmlRenderer;

/**
 * BooleanToFontIconHtmlBeanConverter is Converter capable of converting
 * Booleans to FontIcon Html Strings compatible with {@link HtmlRenderer}. This
 * converter is immutable and hence singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class BooleanToFontIconHtmlBeanConverter implements Converter<String, Boolean> {

	private static final FontIcon TRUE_ICON = VaadinIcons.CHECK;
	private static final FontIcon FALSE_ICON = VaadinIcons.CLOSE;

	private GridConfigurationProvider configurationProvider;

	@Autowired
	public BooleanToFontIconHtmlBeanConverter(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public Result<Boolean> convertToModel(String value, ValueContext context) {
		throw new UnsupportedOperationException("Conversion from HTML to Boolean not supported");
	}

	@Override
	public String convertToPresentation(Boolean value, ValueContext context) {
		if (value == null) {
			return Optional.ofNullable(configurationProvider.getBooleanFalseFontIcon()).orElse(FALSE_ICON).getHtml();
		} else {
			return value
					? Optional.ofNullable(configurationProvider.getBooleanTrueFontIcon()).orElse(TRUE_ICON).getHtml()
					: Optional.ofNullable(configurationProvider.getBooleanFalseFontIcon()).orElse(FALSE_ICON).getHtml();
		}
	}
}
