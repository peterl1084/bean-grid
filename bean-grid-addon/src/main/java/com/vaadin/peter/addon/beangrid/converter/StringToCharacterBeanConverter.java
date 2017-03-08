package com.vaadin.peter.addon.beangrid.converter;

import org.springframework.util.StringUtils;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

/**
 * StringToCharacterBeanConverter is {@link Converter} between String <->
 * Character. This converter is immutable and hence singleton safe.
 * 
 * @author Peter / Vaadin
 */
@SingletonConverter
public class StringToCharacterBeanConverter implements Converter<String, Character> {

	@Override
	public Result<Character> convertToModel(String value, ValueContext context) {
		if (value == null) {
			return Result.ok(null);
		}

		if (StringUtils.isEmpty(value)) {
			return Result.ok(Character.MIN_VALUE);
		}

		return Result.ok(value.charAt(0));
	}

	@Override
	public String convertToPresentation(Character value, ValueContext context) {
		if (value == null) {
			return null;
		}

		return value.toString();
	}
}
