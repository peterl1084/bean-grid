package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

@Component
public class BeanGridStringValueProvider implements BeanGridValueProvider<String> {

	@Override
	public String apply(String source) {
		return source;
	}
}
