package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToIntegerBeanConverter;

@Component
@Scope(scopeName="prototype")
public class IntegerSummarizer extends AbstractSummarizer<Integer> {

	private StringToIntegerBeanConverter converter;

	@Autowired
	public IntegerSummarizer(StringToIntegerBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public boolean canSummarize(ColumnDefinition definition, Collection<Integer> allAvailableValues) {
		return true;
	}

	@Override
	protected StringToIntegerConverter getStringConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	protected Integer doSummarize(ColumnDefinition definition, Collection<Integer> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
	}
}
