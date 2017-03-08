package com.vaadin.peter.addon.beangrid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GridColumn is the annotation to use in item getter or fields when appropriate
 * property is to be shown in the Grid as column.
 * 
 * @author Peter / Vaadin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface GridColumn {

	/**
	 * @return Translation key of the column, if no I18N usage is intended this
	 *         should be the name of the column as is.
	 */
	String translationKey();

	/**
	 * @return true if this column can be hidden by the user, false otherwise.
	 */
	boolean defaultHidable() default true;

	/**
	 * @return true if this column should be visible by default if set to
	 *         hidable with {@link GridColumn#defaultHidable()}
	 */
	boolean defaultVisible() default true;

	/**
	 * @return the column index number in which index this column should be
	 *         shown by default.
	 */
	int defaultOrder();

	/**
	 * @return name of the property used for this column. If not defined the
	 *         property name is acquired from the annotated field or getter
	 *         method.
	 */
	String propertyName() default "";

	/**
	 * @return the {@link ColumnAlignment} based on which the textual alignment
	 *         of cell values of this column will be done.
	 */
	ColumnAlignment alignment() default ColumnAlignment.LEFT;

	/**
	 * @return Format String that should be used with this column type. The
	 *         format is highly dependent on visualizing Grid Renderer or
	 *         otherwise used {@link BeanGridValueProvider}. This attribute is
	 *         optional and system will always default to configured properties.
	 *         Defining the format here should be used only in exceptional
	 *         cases.
	 */
	String format() default "";
}
