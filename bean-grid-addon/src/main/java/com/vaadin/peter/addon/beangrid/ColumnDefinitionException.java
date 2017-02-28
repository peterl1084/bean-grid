package com.vaadin.peter.addon.beangrid;

/**
 * ColumnDefinitionException can be used with all error situations regarding
 * {@link GridColumn} definitions.
 * 
 * @author Peter / Vaadin
 */
public class ColumnDefinitionException extends RuntimeException {
	public ColumnDefinitionException(String message) {
		super(message);
	}

	public ColumnDefinitionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
