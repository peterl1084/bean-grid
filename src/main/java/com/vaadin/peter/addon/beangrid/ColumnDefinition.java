package com.vaadin.peter.addon.beangrid;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.util.StringUtils;

/**
 * ColumnDefinition describes a column used in Vaadin Grid, defined
 * by @GridColumn annotation on field or method.
 * 
 * @author Peter / Vaadin
 */
class ColumnDefinition implements Comparable<ColumnDefinition> {

	private GridColumn columnDefinitionAnnotation;
	private PropertyDescriptor descriptor;

	private static final Map<Class<?>, Class<?>> primitiveMap;

	static {
		Map<Class<?>, Class<?>> primitives = new HashMap<>();
		primitives.put(byte.class, Byte.class);
		primitives.put(short.class, Short.class);
		primitives.put(int.class, Integer.class);
		primitives.put(long.class, Long.class);
		primitives.put(float.class, Float.class);
		primitives.put(double.class, Double.class);
		primitives.put(char.class, Character.class);
		primitives.put(boolean.class, Boolean.class);
		primitiveMap = Collections.unmodifiableMap(primitives);
	}

	public ColumnDefinition(GridColumn columnDefinitionAnnotation, PropertyDescriptor descriptor) {
		this.columnDefinitionAnnotation = Objects.requireNonNull(columnDefinitionAnnotation);
		this.descriptor = Objects.requireNonNull(descriptor);
	}

	/**
	 * @return the name of the property as described in
	 *         {@link GridColumn#propertyName()} or directly as the name of the
	 *         property within the item class.
	 */
	public String getPropertyName() {
		if (StringUtils.hasText(columnDefinitionAnnotation.propertyName())) {
			return columnDefinitionAnnotation.propertyName();
		}

		return descriptor.getName();
	}

	/**
	 * @return type of the property. Will always return actual Object types, not
	 *         primitives.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class<?> getType() {
		Class propertyType = descriptor.getPropertyType();
		return Optional.ofNullable(primitiveMap.get(propertyType)).orElse(propertyType);
	}

	/**
	 * @return translation key as described in
	 *         {@link GridColumn#translationKey()}
	 */
	public String getTranslationKey() {
		return columnDefinitionAnnotation.translationKey();
	}

	/**
	 * @return order number as described in {@link GridColumn#defaultOrder()}
	 */
	public int getDefaultOrderNumber() {
		return columnDefinitionAnnotation.defaultOrder();
	}

	/**
	 * @return true if this column should be visible in the grid by default,
	 *         false otherwise. As described in
	 *         {@link GridColumn#defaultVisible()}
	 */
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
