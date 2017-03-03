package com.vaadin.peter.addon.beangrid.valueprovider;

import com.vaadin.data.Converter;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.renderers.Renderer;

/**
 * BeanGridConvertingValueProvider is {@link BeanGridValueProvider} with added
 * ability to convert the value from BEAN_FIELD_TYPE to RENDERER_TYPE. This is
 * particularly useful in situations where a specific type of renderer is
 * required and the type of the property value doesn't quite match the required
 * renderer value. This special type of provider defines a Vaadin
 * {@link Converter} between the RENDERER_TYPE and PROPERTY_TYPE
 * 
 * @author Peter / Vaadin
 *
 * @param <RENDERER_TYPE>
 *            the type that the {@link Renderer} supports
 * @param <PROPERTY_TYPE>
 *            the type of the property in the bean
 */
public interface BeanGridConvertingValueProvider<RENDERER_TYPE, PROPERTY_TYPE>
		extends BeanGridValueProvider<PROPERTY_TYPE> {

	/**
	 * @return Converter capable of converting the PROPERTY_TYPE value to
	 *         RENDERER_TYPE value.
	 */
	Converter<RENDERER_TYPE, PROPERTY_TYPE> getConverter();

	/**
	 * Utility method for running the conversion
	 * 
	 * @param propertyValue
	 * @return given propertyValue in RENDERER_TYPE format. Returns null for
	 *         null.
	 */
	default RENDERER_TYPE convert(PROPERTY_TYPE propertyValue) {
		if (propertyValue == null) {
			return null;
		}

		return getConverter().convertToPresentation(propertyValue, new ValueContext());
	}
}
