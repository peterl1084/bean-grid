package com.vaadin.peter.addon.beangrid.editorprovider;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.ConfigurableConverter;

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
@Scope(scopeName = "prototype")
public abstract class AbstractGridValueConfigurableConvertingEditorComponentProvider<PROPERTY_TYPE extends Number>
		implements ConfigurableBeanGridValueConvertingEditorComponentProvider<String, PROPERTY_TYPE> {

	@Autowired
	private ApplicationContext appContext;

	private ConfigurableConverter<PROPERTY_TYPE> converter;

	private Class<PROPERTY_TYPE> propertyType;

	public AbstractGridValueConfigurableConvertingEditorComponentProvider(Class<PROPERTY_TYPE> propertyType) {
		this.propertyType = propertyType;
	}

	@PostConstruct
	@SuppressWarnings("unchecked")
	protected void initialize() {
		ResolvableType converterResolvable = ResolvableType.forClassWithGenerics(ConfigurableConverter.class,
				propertyType);
		String converterBeanName = Arrays.asList(appContext.getBeanNamesForType(converterResolvable)).iterator().next();
		converter = appContext.getBean(converterBeanName, ConfigurableConverter.class);
	}

	@Override
	public void configureConverter(ColumnDefinition columnDefinition) {
		converter.configureWithPattern(columnDefinition.getFormat().orElse(null));
	}

	@Override
	public ConfigurableConverter<PROPERTY_TYPE> getConverter() {
		return converter;
	}
}
