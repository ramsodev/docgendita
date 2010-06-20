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
public enum TableTypes implements IDitaTypes {
	SIMPLETABLE(0, "SIMPLETABLE", "simpletable"), STHEAD(1, "STHEAD", "sthead"), STROW(
			2, "STROW", "strow"), STENTRY(3, "STENTRY", "stentry"), TABLE(4,
			"TABLE", "table"), TGROUP(5, "TGROUP", "tgroup"), COLSPEC(6,
			"COLSPEC", "colspec"), THEAD(7, "THEAD", "thead"), TBODY(8,
			"TBODY", "tbody"), ROW(9, "ROW", "row"), ENTRY(10, "ENTRY", "entry");
	public static final int					SIMPLETABLE_VALUE	= 0;
	public static final int					STHEAD_VALUE		= 1;
	public static final int					STROW_VALUE			= 2;
	public static final int					STENTRY_VALUE		= 3;
	public static final int					TABLE_VALUE			= 4;
	public static final int					TGROUP_VALUE		= 5;
	public static final int					COLSPEC_VALUE		= 6;
	public static final int					THEAD_VALUE			= 7;
	public static final int					TBODY_VALUE			= 8;
	public static final int					ROW_VALUE			= 9;
	public static final int					ENTRY_VALUE			= 10;
	private static final TableTypes[]		VALUES_ARRAY		= new TableTypes[] {
			SIMPLETABLE, STHEAD, STROW, STENTRY, TABLE, TGROUP, COLSPEC, THEAD,
			TBODY, ROW, ENTRY									};
	public static final List<TableTypes>	VALUES				= Collections
																		.unmodifiableList(Arrays
																				.asList(VALUES_ARRAY));

	public static TableTypes get(int value) {
		switch (value) {
			case SIMPLETABLE_VALUE:
				return SIMPLETABLE;
			case STHEAD_VALUE:
				return STHEAD;
			case STROW_VALUE:
				return STROW;
			case STENTRY_VALUE:
				return STENTRY;
			case TABLE_VALUE:
				return TABLE;
			case TGROUP_VALUE:
				return TGROUP;
			case COLSPEC_VALUE:
				return COLSPEC;
			case THEAD_VALUE:
				return THEAD;
			case TBODY_VALUE:
				return TBODY;
			case ROW_VALUE:
				return ROW;
			case ENTRY_VALUE:
				return ENTRY;
		}
		return null;
	}

	public static TableTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TableTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static TableTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TableTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private TableTypes(int value, String name, String literal) {
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
