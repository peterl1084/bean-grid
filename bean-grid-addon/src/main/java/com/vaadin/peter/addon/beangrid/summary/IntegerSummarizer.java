package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class IntegerSummarizer implements Summarizer<Integer> {

	@Override
	public Integer getSummary(Collection<Integer> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
	}

	@Override
	public boolean canSummarize(Collection<Integer> allAvailableValues) {
		return true;
	}
}
