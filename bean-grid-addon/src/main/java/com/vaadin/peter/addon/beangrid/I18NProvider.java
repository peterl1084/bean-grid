package com.vaadin.peter.addon.beangrid;

/**
 * I18NSource defines mechanism for BeanGrid to tap into external I18N
 * management. The interface should be implemented as Spring Bean which is able
 * to resolve the I18N translation keys provided by the Grid. Often the
 * implementation should just delegate to the application general I18N system.
 * 
 * @author Peter / Vaadin
 */
public interface I18NProvider {

	String provideTranslation(String translationKey);
}
