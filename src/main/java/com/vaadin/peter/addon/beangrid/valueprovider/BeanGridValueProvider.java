package com.vaadin.peter.addon.beangrid.valueprovider;

/**
 * BeanGridTextualValueProvider defines a "converter" between given TYPE and
 * String. The BeanGridTextualValueProviders are used with BeanGrid for
 * providing any value of given type as String.
 * 
 * @author Peter / Vaadin
 *
 * @param <TYPE>
 *            type of the value to be converter to String
 */
public interface BeanGridValueProvider<SOURCE> {

	String provideValue(SOURCE sourceValue);
}
