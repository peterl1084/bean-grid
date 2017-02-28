package com.vaadin.peter.addon.beangrid;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Sets up the configuration for using Vaadin's BeanGrid add-on.
 * 
 * @author Peter / Vaadin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(BeanGridConfiguration.class)
public @interface EnableBeanGrid {

}
