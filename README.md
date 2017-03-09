# BeanGrid

BeanGrid is Vaadin 8 add-on for applications using Spring. BeanGrid provides @Autowired capable Grid with specified item type that describes the Grid's content. The item may be annotated with various annotations, such as:

* `@GridColumn`
* `@EditableColumn`
* `@Summarizable`
* `@GridSelectionMode`

BeanGrid provides Spring @Configuration that automatically sets up the Grid with configuration given in the Item type.

## Usage

Enable using BeanGrid by declaring `@EnableBeanGrid` in your application's @Configuration class. You can then use @Autowired to instantiate Grid in  your application.

The Generic type parameter of Grid allows you to specify which Item type the Grid should be showing. Annotate your Item's (=bean's) fields or read methods (=getters) with 

* `@GridColumn`
* `@EditableColumn`
* `@Summarizable`

## Displaying data in the Grid

By default the configured Grid supports all basic data types such as primitives and their corresponding Object values, as well as LocalDate and Date. The `BeanGridValueProvider` may be implemented with corresponding data type to add support for additional data types.

If the property type within the Item (bean) is something other than Grid and its default Renderers may display developer can implement `BeanGridConvertingValueProvider` that takes the types of the property as well as the renderer.

For special types of properties that could require Html rendering, the `BeanGridHtmlValueProvider` may be used which always assumes HtmlRenderer being used. All BeanGridValueProviders of BeanGrid project are Spring Beans.

## Editing data in the Grid

Use `@EditableColumn` annotation on field or read method (getter) together with `@GridColumn` to make column editable in Grid. The configuration will attempt to auto detect the editor type based on the property type within the item (bean).

Editor support is implemented based on `BeanGridEditorComponentProvider` and comes with support for all basic data types and primitives as well as Date and LocalDate. If the field type of the editor component is different from the type of the property within the bean, `BeanGridValueConvertingEditorComponentProvider` may be implemented to provide appropriate converter and editor component. All BeanGridEditorComponentProviders of BeanGrid project are Spring Beans.

## Summarizing data in the Grid

BeanGrid configuration currently supports `@Summarizable` together with any `@GridColumn` annotated column. Having Summarizable defined the column's numerical value is automatically summed up. The summarizers are defined as implementations of `Summarizer` interface. New summarizers can be implemented on need basis. The summarizer will always produce String result. The possible formatting applied with `@GridColumn` is also used in the summarizer as summarizers also perform String conversion with converters that are format-aware. All Summarizers of BeanGrid project are Spring Beans.

## Value Conversion

Various parts of BeanGrid functionality depends on Vaadin's Conversion features. As there are currently some limitations in Vaadin Framework 8 the converters have been partially rewritten within BeanGrid project. All converters implement the regular `Converter` interface but provide their own implementations that allow passing ValueContext more efficiently. All Converters of BeanGrid project are Spring Beans.

## Integrating BeanGrid with rest of the application

Right now BeanGrid requires access to mechanism capable of providing translation keys, date formatting patterns as well as system system / user Locale. Support can be implemented by instantiating `GridConfigurationProvider` interface as Bean. The interface contains many default methods but all the methods can be overwritten with application's preference. Based on scoping of GridConfigurationProvider the configuration can be application or user specific, that is for developers to decide.
