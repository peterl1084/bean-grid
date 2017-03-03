package com.vaadin.peter.addon.beangrid.valueprovider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.renderers.HtmlRenderer;

/**
 * BeanGridBooleanValueProvider is capable of converting the Boolean property
 * type to HTML String representation that can be visualised as font icon.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridBooleanValueProvider implements BeanGridConvertingValueProvider<String, Boolean> {

	private static final FontIcon TRUE_ICON = VaadinIcons.CHECK;
	private static final FontIcon FALSE_ICON = VaadinIcons.CLOSE;

	private GridConfigurationProvider configurationProvider;

	@Autowired
	public BeanGridBooleanValueProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public HtmlRenderer getRenderer(ColumnDefinition definition) {
		return new HtmlRenderer();
	}

	@Override
	public BooleanToFontIconConverter getConverter() {
		return new BooleanToFontIconConverter();
	}

	private class BooleanToFontIconConverter implements Converter<String, Boolean> {

		@Override
		public Result<Boolean> convertToModel(String value, ValueContext context) {
			throw new UnsupportedOperationException("Conversion from HTML to Boolean not supported");
		}

		@Override
		public String convertToPresentation(Boolean value, ValueContext context) {
			if (value == null) {
				return Optional.ofNullable(configurationProvider.getBooleanFalseFontIcon()).orElse(FALSE_ICON)
						.getHtml();
			} else {
				return value
						? Optional.ofNullable(configurationProvider.getBooleanTrueFontIcon()).orElse(TRUE_ICON)
								.getHtml()
						: Optional.ofNullable(configurationProvider.getBooleanFalseFontIcon()).orElse(FALSE_ICON)
								.getHtml();
			}
		}
	}
}
