package com.vaadin.peter.addon.beangrid.summary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.peter.addon.beangrid.GridColumn;

/**
 * SummarizableColumnStaticText may be used together with {@link GridColumn}
 * annotation for defining static summary text which should be shown at the
 * footer cell of the particular column. The static text is statically fixed by
 * providing the translationKey via
 * {@link SummarizableColumnStaticText#translationKey()}
 * 
 * @author Peter / Vaadin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface SummarizableColumnStaticText {

	/**
	 * @return the translation key used for retrieving the translation for this
	 *         static text.
	 */
	String translationKey();
}
