package com.vaadin.peter.addon.beangrid.testmaterial;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class SimpleBeanWithBaseClass extends AbstractBaseBean {

	private String firstname;
	private String lastname;

	@GridColumn(translationKey = "first.name", defaultOrder = 2, defaultVisible = true)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@GridColumn(translationKey = "last.name", defaultOrder = 3, defaultVisible = true)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
