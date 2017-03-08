package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;

@Component
public class IntegerSummarizer extends AbstractSummarizer<Integer> {

	public IntegerSummarizer() {
		super(Integer.class);
	}

	@Override
	public boolean canSummarize(ColumnDefinition definition, Collection<Integer> allAvailableValues) {
		return true;
	}

	@Override
	protected Integer doSummarize(ColumnDefinition definition, Collection<Integer> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
	}
}
