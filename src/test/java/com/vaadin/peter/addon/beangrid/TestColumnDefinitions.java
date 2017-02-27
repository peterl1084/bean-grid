package com.vaadin.peter.addon.beangrid;

import java.beans.IntrospectionException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.peter.addon.beangrid.testmaterial.SimpleBean;
import com.vaadin.peter.addon.beangrid.testmaterial.SimpleBeanWithBaseClass;

public class TestColumnDefinitions {

	@Test
	public void testColumnDefinitionLookup_simpleBean_definitionsFound() throws IntrospectionException {
		List<ColumnDefinition> columnDefinitions = ColumnDefinitionTools.discoverColumnDefinitions(SimpleBean.class);
		Assert.assertEquals(2, columnDefinitions.size());

		Assert.assertEquals("first.name", columnDefinitions.get(0).getTranslationKey());
		Assert.assertEquals(0, columnDefinitions.get(0).getDefaultOrderNumber());
		Assert.assertEquals("firstname", columnDefinitions.get(0).getPropertyName());
		Assert.assertEquals(String.class, columnDefinitions.get(0).getType());

		Assert.assertEquals("last.name", columnDefinitions.get(1).getTranslationKey());
		Assert.assertEquals(1, columnDefinitions.get(1).getDefaultOrderNumber());
		Assert.assertEquals("lastname", columnDefinitions.get(1).getPropertyName());
		Assert.assertEquals(String.class, columnDefinitions.get(1).getType());
	}

	@Test
	public void testColumnDwefinitionLookup_simpleBeanExtendedFromBaseClass_definitionsFound()
			throws IntrospectionException {
		List<ColumnDefinition> columnDefinitions = ColumnDefinitionTools
				.discoverColumnDefinitions(SimpleBeanWithBaseClass.class);
		Assert.assertEquals(4, columnDefinitions.size());

		Assert.assertEquals("id", columnDefinitions.get(0).getTranslationKey());
		Assert.assertEquals(0, columnDefinitions.get(0).getDefaultOrderNumber());
		Assert.assertEquals("id", columnDefinitions.get(0).getPropertyName());
		Assert.assertEquals(Integer.class, columnDefinitions.get(0).getType());

		Assert.assertEquals("birth.date", columnDefinitions.get(1).getTranslationKey());
		Assert.assertEquals(1, columnDefinitions.get(1).getDefaultOrderNumber());
		Assert.assertEquals("birthDate", columnDefinitions.get(1).getPropertyName());
		Assert.assertEquals(LocalDate.class, columnDefinitions.get(1).getType());

		Assert.assertEquals("first.name", columnDefinitions.get(2).getTranslationKey());
		Assert.assertEquals(2, columnDefinitions.get(2).getDefaultOrderNumber());
		Assert.assertEquals("firstname", columnDefinitions.get(2).getPropertyName());
		Assert.assertEquals(String.class, columnDefinitions.get(2).getType());

		Assert.assertEquals("last.name", columnDefinitions.get(3).getTranslationKey());
		Assert.assertEquals(3, columnDefinitions.get(3).getDefaultOrderNumber());
		Assert.assertEquals("lastname", columnDefinitions.get(3).getPropertyName());
		Assert.assertEquals(String.class, columnDefinitions.get(3).getType());
	}
}
