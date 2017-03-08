package com.vaadin.peter.addon.beangrid.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

/**
 * StringToLongBeanConverter is StringToLongConverter bean that allows external
 * configuration through {@link GridConfigurationProvider} as well as through
 * {@link GridColumn#format()}. Due to configurability this converter is mutable
 * and is NOT singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class StringToLongBeanConverter extends AbstractStringToNumberConverterBean<Long> {

	@Autowired
	public StringToLongBeanConverter(GridConfigurationProvider configurationProvider) {
		super(configurationProvider);
	}

	@Override
	public Result<Long> convertToModel(String value, ValueContext context) {
		Result<Number> n = convertToNumber(value, context);
		return n.map(number -> {
			if (number == null) {
				return null;
			} else {
				return number.longValue();
			}
		});
	}
}
