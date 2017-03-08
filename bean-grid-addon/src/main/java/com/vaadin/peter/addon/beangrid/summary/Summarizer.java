package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.SummarizableColumn;

/**
 * Summarizer is top level interface for all Summarizers that work with specific
 * PROPERTY_TYPE. The implementations should be exposed as Spring Beans and
 * their PROPERTY_TYPE is used as qualifier for picking the right type of
 * summarizer automatically if it's unspecified in the
 * {@link SummarizableColumn} definition.
 * 
 * @author Peter / Vaadin
 *
 * @param <PROPERTY_TYPE>
 *            type of the property for which the summary is calculated.
 */
public interface Summarizer<PROPERTY_TYPE> {

	/**
	 * @param allAvailableValues
	 * @return calculated summary of given collection of items to be summarized.
	 *         Generally this is sum of allAvailableValues.
	 */
	String summarize(ColumnDefinition definition, Collection<PROPERTY_TYPE> allAvailableValues);

	/**
	 * Tests if this summarizer is capable of summarizing the given values. It
	 * may be that not all items can be summarized even if they are
	 * programmatically of same type, for example different currencies.
	 * 
	 * @param allAvailableValues
	 * @return true if this summarizer can determine a sum of
	 *         allAvailableValues, false otherwise.
	 */
	boolean canSummarize(ColumnDefinition definition, Collection<PROPERTY_TYPE> allAvailableValues);
}
