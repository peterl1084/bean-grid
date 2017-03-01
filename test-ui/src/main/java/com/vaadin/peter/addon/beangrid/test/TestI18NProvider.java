package com.vaadin.peter.addon.beangrid.test;

import com.vaadin.peter.addon.beangrid.I18NProvider;
import com.vaadin.shared.util.SharedUtil;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class TestI18NProvider implements I18NProvider {

	@Override
	public String provideTranslation(String translationKey) {
		if ("true".equals(translationKey)) {
			return "yep";
		} else if ("false".equals(translationKey)) {
			return "nope";
		}

		return SharedUtil.propertyIdToHumanFriendly(translationKey);
	}
}
