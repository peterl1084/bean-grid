package com.vaadin.peter.addon.beangrid.converter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;

/**
 * PrototypeConverter is a marker annotation for all such {@link Converter}s
 * that are expected to be Spring Beans in Prototype scope. This annotation's
 * purpose is to add clarity for converter definitions.
 * 
 * @author Peter / Vaadin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope(scopeName = "prototype")
public @interface PrototypeConverter {

}
