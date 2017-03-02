package com.vaadin.peter.addon.beangrid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;

/**
 * GridSelectionMode defines the selection mode of the {@link Grid} that should
 * be applied when grid is showing data from a bean of type where this
 * annotation is defined.
 * 
 * @author Peter / Vaadin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GridSelectionMode {

	/**
	 * @return selected {@link SelectionMode} or {@link SelectionMode#NONE} by
	 *         default if not specified.
	 */
	SelectionMode value() default SelectionMode.NONE;
}
