package com.vaadin.peter.addon.beangrid.valueprovider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.I18NProvider;

@Component
public class BeanGridBooleanValueProvider implements BeanGridValueProvider<Boolean> {

	@Autowired(required = false)
	private I18NProvider i18NProvider;

	@Override
	public String provideValue(Boolean sourceValue) {
		if (sourceValue == null) {
			return null;
		}

		return Optional.ofNullable(i18NProvider)
				.map(provider -> provider.provideTranslation(Boolean.toString(sourceValue)))
				.orElse(Boolean.toString(sourceValue));
	}
}
