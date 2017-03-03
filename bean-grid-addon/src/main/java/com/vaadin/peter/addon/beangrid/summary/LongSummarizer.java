package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToLongBeanConverter;

@Component
@Scope(scopeName="prototype")
public class LongSummarizer extends AbstractSummarizer<Long> {

	private StringToLongBeanConverter converter;

	@Autowired
	public LongSummarizer(StringToLongBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public boolean canSummarize(ColumnDefinition definition, Collection<Long> allAvailableValues) {
		return true;
	}

	@Override
	protected StringToLongConverter getStringConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	protected Long doSummarize(ColumnDefinition definition, Collection<Long> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0l;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToLong(Long::longValue).sum();
	}
}
