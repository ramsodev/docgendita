/**
 * 
 */
package net.ramso.doc.dita.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ramso
 */
public enum TextTypes implements IDitaTypes {
	B(0, "B", "b"), TT(1, "TT", "tt"), I(2,
			"I", "i");
	public static final int					B_VALUE	= 0;
	public static final int					TT_VALUE	= 1;
	public static final int					I_VALUE	= 2;
	private static final TextTypes[]		VALUES_ARRAY	= new TextTypes[] {
			B, TT, I};
	public static final List<TextTypes>	VALUES			= Collections
																	.unmodifiableList(Arrays
																			.asList(VALUES_ARRAY));

	public static TextTypes get(int value) {
		switch (value) {
			case B_VALUE:
				return B;
			case TT_VALUE:
				return TT;
			case I_VALUE:
				return I;
		}
		return null;
	}

	public static TextTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TextTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static TextTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TextTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private TextTypes(int value, String name, String literal) {
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
