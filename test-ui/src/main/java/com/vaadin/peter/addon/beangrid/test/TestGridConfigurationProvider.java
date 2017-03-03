package com.vaadin.peter.addon.beangrid.test;

import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.shared.util.SharedUtil;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class TestGridConfigurationProvider implements GridConfigurationProvider {

	@Override
	public String resolveTranslationKey(String translationKey) {
		if ("true".equals(translationKey)) {
			return "yep";
		} else if ("false".equals(translationKey)) {
			return "nope";
		} else if ("this.is.static.text".equals(translationKey)) {
			return "Static footer text";
		}

		return SharedUtil.propertyIdToHumanFriendly(translationKey);
	}
}
