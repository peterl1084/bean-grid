package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

@Component
public class BeanGridBooleanValueProvider implements BeanGridValueProvider<Boolean> {

	@Override
	public String provideValue(Boolean sourceValue) {
		if (sourceValue == null) {
			return null;
		}

		return Boolean.toString(sourceValue);
	}
}
