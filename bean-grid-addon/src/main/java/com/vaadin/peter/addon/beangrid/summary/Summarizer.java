package com.vaadin.peter.addon.beangrid.summary;

import java.util.Collection;

/**
 * Summarizer is top level interface for all Summarizers that work with specific
 * BEAN_FIELD_TYPEs. The implementations should be exposed as Spring Beans and
 * their BEAN_FIELD_TYPE is used as qualifier for picking the right type of
 * summarizer automatically if it's unspecified in the
 * {@link SummarizableColumn} definition.
 * 
 * @author Peter / Vaadin
 *
 * @param <BEAN_FIELD_TYPE>
 *            type of the property for which the summary is calculated.
 */
public interface Summarizer<BEAN_FIELD_TYPE> {

	/**
	 * @param allAvailableValues
	 * @return calculated summary of given collection of items to be summarized.
	 *         Generally this is sum of allAvailableValues. Returns null if
	 */
	BEAN_FIELD_TYPE getSummary(Collection<BEAN_FIELD_TYPE> allAvailableValues);

	/**
	 * Tests if this summarizer is capable of summarizing the given values. It
	 * may be that not all items can be summarized even if they are
	 * programmatically of same type, for example different currencies.
	 * 
	 * @param allAvailableValues
	 * @return true if this summarizer can determine a sum of
	 *         allAvailableValues, false otherwise.
	 */
	boolean canSummarize(Collection<BEAN_FIELD_TYPE> allAvailableValues);

	/**
	 * DefaultNoOpSummarizer is hidden internal default value for
	 * {@link SummarizableColumn}'s summarizer attribute. It is not intended to
	 * be used anywhere ever.
	 * 
	 * @author Peter / Vaadin
	 */
	class DefaultNoOpSummarizer implements Summarizer<Object> {

		@Override
		public Object getSummary(Collection<Object> allAvailableValues) {
			return null;
		}

		@Override
		public boolean canSummarize(Collection<Object> allAvailableValues) {
			return false;
		}
	}
}
