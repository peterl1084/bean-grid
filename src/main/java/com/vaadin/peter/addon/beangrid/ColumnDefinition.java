package com.vaadin.peter.addon.beangrid;

import java.beans.PropertyDescriptor;
import java.util.Objects;

import org.springframework.util.StringUtils;

class ColumnDefinition implements Comparable<ColumnDefinition> {

	private GridColumn columnDefinitionAnnotation;
	private PropertyDescriptor descriptor;

	public ColumnDefinition(GridColumn columnDefinitionAnnotation, PropertyDescriptor descriptor) {
		this.columnDefinitionAnnotation = Objects.requireNonNull(columnDefinitionAnnotation);
		this.descriptor = Objects.requireNonNull(descriptor);
	}

	public String getPropertyName() {
		if (StringUtils.hasText(columnDefinitionAnnotation.propertyName())) {
			return columnDefinitionAnnotation.propertyName();
		}

		return descriptor.getName();
	}

	public Class<?> getType() {
		return descriptor.getPropertyType();
	}

	public String getTranslationKey() {
		return columnDefinitionAnnotation.translationKey();
	}

	public int getDefaultOrderNumber() {
		return columnDefinitionAnnotation.defaultOrder();
	}

	public boolean isDefaultVisible() {
		return columnDefinitionAnnotation.defaultVisible();
	}

	@Override
	public String toString() {
		return "property: " + getPropertyName() + ", key: " + getTranslationKey() + ", default order: "
				+ getDefaultOrderNumber() + " default visible: " + isDefaultVisible();
	}

	@Override
	public int compareTo(ColumnDefinition other) {
		return this.getDefaultOrderNumber() - other.getDefaultOrderNumber();
	}
}
