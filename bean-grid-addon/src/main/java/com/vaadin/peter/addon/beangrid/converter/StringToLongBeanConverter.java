package com.vaadin.peter.addon.beangrid.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

@Component
@Scope(scopeName = "prototype")
public class StringToLongBeanConverter extends StringToLongConverter implements ConfigurableConverter<Long> {

	private String pattern;
	private GridConfigurationProvider configurationProvider;

	@Autowired
	public StringToLongBeanConverter(GridConfigurationProvider configurationProvider) {
		super(configurationProvider.getConversionErrorString());
		this.configurationProvider = configurationProvider;
	}

	@Override
	protected NumberFormat getFormat(Locale locale) {
		String selectedPattern = Optional.ofNullable(pattern)
				.orElse(configurationProvider.getNumberFormatPattern().orElse(null));

		if (selectedPattern == null) {
			return super.getFormat(locale);
		}

		DecimalFormat decimalFormat = new DecimalFormat(selectedPattern, new DecimalFormatSymbols(locale));
		decimalFormat.setParseBigDecimal(true);
		return decimalFormat;
	}

	@Override
	public void configureWithPattern(String pattern) {
		this.pattern = pattern;
	}
}
