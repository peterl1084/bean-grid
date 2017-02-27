package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

@Component
public class BeanGridNumberValueProvider implements BeanGridValueProvider<Number> {

	@Override
	public String provideValue(Number sourceValue) {
		if (sourceValue == null) {
			return null;
		}

		return sourceValue.toString();
	}
}
