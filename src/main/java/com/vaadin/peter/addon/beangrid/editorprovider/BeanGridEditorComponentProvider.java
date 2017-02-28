package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;

public interface BeanGridEditorComponentProvider<BEAN_FIELD_TYPE> {

	HasValue<?> provideEditorComponent(ColumnDefinition columnDefinition);

	default boolean requiresConversion() {
		return this instanceof BeanGridValueConvertingEditorComponentProvider;
	}

	default BeanGridValueConvertingEditorComponentProvider<?, ?> asConvertable() {
		return BeanGridValueConvertingEditorComponentProvider.class.cast(this);
	}

}
