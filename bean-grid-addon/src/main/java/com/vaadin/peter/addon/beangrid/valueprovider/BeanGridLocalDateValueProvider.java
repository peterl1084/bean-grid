package com.vaadin.peter.addon.beangrid.valueprovider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.springframework.stereotype.Component;

import com.vaadin.ui.UI;

@Component
public class BeanGridLocalDateValueProvider implements BeanGridValueProvider<LocalDate> {

	@Override
	public String provideValue(LocalDate sourceValue) {
		if (sourceValue == null) {
			return null;
		}

		return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(UI.getCurrent().getLocale())
				.format(sourceValue);
	}
}
