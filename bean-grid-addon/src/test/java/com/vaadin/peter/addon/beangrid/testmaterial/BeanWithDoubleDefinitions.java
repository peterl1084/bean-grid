package com.vaadin.peter.addon.beangrid.testmaterial;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class BeanWithDoubleDefinitions {

	@GridColumn(translationKey = "first.name", defaultOrder = 0, defaultVisible = true)
	private String firstname;

	@GridColumn(translationKey = "first.name", defaultOrder = 0, defaultVisible = true)
	public String getFirstname() {
		return firstname;
	}
}
