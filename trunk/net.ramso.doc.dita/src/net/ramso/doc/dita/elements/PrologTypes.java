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
public enum PrologTypes implements IDitaTypes {
	PROLOG(0, "PROLOG", "prolog"), AUTHOR(1, "AUTHOR", "author"), CRITDATES(2,
			"CRITDATES", "critdates"), CREATED(3, "CREATED", "created"), REVISED(
			4, "REVISED", "revised");
	public static final int					PROLOG_VALUE	= 0;
	public static final int					AUTHOR_VALUE	= 1;
	public static final int					CRITDATES_VALUE	= 2;
	public static final int					CREATED_VALUE	= 3;
	public static final int					REVISED_VALUE	= 4;
	private static final PrologTypes[]		VALUES_ARRAY	= new PrologTypes[] {
			PROLOG, AUTHOR, CRITDATES, CREATED, REVISED	};
	public static final List<PrologTypes>	VALUES			= Collections
																	.unmodifiableList(Arrays
																			.asList(VALUES_ARRAY));

	public static PrologTypes get(int value) {
		switch (value) {
			case PROLOG_VALUE:
				return PROLOG;
			case AUTHOR_VALUE:
				return AUTHOR;
			case CRITDATES_VALUE:
				return CRITDATES;
			case CREATED_VALUE:
				return CREATED;
			case REVISED_VALUE:
				return REVISED;
		}
		return null;
	}

	public static PrologTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PrologTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static PrologTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PrologTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private PrologTypes(int value, String name, String literal) {
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
