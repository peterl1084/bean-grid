package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

/**
 * <pre>
 * BeanGridEditorComponentProvider is top level interface for all component
 * providing capable beans which are to be consulted when {@link Grid}'s editor
 * row is opened and editor components are instantiated.
 * 
 * The implementations of BeanGridEditorComponentProvider are looked for as
 * Spring Beans, hence they need to be enabled as such by declaring them
 * as @Components or @SpringComponents. The generic type resolution is resolved
 * based on PROPERTY_TYPE. This means that for each property type there
 * should be single BeanGridEditorComponentProvider bean that is capable of
 * building Vaadin component that is intended to be used with that type.
 * 
 * If the type is not directly assignable a special
 * {@link BeanGridValueConvertingEditorComponentProvider} can be used. With it
 * the field type can also be specified as additional generic type parameter.
 * The interface also allows defining converter between the field type and
 * property type.
 * </pre>
 * 
 * @author Peter / Vaadin
 *
 * @param <PROPERTY_TYPE>
 *            type of the property in the item that represents the grid row.
 */
public interface BeanGridEditorComponentProvider<PROPERTY_TYPE> {

	/**
	 * Provides a component that should be used with PROPERTY_TYPE. If the
	 * component's {@link HasValue} type is different from PROPERTY_TYPE this
	 * component provider bean should implement
	 * {@link BeanGridValueConvertingEditorComponentProvider} instead.
	 * 
	 * @param columnDefinition
	 * @return Vaadin {@link Component} with required {@link HasValue} type.
	 */
	HasValue<?> provideEditorComponent(ColumnDefinition columnDefinition);

	/**
	 * @return true if this type requires conversion, false otherwise.
	 */
	default boolean requiresConversion() {
		return this instanceof BeanGridValueConvertingEditorComponentProvider;
	}

	/**
	 * @return this bean as conversion capable component provider. This method
	 *         is intended for internal use only and it must not be invoked
	 *         unless
	 *         {@link BeanGridEditorComponentProvider#requiresConversion()} says
	 *         so.
	 */
	default BeanGridValueConvertingEditorComponentProvider<?, ?> asConvertable() {
		return BeanGridValueConvertingEditorComponentProvider.class.cast(this);
	}
}
