package com.vaadin.peter.addon.beangrid.summary;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;

@Component
public class BigDecimalSummarizer extends AbstractSummarizer<BigDecimal> {

	public BigDecimalSummarizer() {
		super(BigDecimal.class);
	}

	@Override
	public boolean canSummarize(ColumnDefinition definition, Collection<BigDecimal> allAvailableValues) {
		return true;
	}

	@Override
	protected BigDecimal doSummarize(ColumnDefinition definition, Collection<BigDecimal> allAvailableValues) {
		if (allAvailableValues == null) {
			return BigDecimal.ZERO;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
