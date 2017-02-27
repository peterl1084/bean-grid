package com.vaadin.peter.addon.beangrid;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.vaadin.peter.addon.beangrid.editorprovider.BeanGridEditorComponentProvider;

/**
 * ColumnDefinition describes a column used in Vaadin Grid, defined
 * by @GridColumn annotation on field or method.
 * 
 * @author Peter / Vaadin
 */
public class ColumnDefinition implements Comparable<ColumnDefinition> {

	private GridColumn columnDefinitionAnnotation;
	private EditableColumn editorDefinitionAnnotation;

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

	public ColumnDefinition(GridColumn columnDefinitionAnnotation, EditableColumn editorDefinitionAnnotation,
			PropertyDescriptor descriptor) {
		this.columnDefinitionAnnotation = Objects.requireNonNull(columnDefinitionAnnotation);
		this.editorDefinitionAnnotation = editorDefinitionAnnotation;
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

	/**
	 * @return true if this column can by default be hidden by the user, false
	 *         otherwise.
	 */
	public boolean isDefaultHidable() {
		return columnDefinitionAnnotation.defaultHidable();
	}

	/**
	 * @return Optional of {@link BeanGridEditorComponentProvider} which will
	 *         later be queried for component capable of providing editor
	 *         component for this column.
	 */
	public Optional<Class<? extends BeanGridEditorComponentProvider>> getEditorComponentProviderType() {
		if (editorDefinitionAnnotation != null) {
			return Optional.of(editorDefinitionAnnotation.value());
		}

		return Optional.empty();
	}

	/**
	 * @return true if this column is editable by having its
	 *         {@link BeanGridEditorComponentProvider} configured, false
	 *         otherwise.
	 */
	public boolean isEditable() {
		return getEditorComponentProviderType().isPresent();
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

	public Method getReadMethod() {
		return descriptor.getReadMethod();
	}

	public Method getWriteMethod() {
		return descriptor.getWriteMethod();
	}
}
