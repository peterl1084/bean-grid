package com.vaadin.peter.addon.beangrid;

/**
 * GridMode lists all selection modes of the Vaadin Grid. This Enum is redefined
 * so that the actual dependency to Vaadin can be omitted.
 * 
 * @author Peter / Vaadin
 */
public enum GridMode {

	/**
	 * Non-selectable.
	 */
	NONE,

	/**
	 * Single-row-selectable.
	 */
	SINGLE,

	/**
	 * Multi-row-selectable.
	 */
	MULTI;
}
