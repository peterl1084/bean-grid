package com.vaadin.peter.addon.beangrid;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.vaadin.peter.addon.beangrid.summary.SummarizableColumnStaticText;

/**
 * ColumnDefinition describes a column used in Vaadin Grid, defined
 * by @GridColumn annotation on field or method.
 * 
 * @author Peter / Vaadin
 */
public class ColumnDefinition implements Comparable<ColumnDefinition> {

	private GridColumn columnDefinitionAnnotation;
	private EditableColumn editorDefinition;
	private SummarizableColumn summarizableDefinition;
	private SummarizableColumnStaticText summarizableStaticDefinition;

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

	public ColumnDefinition(PropertyDescriptor descriptor, Annotation... definitions) {
		this.descriptor = Objects.requireNonNull(descriptor);

		columnDefinitionAnnotation = assignAnnotation(GridColumn.class, definitions);
		editorDefinition = assignAnnotation(EditableColumn.class, definitions);
		summarizableDefinition = assignAnnotation(SummarizableColumn.class, definitions);
		summarizableStaticDefinition = assignAnnotation(SummarizableColumnStaticText.class, definitions);

		ifEditableAssertWritableOrThrow();
		assertOnlyOneSummarizableDefinedOrThrow();
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
	public Class<?> getPropertyType() {
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
	 * @return true if this column is equipped with {@link EditableColumn}
	 *         annotation and has write method (setter) defined.
	 */
	public boolean isEditable() {
		return editorDefinition != null && descriptor.getWriteMethod() != null;
	}

	/**
	 * @return true if this column has the {@link SummarizableColumn} or
	 *         {@link SummarizableColumnStaticText} annotation in place, false
	 *         otherwise.
	 */
	public boolean isSummarizable() {
		return summarizableDefinition != null || summarizableStaticDefinition != null;
	}

	/**
	 * @return true if this column has a write method and if the parameter type
	 *         of the write method is primitive, false otherwise.
	 */
	public boolean isPrimitiveTypeWriteMethod() {
		if (descriptor.getWriteMethod() == null) {
			return false;
		}

		return Arrays.asList(getWriteMethod().getParameterTypes()).iterator().next().isPrimitive();
	}

	/**
	 * @return Method for reading the property value of a cell within this
	 *         column.
	 */
	public Method getReadMethod() {
		return descriptor.getReadMethod();
	}

	/**
	 * @return Method for writing the property value of a cell within this
	 *         column.
	 */
	public Method getWriteMethod() {
		return descriptor.getWriteMethod();
	}

	/**
	 * @return the alignment associated with this column.
	 */
	public ColumnAlignment getColumnAlignment() {
		return columnDefinitionAnnotation.alignment();
	}

	/**
	 * @return true if this {@link ColumnDefinition} has static summarizer
	 *         configured with {@link SummarizableColumnStaticText} annotation,
	 *         false otherwise.
	 */
	public boolean isStaticTextSummarizable() {
		return summarizableStaticDefinition != null;
	}

	/**
	 * @return the tranlation key provided with
	 *         {@link SummarizableColumnStaticText#translationKey()} if present,
	 *         otherwise null.
	 */
	public String getStaticTextSummarizerTranslationKey() {
		if (summarizableStaticDefinition == null) {
			return null;
		}

		return summarizableStaticDefinition.translationKey();
	}

	/**
	 * @return Optional of format string that should be used for formatting the
	 *         cell value. Most commonly used with number or date formats but
	 *         can also be used to pad strings in textual fields etc.
	 */
	public Optional<String> getFormat() {
		if (StringUtils.hasText(columnDefinitionAnnotation.format())) {
			return Optional.of(columnDefinitionAnnotation.format());
		}

		return Optional.empty();
	}

	/**
	 * Finds an annotation of given type from the given array of annotations.
	 * 
	 * @param annotationType
	 * @param definitions
	 * @return Annotation matching given annotationType from the given array of
	 *         definitions or null if no such annotation is found.
	 */
	private <T extends Annotation> T assignAnnotation(Class<T> annotationType, Annotation[] definitions) {
		if (definitions == null || definitions.length == 0) {
			return null;
		}

		return Arrays.asList(definitions).stream()
				.filter(annotation -> annotationType.isAssignableFrom(annotation.annotationType())).findFirst()
				.map(annotation -> annotationType.cast(annotation)).orElse(null);
	}

	/**
	 * @throws ColumnDefinitionException
	 *             if marked as editable with {@link EditableColumn} but no
	 *             write method present
	 */
	private void ifEditableAssertWritableOrThrow() throws ColumnDefinitionException {
		if (descriptor.getWriteMethod() == null && editorDefinition != null) {
			throw new ColumnDefinitionException("Column with property '" + getPropertyName()
					+ "' has been marked as editable but it doesn't have corresponding write (setter) method.");
		}

		if (descriptor.getWriteMethod() != null && descriptor.getWriteMethod().getParameterCount() != 1) {
			throw new ColumnDefinitionException("Write (setter) method for " + getPropertyName()
					+ " has more than one parameter, it's expected to only have one");
		}
	}

	/**
	 * Tests that this definition doesn't have both types of summarizers
	 * defined, but only either one if any. Otherwise will throw.
	 * 
	 * @throws ColumnDefinitionException
	 *             if this column definition has more than one type of
	 *             summarizer configured.
	 */
	private void assertOnlyOneSummarizableDefinedOrThrow() throws ColumnDefinitionException {
		if (summarizableDefinition != null && summarizableStaticDefinition != null) {
			throw new ColumnDefinitionException(getPropertyName()
					+ " has more than one summarizer definition, it should only have either one or none.");
		}
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
