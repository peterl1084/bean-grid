package com.vaadin.peter.addon.beangrid.editorprovider;

import java.util.Arrays;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import com.vaadin.peter.addon.beangrid.converter.ConverterBean;

/**
 * AbstractGridValueConfigurableConvertingEditorComponentProvider is an abstract
 * implementation of ConfigurableBeanGridValueConvertingEditorComponentProvider
 * which takes care of {@link ConfigurableConverter} discovery and
 * instantiation.
 * 
 * @author Peter / Vaadin
 *
 * @param <PROPERTY_TYPE>
 */
public abstract class AbstractGridNumberValueEditorComponentProvider<PROPERTY_TYPE extends Number>
		implements BeanGridValueConvertingEditorComponentProvider<String, PROPERTY_TYPE> {

	@Autowired
	private ApplicationContext appContext;

	private ConverterBean<String, PROPERTY_TYPE> converter;

	private Class<PROPERTY_TYPE> propertyType;

	public AbstractGridNumberValueEditorComponentProvider(Class<PROPERTY_TYPE> propertyType) {
		this.propertyType = Objects.requireNonNull(propertyType);
	}

	@PostConstruct
	@SuppressWarnings("unchecked")
	protected void initialize() {
		ResolvableType converterResolvable = ResolvableType.forClassWithGenerics(ConverterBean.class, String.class,
				propertyType);
		String converterBeanName = Arrays.asList(appContext.getBeanNamesForType(converterResolvable)).iterator().next();
		converter = appContext.getBean(converterBeanName, ConverterBean.class);
	}

	@Override
	public ConverterBean<String, PROPERTY_TYPE> getConverter() {
		return converter;
	}
}
