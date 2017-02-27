package com.vaadin.peter.addon.beangrid.editorprovider;

import com.vaadin.data.HasValue;
import com.vaadin.peter.addon.beangrid.ColumnDefinition;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

@SpringComponent
public class DefaultBeanGridEditorComponentProvider implements BeanGridEditorComponentProvider {

	@Override
	public <EDITOR extends Component & HasValue<?>> EDITOR provideEditorComponent(ColumnDefinition columnDefinition) {
		return (EDITOR) new TextField();
	}
}
