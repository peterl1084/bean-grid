package com.vaadin.peter.addon.beangrid.testmaterial;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class SimpleBeanWithOnlyWriteMethod {

	@GridColumn(translationKey = "id", defaultOrder = 0)
	private int id;

	public void setId(int id) {
		this.id = id;
	}
}
