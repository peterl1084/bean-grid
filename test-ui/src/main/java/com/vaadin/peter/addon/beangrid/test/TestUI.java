package com.vaadin.peter.addon.beangrid.test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("test-theme")
@SpringUI
public class TestUI extends UI {

	@Autowired
	private Grid<Customer> testGrid;

	@Override
	protected void init(VaadinRequest request) {
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);

		testGrid.setSizeFull();
		testGrid.getEditor().setEnabled(true);

		testGrid.setItems(getCustomers());

		layout.addComponent(testGrid);

		setContent(layout);
	}

	private List<Customer> getCustomers() {
		Customer a = new Customer(0, "John", "Doe");
		a.setOpenInvoiceTotal(BigDecimal.valueOf(500.25));
		Customer b = new Customer(1, "Jack", "Smith");
		b.setOpenInvoiceTotal(BigDecimal.valueOf(250.45));

		return Arrays.asList(a, b);
	}
}
