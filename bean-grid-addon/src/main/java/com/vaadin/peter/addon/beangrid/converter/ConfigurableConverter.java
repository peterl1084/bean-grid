package com.vaadin.peter.addon.beangrid.converter;

import com.vaadin.data.Converter;

/**
 * ConfigurableConverter allows specifying a pattern that should be used with
 * the conversion. The pattern is fully converter specific and may be date,
 * number or decimal format. Due to converter instance specificity, the
 * implementation implementing this interface needs to be prototype scoped as
 * the pattern makes the converter stateful.
 * 
 * @author Peter / Vaadin
 */
@PrototypeConverter
public interface ConfigurableConverter<PROPERTY_TYPE> extends Converter<String, PROPERTY_TYPE> {

	/**
	 * Configures this converter instance with given pattern.
	 * 
	 * @param pattern
	 * @return this converter instance.
	 */
	void configureWithPattern(String pattern);
}
