/**
 * 
 */
package net.ramso.doc.dita.attributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ramso
 */
public enum VerticalAlignValues {
	BOTTOM(0, "BOTTOM", "bottom"), MIDDLE(1, "MIDDLE", "middle"), TOP(2, "TOP",
			"top"), DITA_USE_CONREF(3, "DITA-USE-CONREF", "dita-use-conref");
	public static final int							BOTTOM_VALUE			= 0;
	public static final int							MIDDLE_VALUE			= 1;
	public static final int							TOP_VALUE				= 2;
	public static final int							DITA_USE_CONREF_VALUE	= 3;
	private static final VerticalAlignValues[]		VALUES_ARRAY			= new VerticalAlignValues[] {
			BOTTOM, MIDDLE, TOP, DITA_USE_CONREF							};
	public static final List<VerticalAlignValues>	VALUES					= Collections
																					.unmodifiableList(Arrays
																							.asList(VALUES_ARRAY));

	public static VerticalAlignValues get(int value) {
		switch (value) {
			case BOTTOM_VALUE:
				return BOTTOM;
			case MIDDLE_VALUE:
				return MIDDLE;
			case TOP_VALUE:
				return TOP;
			case DITA_USE_CONREF_VALUE:
				return DITA_USE_CONREF;
		}
		return null;
	}

	public static VerticalAlignValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VerticalAlignValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static VerticalAlignValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VerticalAlignValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private VerticalAlignValues(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	public String getLiteral() {
		return literal;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return literal;
	}
}
