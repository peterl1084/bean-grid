package com.vaadin.peter.addon.beangrid.testmaterial;

import java.time.LocalDate;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class AbstractBaseBean {

	private int id;
	private LocalDate birthDate;

	public AbstractBaseBean() {
		birthDate = LocalDate.of(1984, 10, 21);
	}

	@GridColumn(translationKey = "id", defaultOrder = 0, defaultVisible = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@GridColumn(translationKey = "birth.date", defaultOrder = 1)
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}
