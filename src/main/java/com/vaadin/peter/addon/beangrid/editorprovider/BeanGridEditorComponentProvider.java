package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.ui.Component;

public interface BeanGridEditorComponentProvider<FIELD_TYPE> {

	<EDITOR extends Component & HasValue<FIELD_TYPE>> EDITOR provideEditorComponent(ColumnDefinition columnDefinition);
}
