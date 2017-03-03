package com.vaadin.peter.addon.beangrid.summary;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.StringToBigDecimalBeanConverter;

@Component
@Scope(scopeName = "prototype")
public class BigDecimalSummarizer extends AbstractSummarizer<BigDecimal> {

	private StringToBigDecimalBeanConverter converter;

	@Autowired
	public BigDecimalSummarizer(StringToBigDecimalBeanConverter converter) {
		this.converter = converter;
	}

	@Override
	public boolean canSummarize(ColumnDefinition definition, Collection<BigDecimal> allAvailableValues) {
		return true;
	}

	@Override
	protected StringToBigDecimalConverter getStringConverter(ColumnDefinition definition) {
		return converter.configureWithPattern(definition.getFormat().orElse(null));
	}

	@Override
	protected BigDecimal doSummarize(ColumnDefinition definition, Collection<BigDecimal> allAvailableValues) {
		if (allAvailableValues == null) {
			return BigDecimal.ZERO;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
