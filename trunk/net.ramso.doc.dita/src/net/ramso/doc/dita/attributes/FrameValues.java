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
public enum FrameValues {
	BOTTOM(0, "BOTTOM", "bottom"), TOPBOT(1, "TOPBOT", "topbot"), TOP(2, "TOP",
			"top"), DITA_USE_CONREF(3, "DITA-USE-CONREF", "dita-use-conref"), ALL(
			4, "ALL", "all"), SIDES(5, "SIDES", "SIDES"), NONE(6, "NONE",
			"none");
	public static final int					BOTTOM_VALUE			= 0;
	public static final int					TOPBOT_VALUE			= 1;
	public static final int					TOP_VALUE				= 2;
	public static final int					DITA_USE_CONREF_VALUE	= 3;
	public static final int					ALL_VALUE				= 4;
	public static final int					SIDES_VALUE				= 5;
	public static final int					NONE_VALUE				= 6;
	private static final FrameValues[]		VALUES_ARRAY			= new FrameValues[] {
			BOTTOM, TOPBOT, TOP, DITA_USE_CONREF, ALL, SIDES, NONE	};
	public static final List<FrameValues>	VALUES					= Collections
																			.unmodifiableList(Arrays
																					.asList(VALUES_ARRAY));

	public static FrameValues get(int value) {
		switch (value) {
			case BOTTOM_VALUE:
				return BOTTOM;
			case TOPBOT_VALUE:
				return TOPBOT;
			case TOP_VALUE:
				return TOP;
			case DITA_USE_CONREF_VALUE:
				return DITA_USE_CONREF;
			case ALL_VALUE:
				return ALL;
			case SIDES_VALUE:
				return SIDES;
			case NONE_VALUE:
				return NONE;
		}
		return null;
	}

	public static FrameValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FrameValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static FrameValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FrameValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private FrameValues(int value, String name, String literal) {
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
