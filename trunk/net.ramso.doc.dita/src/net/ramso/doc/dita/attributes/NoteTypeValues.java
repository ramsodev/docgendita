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
public enum NoteTypeValues {
	ATTENTION(0, "ATTENTION", "attention"), CAUTION(1, "CAUTION", "caution"), NOTICE(2, "NOTICE",
			"notice");
	public static final int					ATTENTION_VALUE			= 0;
	public static final int					CAUTION_VALUE				= 1;
	public static final int					NOTICE_VALUE				= 2;

	private static final NoteTypeValues[]		VALUES_ARRAY			= new NoteTypeValues[] {
			ATTENTION, CAUTION, NOTICE	};
	public static final List<NoteTypeValues>	VALUES					= Collections
																			.unmodifiableList(Arrays
																					.asList(VALUES_ARRAY));

	public static NoteTypeValues get(int value) {
		switch (value) {
			case ATTENTION_VALUE:
				return ATTENTION;
			case CAUTION_VALUE:
				return CAUTION;
			case NOTICE_VALUE:
				return NOTICE;
		}
		return null;
	}

	public static NoteTypeValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			NoteTypeValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static NoteTypeValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			NoteTypeValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private NoteTypeValues(int value, String name, String literal) {
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
