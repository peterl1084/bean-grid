package com.vaadin.peter.addon.beangrid.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

/**
 * StringToBigDecimalBeanConverter is StringToBigIntegerConverter bean that
 * allows external configuration through {@link GridConfigurationProvider} as
 * well as through {@link GridColumn#format()}. Due to configurability this
 * converter is mutable and is NOT singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class StringToBigIntegerBeanConverter extends AbstractStringToNumberConverterBean<BigInteger> {

	@Autowired
	public StringToBigIntegerBeanConverter(GridConfigurationProvider configurationProvider) {
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
	public Result<BigInteger> convertToModel(String value, ValueContext context) {

		return convertToNumber(value, context).map(number -> {
			if (number == null) {
				return null;
			} else {
				// Empty value will be a BigInteger
				if (number instanceof BigInteger) {
					return (BigInteger) number;
				}
				return ((BigDecimal) number).toBigInteger();
			}
		});
	}
}
