package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class LongSummarizer implements Summarizer<Long> {

	@Override
	public Long getSummary(Collection<Long> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0l;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToLong(Long::longValue).sum();
	}

	@Override
	public boolean canSummarize(Collection<Long> allAvailableValues) {
		return true;
	}
}
