package com.vaadin.peter.addon.beangrid;

/**
 * ColumnAlignment defines various alignment options how the textual content of
 * a Grid cell should be aligned within the cell for particular column type.
 * 
 * @author Peter / Vaadin
 */
public enum ColumnAlignment {
	/**
	 * Column content will be left aligned.
	 */
	LEFT("left-align"),

	/**
	 * Column content will be centered.
	 */
	CENTER("center-align"),

	/**
	 * Column content will be right aligned.
	 */
	RIGHT("right-align");

	private final String styleName;

	private ColumnAlignment(String styleName) {
		this.styleName = styleName;
	}

	/**
	 * @return the CSS stylename associated with this {@link ColumnAlignment}
	 *         type.
	 */
	public String getStyleName() {
		return styleName;
	}
}
