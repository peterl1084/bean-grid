# BeanGrid

BeanGrid is Vaadin 8 add-on for applications using Spring.

## Configuration

* Enable using BeanGrid by declaring `@EnableBeanGrid` in your application's @Configuration class.
* Annotate your bean with `@GridColumn` and optionally `@EditableColumn` annotations. You can place the annotations on fields or read methods.
* Declare Grid with bean type by directly @Autowire(ing) it.

## Compatibility

* By default the configured Grid supports all basic data types such as primitives and their corresponding Object values, as well as LocalDate and Date. The `BeanGridValueProvider` may be implemented with corresponding data type to add support for additional data types.

* In edit mode the Grid's row is replaced with corresponding editor components. The editor supports all the same basic data types as reading and is able to run common conversions between types. Data type for which there is a direct Vaadin component equivalent can be directly mapped through the `BeanGridValueProvider`, for types requiring conversion, such as String to Number based fields, the `BeanGridValueConvertingEditorComponentProvider` should be used. BeanGrid will autodetect the component provider beans and will use their generic types to determine which provider should be used.

* If you're implementing completely new type of editor component, implement `BeanGridValueProvider` or `BeanGridValueConvertingEditorComponentProvider` and map it with the bean field type.

* If you need to provide I18N support for the column translation keys or `BeanGridValueProvider` implementations @Autowire the `com.vaadin.peter.addon.beangrid.I18NProvider` and have it implemented as Spring Bean in your application. The BeanGrid will use that (optional) provider for resolving the translation keys defined in the `@GridColumn#translationKey`

## Things to be added in near future

* Support for defining own editor component directly in `@EditableColumn`
* Ability to define validators on bean level
* Ability to use JSR303 (bean validation) annotations with the beans.
