package com.vaadin.peter.addon.beangrid.valueprovider;

public interface BeanGridTextualValueProvider<TYPE> {

	String provideTextualValue(TYPE cellValue);
}
