package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

@Component
public class BeanGridStringValueProvider implements BeanGridValueProvider<String> {

	@Override
	public String provideValue(String sourceValue) {
		return sourceValue;
	}
}
