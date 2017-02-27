package com.vaadin.peter.addon.beangrid.testmaterial;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class SimpleBean {

	private String firstname;
	private String lastname;

	@GridColumn(translationKey = "first.name", defaultOrder = 0, defaultVisible = true)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@GridColumn(translationKey = "last.name", defaultOrder = 1, defaultVisible = true)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
