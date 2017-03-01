package com.vaadin.peter.addon.beangrid.valueprovider;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.vaadin.ui.UI;

@Component
public class BeanGridDateValueProvider implements BeanGridValueProvider<Date> {

	@Override
	public String provideValue(Date sourceValue) {
		if (sourceValue == null) {
			return null;
		}

		return DateFormat.getDateInstance(DateFormat.SHORT, UI.getCurrent().getLocale()).format(sourceValue);
	}
}
