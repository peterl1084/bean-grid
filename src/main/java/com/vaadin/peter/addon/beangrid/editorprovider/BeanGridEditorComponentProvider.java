package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

/**
 * BeanGridEditorComponentProvider is the base interface for all factories
 * capable of providing editor {@link Component}s for Vaadin {@link Grid}. The
 * implementing classes should be exposable as Spring Beans.
 * 
 * @author Peter / Vaadin
 */
public interface BeanGridEditorComponentProvider {

	/**
	 * @param columnDefinition
	 *            describing for which column the editor is being looked for.
	 * @return editor component capable of having a value with {@link HasValue}
	 *         that can be associated with the property value of the item.
	 */
	<EDITOR extends Component & HasValue<?>> EDITOR provideEditorComponent(ColumnDefinition columnDefinition);
}
