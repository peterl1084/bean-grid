package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.Converter;

public interface BeanGridValueConvertingEditorComponentProvider<FIELD_TYPE, BEAN_FIELD_TYPE>
		extends BeanGridEditorComponentProvider<BEAN_FIELD_TYPE> {

	Converter<FIELD_TYPE, BEAN_FIELD_TYPE> getConverter();

}
