package com.vaadin.peter.addon.beangrid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SummarizableColumn annotation can be used together with {@link GridColumn}
 * annotation for further specifying that the footer of the column should show a
 * summary of the columns data.
 * 
 * @author Peter / Vaadin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface SummarizableColumn {

}
