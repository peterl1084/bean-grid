package com.vaadin.peter.addon.beangrid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.peter.addon.beangrid.editorprovider.BeanGridEditorComponentProvider;
import com.vaadin.ui.Grid;

/**
 * EditableColumn is an annotation that is intended to be used together with
 * {@link GridColumn} on such fields that are to be editable within {@link Grid}
 * when editor functionality is enabled.
 * 
 * @author Peter / Vaadin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface EditableColumn {

	/**
	 * @return the type of the {@link BeanGridEditorComponentProvider} that is
	 *         intended to be used as the provider that instantiates the editor
	 *         components.
	 */
	Class<? extends BeanGridEditorComponentProvider> value() default BeanGridEditorComponentProvider.class;
}
