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
public enum AlignValues {
	CENTER(0, "CENTER", "center"), LEFT(1, "LEFT", "left"), RIGHT(2, "RIGHT",
			"right"), JUSTIFY(3, "JUSTIFY", "justify"), CHAR(4, "CHAR", "char"), DITA_USE_CONREF(
			5, "DITA-USE-CONREF", "dita-use-conref");
	public static final int					CENTER_VALUE			= 0;
	public static final int					LEFT_VALUE				= 1;
	public static final int					RIGHT_VALUE				= 2;
	public static final int					JUSTIFY_VALUE			= 3;
	public static final int					CHAR_VALUE				= 4;
	public static final int					DITA_USE_CONREF_VALUE	= 5;
	private static final AlignValues[]		VALUES_ARRAY			= new AlignValues[] {
			CENTER, LEFT, RIGHT, JUSTIFY, CHAR, DITA_USE_CONREF	};
	public static final List<AlignValues>	VALUES					= Collections
																			.unmodifiableList(Arrays
																					.asList(VALUES_ARRAY));

	public static AlignValues get(int value) {
		switch (value) {
			case CENTER_VALUE:
				return CENTER;
			case LEFT_VALUE:
				return LEFT;
			case RIGHT_VALUE:
				return RIGHT;
			case JUSTIFY_VALUE:
				return JUSTIFY;
			case CHAR_VALUE:
				return CHAR;
			case DITA_USE_CONREF_VALUE:
				return DITA_USE_CONREF;
		}
		return null;
	}

	public static AlignValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AlignValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static AlignValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AlignValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private AlignValues(int value, String name, String literal) {
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
