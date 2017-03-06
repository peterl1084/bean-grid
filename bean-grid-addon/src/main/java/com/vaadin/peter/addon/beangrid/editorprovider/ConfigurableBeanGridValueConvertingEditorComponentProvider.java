package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;

/**
 * ConfigurableBeanGridValueConvertingEditorComponentProvider is a nice name for
 * such BeanGridValueConvertingEditorComponentProviders which are capable per
 * column configuring the conversion. For that reason this special type of bean
 * needs to be in prototype scope as due to configurability it's stateful.
 * 
 * @author Peter / Vaadin
 *
 * @param <PROPERTY_TYPE>
 *            type of the property for which the converter can be configured.
 */

public interface ConfigurableBeanGridValueConvertingEditorComponentProvider<FIELD_TYPE, PROPERTY_TYPE>
		extends BeanGridValueConvertingEditorComponentProvider<FIELD_TYPE, PROPERTY_TYPE> {

	/**
	 * Configures this component provider's converter with given
	 * columnDefinition parameters.
	 * 
	 * @param columnDefinition
	 */
	void configureConverter(ColumnDefinition columnDefinition);
}
