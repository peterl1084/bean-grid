package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class DoubleSummarizer implements Summarizer<Double> {

	@Override
	public Double getSummary(Collection<Double> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0.0;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
	}

	@Override
	public boolean canSummarize(Collection<Double> allAvailableValues) {
		return true;
	}
}
