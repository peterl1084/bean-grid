package com.vaadin.peter.addon.beangrid.valueprovider;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.GridConfigurationProvider;
import com.vaadin.ui.renderers.DateRenderer;

/**
 * BeanGridLocalDateValueProvider is used for showing LocalDate types of values
 * in Grid with {@link DateRenderer}. As {@link DateRenderer} in Vaadin 8.0.0
 * only supports {@link Date} and not {@link LocalDate}, the conversion is done
 * within this value provider.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridLocalDateValueProvider implements BeanGridConvertingValueProvider<Date, LocalDate> {

	private final GridConfigurationProvider configurationProvider;

	@Autowired
	public BeanGridLocalDateValueProvider(GridConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@Override
	public DateRenderer getRenderer(ColumnDefinition definition) {
		if (definition.getFormat().isPresent()) {
			return new DateRenderer(new SimpleDateFormat(definition.getFormat().get()));
		}

		if (configurationProvider.getDateFormatPattern().isPresent()) {
			return new DateRenderer(new SimpleDateFormat(configurationProvider.getDateFormatPattern().get()));
		}

		return new DateRenderer(configurationProvider.getLocale());
	}

	@Override
	public DateToLocalDateConverter getConverter() {
		return new DateToLocalDateConverter();
	}

	private class DateToLocalDateConverter implements Converter<Date, LocalDate> {

		@Override
		public Result<LocalDate> convertToModel(Date value, ValueContext context) {
			if (value == null) {
				return Result.ok(null);
			}

			return Result.ok(value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}

		@Override
		public Date convertToPresentation(LocalDate value, ValueContext context) {
			if (value == null) {
				return null;
			}

			return Date.from(value.atStartOfDay(configurationProvider.getTimeZoneId()).toInstant());
		}
	}
}
