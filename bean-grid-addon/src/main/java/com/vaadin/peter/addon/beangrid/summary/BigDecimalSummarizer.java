package com.vaadin.peter.addon.beangrid.summary;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class BigDecimalSummarizer implements Summarizer<BigDecimal> {

	@Override
	public BigDecimal getSummary(Collection<BigDecimal> allAvailableValues) {
		if (allAvailableValues == null) {
			return BigDecimal.ZERO;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public boolean canSummarize(Collection<BigDecimal> allAvailableValues) {
		return true;
	}
}
