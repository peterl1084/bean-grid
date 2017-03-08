package com.vaadin.peter.addon.beangrid.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

/**
 * StringToBigDecimalBeanConverter is StringToBigDecimalConverter bean that
 * allows external configuration through {@link GridConfigurationProvider} as
 * well as through {@link GridColumn#format()}. Due to configurability this
 * converter is mutable and is NOT singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class StringToBigDecimalBeanConverter extends AbstractStringToNumberConverterBean<BigDecimal> {

	@Autowired
	public StringToBigDecimalBeanConverter(GridConfigurationProvider configurationProvider) {
		super(configurationProvider);
	}

	@Override
	protected NumberFormat getFormat(ValueContext context) {
		NumberFormat format = super.getFormat(context);

		if (format instanceof DecimalFormat) {
			((DecimalFormat) format).setParseBigDecimal(true);
		}

		return format;
	}

	@Override
	public Result<BigDecimal> convertToModel(String value, ValueContext context) {
		return convertToNumber(value, context).map(number -> (BigDecimal) number);
	}
}
