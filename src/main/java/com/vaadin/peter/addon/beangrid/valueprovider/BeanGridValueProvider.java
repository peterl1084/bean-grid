package com.vaadin.peter.addon.beangrid.valueprovider;

import com.vaadin.data.ValueProvider;

/**
 * BeanGridTextualValueProvider defines a "converter" between given TYPE and
 * String. The BeanGridTextualValueProviders are used with BeanGrid for
 * providing any value of given type as Sting.
 * 
 * @author Peter / Vaadin
 *
 * @param <TYPE>
 *            type of the value to be converter to String
 */
public interface BeanGridValueProvider<SOURCE> extends ValueProvider<SOURCE, String> {

	@Override
	String apply(SOURCE source);
}
