package com.vaadin.peter.addon.beangrid.converter;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;

@Component
public class LocalDateToDateConverterBean extends LocalDateToDateConverter implements ConverterBean<LocalDate, Date> {

	@Autowired
	public LocalDateToDateConverterBean(GridConfigurationProvider configurationProvider) {
		super(configurationProvider.getTimeZoneId());
	}
}
