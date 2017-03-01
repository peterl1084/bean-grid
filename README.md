# BeanGrid

BeanGrid is Vaadin 8 add-on for applications using Spring.

## Configuration

* Enable using BeanGrid by declaring `@EnableBeanGrid` in your application's @Configuration class.
* Annotate your bean with `@GridColumn` and optionally `@EditableColumn` annotations. You can place the annotations on fields or read methods.
* Declare Grid with bean type by directly @Autowire(ing) it.


