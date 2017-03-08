package com.vaadin.peter.addon.beangrid.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

/**
 * StringToDoubleBeanConverter is StringToDoubleConverter bean that allows
 * external configuration through {@link GridConfigurationProvider} as well as
 * through {@link GridColumn#format()}. Due to configurability this converter is
 * mutable and is NOT singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class StringToDoubleBeanConverter extends AbstractStringToNumberConverterBean<Double> {

	@Autowired
	public StringToDoubleBeanConverter(GridConfigurationProvider configurationProvider) {
		super(configurationProvider);
	}

	@Override
	public Result<Double> convertToModel(String value, ValueContext context) {
		return convertToNumber(value, context)
				.map(number -> Optional.ofNullable(number).map(Number::doubleValue).orElse(null));
	}
}
