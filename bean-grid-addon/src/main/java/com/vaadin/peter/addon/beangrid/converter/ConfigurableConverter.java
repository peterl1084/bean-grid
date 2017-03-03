package com.vaadin.peter.addon.beangrid.converter;

/**
 * ConfigurableConverter allows specifying a pattern that should be used with
 * the conversion. The pattern is fully converter specific and may be date,
 * number or decimal format.
 * 
 * @author Peter / Vaadin
 */
public interface ConfigurableConverter {

	/**
	 * Configures this converter instance with given pattern.
	 * 
	 * @param pattern
	 * @return this converter instance.
	 */
	ConfigurableConverter configureWithPattern(String pattern);
}
