/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.peter.addon.beangrid.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.ui.AbstractComponent;

/**
 * AbstractStringToNumberConverterBean is the abstract base class for all such
 * converters that do conversions between String and Number. This class has been
 * adapted from Vaadin Framework 8.0 and modified for the purposes of supporting
 * {@link ValueContext} passing and configurability through
 * {@link GridConfigurationProvider}
 * 
 * @author Peter / Vaadin
 */
public abstract class AbstractStringToNumberConverterBean<PROPERTY_TYPE extends Number>
		implements ConverterBean<String, PROPERTY_TYPE> {

	private GridConfigurationProvider configurationProvider;

	@Autowired
	protected AbstractStringToNumberConverterBean(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	/**
	 * Returns the format used by
	 * {@link #convertToPresentation(Object, ValueContext)} and
	 * {@link #convertToModel(Object, ValueContext)}.
	 *
	 * @param context
	 *            value context to use
	 * @return A NumberFormat instance
	 */
	protected NumberFormat getFormat(ValueContext context) {
		String pattern = null;

		Object data = context.getComponent().map(AbstractComponent.class::cast).map(component -> component.getData())
				.orElse(null);
		if (data instanceof ColumnDefinition) {
			pattern = ((ColumnDefinition) data).getFormat()
					.orElse(configurationProvider.getNumberFormatPattern().orElse(null));
		}

		Locale locale = context.getLocale().orElse(configurationProvider.getLocale());

		if (pattern == null) {
			return NumberFormat.getNumberInstance(locale);
		}

		return new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
	}

	/**
	 * Convert the value to a Number using the given valueContext
	 *
	 * @param value
	 *            The value to convert
	 * @param valueContext
	 *            The locale to use for conversion
	 * @return The converted value
	 */
	protected Result<Number> convertToNumber(String value, ValueContext valueContext) {
		if (value == null) {
			return Result.ok(null);
		}

		// Remove leading and trailing white space
		value = value.trim();

		// Parse and detect errors. If the full string was not used, it is
		// an error.
		ParsePosition parsePosition = new ParsePosition(0);
		Number parsedValue = getFormat(valueContext).parse(value, parsePosition);
		if (parsePosition.getIndex() != value.length()) {
			return Result.error(getErrorMessage());
		}

		if (parsedValue == null) {
			// Convert "" to the empty value
			return Result.ok(null);
		}

		return Result.ok(parsedValue);
	}

	/**
	 * Gets the error message to use when conversion fails.
	 *
	 * @return the error message
	 */
	protected String getErrorMessage() {
		return configurationProvider.getConversionErrorString();
	}

	@Override
	public String convertToPresentation(PROPERTY_TYPE value, ValueContext context) {
		if (value == null) {
			return null;
		}

		return getFormat(context).format(value);
	}
}
