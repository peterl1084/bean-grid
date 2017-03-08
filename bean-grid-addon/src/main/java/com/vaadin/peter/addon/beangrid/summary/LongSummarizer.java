package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;

@Component
public class LongSummarizer extends AbstractSummarizer<Long> {

	public LongSummarizer() {
		super(Long.class);
	}

	@Override
	public boolean canSummarize(ColumnDefinition definition, Collection<Long> allAvailableValues) {
		return true;
	}

	@Override
	protected Long doSummarize(ColumnDefinition definition, Collection<Long> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0l;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToLong(Long::longValue).sum();
	}
}
