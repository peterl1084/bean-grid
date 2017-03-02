package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class FloatSummarizer implements Summarizer<Float> {

	@Override
	public Float getSummary(Collection<Float> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0f;
		}

		return (float) allAvailableValues.stream().filter(Objects::nonNull).mapToDouble(Float::doubleValue).sum();
	}

	@Override
	public boolean canSummarize(Collection<Float> allAvailableValues) {
		return true;
	}
}
