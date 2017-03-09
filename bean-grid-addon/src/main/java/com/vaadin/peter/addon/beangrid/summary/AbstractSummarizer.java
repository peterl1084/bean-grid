package com.vaadin.peter.addon.beangrid.summary;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;

import com.vaadin.data.ValueContext;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.peter.addon.beangrid.converter.ConverterBean;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * AbstractSummarizer is abstract implementation of {@link Summarizer} which
 * sets up converter beans if necessary.
 * 
 * @author Peter / Vaadin
 *
 * @param <PROPERTY_TYPE>
 */
public abstract class AbstractSummarizer<PROPERTY_TYPE> implements Summarizer<PROPERTY_TYPE>, ApplicationContextAware {

	private ApplicationContext appContext;

	private Class<PROPERTY_TYPE> propertyType;

	private ConverterBean<String, PROPERTY_TYPE> converter;

	public AbstractSummarizer(Class<PROPERTY_TYPE> propertyType) {
		this.propertyType = propertyType;
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
	public String summarize(ColumnDefinition definition, Collection<PROPERTY_TYPE> allAvailableValues) {
		PROPERTY_TYPE summaryValue = doSummarize(definition, allAvailableValues);
		return converter.convertToPresentation(summaryValue, new ValueContextDelegate(definition));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	protected abstract PROPERTY_TYPE doSummarize(ColumnDefinition definition,
			Collection<PROPERTY_TYPE> allAvailableValues);

	private static class ValueContextDelegate extends ValueContext {
		private final ColumnDefinition definition;

		public ValueContextDelegate(ColumnDefinition definition) {
			this.definition = definition;
		}

		// :(
		@Override
		public Optional<Component> getComponent() {
			return Optional.of(new AbstractComponent() {
				@Override
				public Object getData() {
					return definition;
				}
			});
		}
	}
}
