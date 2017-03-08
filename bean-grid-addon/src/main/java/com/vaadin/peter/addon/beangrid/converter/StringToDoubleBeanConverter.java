package com.vaadin.peter.addon.beangrid.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.peter.addon.beangrid.PrototypeConverter;

/**
 * StringToDoubleBeanConverter is StringToDoubleConverter bean that allows
 * external configuration through {@link GridConfigurationProvider} as well as
 * through {@link GridColumn#format()}. Due to configurability this converter is
 * mutable and is NOT singleton safe.
 * 
 * @author Peter / Vaadin
 */
@PrototypeConverter
public class StringToDoubleBeanConverter extends StringToDoubleConverter implements ConfigurableConverter<Double> {

	private String pattern;
	private GridConfigurationProvider configurationProvider;

	@Autowired
	public StringToDoubleBeanConverter(GridConfigurationProvider configurationProvider) {
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