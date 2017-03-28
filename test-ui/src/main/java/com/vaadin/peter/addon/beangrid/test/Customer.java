package com.vaadin.peter.addon.beangrid.test;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.peter.addon.beangrid.ColumnAlignment;
import com.vaadin.peter.addon.beangrid.EditableColumn;
import com.vaadin.peter.addon.beangrid.GridColumn;
import com.vaadin.peter.addon.beangrid.SummarizableColumn;

public class Customer {

	@GridColumn(defaultOrder = 0, translationKey = "id")
	private long id;

	@GridColumn(defaultOrder = 1, translationKey = "firstName")
	@EditableColumn
	private String firstName;

	@GridColumn(defaultOrder = 2, translationKey = "lastName")
	private String lastName;

	@GridColumn(defaultOrder = 3, translationKey = "openInvoiceTotal", alignment = ColumnAlignment.RIGHT)
	@SummarizableColumn
	private BigDecimal openInvoiceTotal;

	@JsonCreator
	public Customer(@JsonProperty("id") int id, @JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getOpenInvoiceTotal() {
		return openInvoiceTotal;
	}

	public void setOpenInvoiceTotal(BigDecimal openInvoiceTotal) {
		this.openInvoiceTotal = openInvoiceTotal;
	}
}
