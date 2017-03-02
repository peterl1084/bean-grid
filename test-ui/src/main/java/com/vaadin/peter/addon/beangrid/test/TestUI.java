package com.vaadin.peter.addon.beangrid.test;

import java.math.BigDecimal;
import java.time.LocalDate;

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
	private Grid<TestBean> testGrid;

	@Override
	protected void init(VaadinRequest request) {
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);

		testGrid.setSizeFull();
		testGrid.getEditor().setEnabled(true);

		TestBean a = new TestBean("Mr", "Vaadin", LocalDate.of(2000, 1, 1));
		a.setBdValue(BigDecimal.valueOf(100.25));

		TestBean b = new TestBean("Mr", "GWT", LocalDate.of(2000, 1, 1));
		b.setBdValue(BigDecimal.valueOf(38.50));

		testGrid.setItems(a, b);

		layout.addComponent(testGrid);

		setContent(layout);
	}
}
