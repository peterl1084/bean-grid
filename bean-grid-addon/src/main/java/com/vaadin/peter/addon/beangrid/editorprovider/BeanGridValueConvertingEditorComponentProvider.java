package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;

/**
 * BeanGridValueConvertingEditorComponentProvider is a specialized
 * {@link BeanGridEditorComponentProvider} with the capability of converting
 * between the FIELD_TYPE and BEAN_PROPERTY_TYPE
 * 
 * @author Peter / Vaadin
 *
 * @param <FIELD_TYPE>
 *            type of the Vaadin Component's {@link HasValue} expected for the
 *            component instantiated by this provider
 * @param <BEAN_PROPERTY_TYPE>
 *            type of the property as defined in the bean used as item of the
 *            Grid.
 */
public interface BeanGridValueConvertingEditorComponentProvider<FIELD_TYPE, BEAN_PROPERTY_TYPE>
		extends BeanGridEditorComponentProvider<BEAN_PROPERTY_TYPE> {

	/**
	 * @param definition
	 *            that may be used to configure the converter.
	 * @return {@link Converter} capable of converting between the FIELD_TYPE
	 *         and BEAN_PROPERTY_TYPE
	 */
	Converter<FIELD_TYPE, BEAN_PROPERTY_TYPE> getConverter(ColumnDefinition definition);
}
