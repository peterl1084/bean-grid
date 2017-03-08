package com.vaadin.peter.addon.beangrid.converter;

import java.util.Locale;
import java.util.Objects;

import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;

/**
 * ColumnDefinitionValueContext is an extension of Vaadin's regular converter
 * {@link ValueContext} with the addition that it contains reference to the
 * column that is currently being formatted.
 * 
 * @author Peter / Vaadin
 */
public class ColumnDefinitionValueContext extends ValueContext {

	private final ColumnDefinition definition;

	public ColumnDefinitionValueContext(Locale locale, ColumnDefinition definition) {
		super(locale);
		this.definition = Objects.requireNonNull(definition);
	}

	/**
	 * @return the {@link ColumnDefinition} describing the current column of
	 *         which cell we're formatting.
	 */
	public ColumnDefinition getDefinition() {
		return definition;
	}
}
