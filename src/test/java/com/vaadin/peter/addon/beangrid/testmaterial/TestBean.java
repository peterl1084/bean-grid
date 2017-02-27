package com.vaadin.peter.addon.beangrid.testmaterial;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class TestBean extends AbstractBaseBean {

	private String firstName;

	private String lastName;

	@GridColumn(translationKey = "Age", defaultOrder = 2)
	private Long age;

	@GridColumn(translationKey = "Age Primitive", defaultOrder = 3)
	private int agePrimitive;

	@GridColumn(translationKey = "First Name", defaultOrder = 4)
	public String getFirstName() {
		return firstName;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	@GridColumn(translationKey = "Ages", defaultOrder = 5)
	public Long getAge() {
		return age;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@GridColumn(translationKey = "Last Name", defaultOrder = 5)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
