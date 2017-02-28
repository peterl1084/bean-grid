package com.vaadin.peter.addon.beangrid.testmaterial;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class SimpleBeanFieldDefinitions {

	@GridColumn(translationKey = "first.name", defaultOrder = 0)
	private String firstname;

	@GridColumn(translationKey = "last.name", defaultOrder = 1)
	private String lastname;

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
}
