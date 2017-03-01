package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

@Component
public class BeanGridCharacterValueProvider implements BeanGridValueProvider<Character> {

	@Override
	public String provideValue(Character sourceValue) {
		if (sourceValue == null) {
			return null;
		}

		return sourceValue.toString();
	}
}
