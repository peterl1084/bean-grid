package com.vaadin.peter.addon.beangrid.testmaterial;

import com.vaadin.peter.addon.beangrid.GridColumn;

public class SimpleBeanWithoutReadMethod {

	@GridColumn(translationKey = "id", defaultOrder = 0)
	private int id;
}
