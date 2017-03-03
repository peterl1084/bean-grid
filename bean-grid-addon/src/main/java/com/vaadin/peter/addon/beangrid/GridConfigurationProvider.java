package com.vaadin.peter.addon.beangrid;

import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontIcon;

/**
 * GridConfigurationProvider is the way for Grid and its related functionality
 * to reach the configuration information from the host system. This interface
 * should be implemented as a (singleton) Spring Bean that is then accessed by
 * the Grid and its various parts when requiring specific information.
 * 
 * @author Peter / Vaadin
 */
public interface GridConfigurationProvider {

	/**
	 * @return the Locale of the system that Grid should be using.
	 */
	default Locale getLocale() {
		return Locale.getDefault();
	}

	/**
	 * @return The String format pattern that should be used whenever formatting
	 *         {@link Date} to String. May return Empty Optional which indicates
	 *         that system defaults should be used.
	 */
	default Optional<String> getDateFormatPattern() {
		return Optional.of("yyyy-MM-dd");
	}

	/**
	 * @return The String format pattern that should be used whenever formatting
	 *         {@link Number} to String. May return Empty Optional which
	 *         indicates that system defaults should be used.
	 */
	default Optional<String> getNumberFormatPattern() {
		return Optional.of("0.00");
	}

	/**
	 * @return the string that should be shown if value conversion within grid's
	 *         editor fails.
	 */
	default String getConversionErrorString() {
		return "Conversion failed";
	}

	/**
	 * @return ZoneId of the system that should be used whenever doing
	 *         (local)date/time related conversions.
	 */
	default ZoneId getTimeZoneId() {
		return ZoneId.systemDefault();
	}

	/**
	 * @param translationKey
	 * @return The localized (translated) value of given translationKey.
	 */
	String resolveTranslationKey(String translationKey);

	/**
	 * @return FontIcon that should be used whenever a boolean true is expected
	 *         to be visualized as FontIcon.
	 */
	default FontIcon getBooleanTrueFontIcon() {
		return VaadinIcons.CHECK;
	}

	/**
	 * @return FontIcon that should be used whenever a boolean false is expected
	 *         to be visualized as FontIcon.
	 */
	default FontIcon getBooleanFalseFontIcon() {
		return VaadinIcons.CLOSE;
	}
}
