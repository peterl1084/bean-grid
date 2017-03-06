package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;
import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;

@Component
@Scope(scopeName = "prototype")
public class DoubleSummarizer extends AbstractSummarizer<Double> {

	public DoubleSummarizer() {
		super(Double.class);
	}

	@Override
	public boolean canSummarize(ColumnDefinition definition, Collection<Double> allAvailableValues) {
		return true;
	}

	@Override
	protected Double doSummarize(ColumnDefinition definition, Collection<Double> allAvailableValues) {
		if (allAvailableValues == null) {
			return 0.0;
		}

		return allAvailableValues.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
	}
}
