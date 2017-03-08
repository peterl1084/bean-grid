package com.vaadin.peter.addon.beangrid.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

/**
 * StringToIntegerBeanConverter is StringToIntegerConverter bean that allows
 * external configuration through {@link GridConfigurationProvider} as well as
 * through {@link GridColumn#format()}. Due to configurability this converter is
 * mutable and is NOT singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class StringToIntegerBeanConverter extends AbstractStringToNumberConverterBean<Integer> {

	@Autowired
	public StringToIntegerBeanConverter(GridConfigurationProvider configurationProvider) {
		super(configurationProvider);
	}

	@Override
	public Result<Integer> convertToModel(String value, ValueContext context) {
		Result<Number> n = convertToNumber(value, context);
		return n.flatMap(number -> {
			if (number == null) {
				return Result.ok(null);
			} else {
				int intValue = number.intValue();
				if (intValue == number.longValue()) {
					// If the value of n is outside the range of long, the
					// return value of longValue() is either Long.MIN_VALUE or
					// Long.MAX_VALUE. The/ above comparison promotes int to
					// long and thus does not need to consider wrap-around.
					return Result.ok(intValue);
				} else {
					return Result.error(getErrorMessage());
				}
			}
		});
	}
}
