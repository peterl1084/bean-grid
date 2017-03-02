package com.vaadin.peter.addon.beangrid.summary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.summary.Summarizer.DefaultNoOpSummarizer;

/**
 * SummarizableColumn annotation can be used together with {@link GridColumn}
 * annotation for further specifying that the footer of the column should show a
 * summary of the columns data. What kind of summary is in question completely
 * depends on the {@link Summarizer} used. If unspecified the BeanGrid will
 * automatically look for a {@link Summarizer} that matches the type of the
 * column.
 * 
 * @author Peter / Vaadin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface SummarizableColumn {

	Class<? extends Summarizer<?>> summarizer() default DefaultNoOpSummarizer.class;
}
