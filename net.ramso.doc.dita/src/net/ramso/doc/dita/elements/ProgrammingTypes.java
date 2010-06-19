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
public enum ProgrammingTypes implements IDitaTypes {
	CODEBLOCK(0, "CODEBLOCK", "codeblock");
	public static final int						CODEBLOCK_VALUE	= 10;
	private static final ProgrammingTypes[]		VALUES_ARRAY	= new ProgrammingTypes[] { CODEBLOCK };
	public static final List<ProgrammingTypes>	VALUES			= Collections
																		.unmodifiableList(Arrays
																				.asList(VALUES_ARRAY));

	public static ProgrammingTypes get(int value) {
		switch (value) {
			case CODEBLOCK_VALUE:
				return CODEBLOCK;
		}
		return null;
	}

	public static ProgrammingTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProgrammingTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static ProgrammingTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProgrammingTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private ProgrammingTypes(int value, String name, String literal) {
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
