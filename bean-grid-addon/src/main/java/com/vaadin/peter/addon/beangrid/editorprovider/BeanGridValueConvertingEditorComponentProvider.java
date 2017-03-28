package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.converter.ConverterBean;

/**
 * BeanGridValueConvertingEditorComponentProvider is a specialized
 * {@link BeanGridEditorComponentProvider} with the capability of converting
 * between the FIELD_TYPE and PROPERTY_TYPE.
 * 
 * The direct implementations of this interface are safe to be singletons as
 * they're immutable.
 * 
 * @author Peter / Vaadin
 *
 * @param <FIELD_TYPE>
 *            type of the Vaadin Component's {@link HasValue} expected for the
 *            component instantiated by this provider.
 * @param <PROPERTY_TYPE>
 *            type of the property as defined in the bean, used as item of the
 *            Grid.
 */
public interface BeanGridValueConvertingEditorComponentProvider<FIELD_TYPE, PROPERTY_TYPE>
		extends BeanGridEditorComponentProvider<PROPERTY_TYPE> {

	/**
	 * @return {@link Converter} capable of converting between the FIELD_TYPE
	 *         and PROPERTY_TYPE
	 */
	ConverterBean<FIELD_TYPE, PROPERTY_TYPE> getConverter();
}
