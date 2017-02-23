package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

@Component
public class BeanGridStringValueProvider implements BeanGridTextualValueProvider<String> {

	@Override
	public String provideTextualValue(String cellValue) {
		return cellValue;
	}
}
