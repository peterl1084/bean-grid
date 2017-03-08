package com.vaadin.peter.addon.beangrid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GridSelectionMode defines the selection mode of the Grid that should be
 * applied when Grid is showing data from a bean of type where this annotation
 * is defined.
 * 
 * @author Peter / Vaadin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GridSelectionMode {

	/**
	 * @return selected GridMode or {@link GridMode#NONE} default if not
	 *         specified.
	 */
	GridMode value() default GridMode.NONE;
}
