package com.vaadin.peter.addon.beangrid.valueprovider;

import org.springframework.stereotype.Component;

import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.renderers.TextRenderer;

/**
 * BeanGridDefaultValueProvider usable with all types of Objects. The rendering
 * relies on toString method of the object and uses default {@link TextRenderer}
 * for showing the value in Grid.
 * 
 * @author Peter / Vaadin
 */
@Component
public class BeanGridDefaultValueProvider implements BeanGridValueProvider<Object> {

	@Override
	public TextRenderer getRenderer(ColumnDefinition definition) {
		return new TextRenderer();
	}
}
