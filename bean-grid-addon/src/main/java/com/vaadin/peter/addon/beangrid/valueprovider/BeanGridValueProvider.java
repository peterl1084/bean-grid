package com.vaadin.peter.addon.beangrid.valueprovider;

import com.vaadin.data.ValueProvider;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.editorprovider.BeanGridEditorComponentProvider;
import com.vaadin.ui.renderers.Renderer;

/**
 * BeanGridValueProvider is used for acquiring the property value from the Item
 * and passing it as {@link ValueProvider} to Grid's Column. In regular cases if
 * the value of the property can be represented as String the
 * {@link BeanGridDefaultValueProvider} implementation is used, otherwise a
 * {@link Renderer} specific {@link BeanGridConvertingValueProvider} is
 * automatically discovered.
 *
 * {@link BeanGridValueProvider}s are qualified by their generic PROPERTY_TYPE.
 * 
 * @author Peter / Vaadin
 *
 * @param <PROPERTY_TYPE>
 *            type of the property in the Item from which the properties are to
 *            be extracted.
 */
public interface BeanGridValueProvider<PROPERTY_TYPE> {

	/**
	 * @param definition
	 *            that can be used to configure the renderer if needed.
	 * @return new {@link Renderer} instance that can be used to visualize the
	 *         value provided by this {@link BeanGridValueProvider}
	 */
	Renderer<?> getRenderer(ColumnDefinition definition);

	@SuppressWarnings("unchecked")
	default <ITEM> ValueProvider<ITEM, Object> getGetter(ColumnDefinition definition) {
		return item -> {
			try {
				PROPERTY_TYPE modelValue = (PROPERTY_TYPE) definition.getPropertyType()
						.cast(definition.getReadMethod().invoke(item));

				return requiresConversion() ? asConvertable().convert(modelValue) : modelValue;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	/**
	 * @return true if this type requires conversion, false otherwise.
	 */
	default boolean requiresConversion() {
		return this instanceof BeanGridConvertingValueProvider;
	}

	/**
	 * @return this bean as conversion capable component provider. This method
	 *         is intended for internal use only and it must not be invoked
	 *         unless
	 *         {@link BeanGridEditorComponentProvider#requiresConversion()} says
	 *         so.
	 */
	@SuppressWarnings("unchecked")
	default <RENDERER_TYPE> BeanGridConvertingValueProvider<RENDERER_TYPE, PROPERTY_TYPE> asConvertable() {
		return BeanGridConvertingValueProvider.class.cast(this);
	}
}
