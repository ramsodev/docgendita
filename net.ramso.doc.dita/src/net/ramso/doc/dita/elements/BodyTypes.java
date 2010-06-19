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
public enum BodyTypes implements IDitaTypes {
	P(0, "P", "p"), DL(1, "DL", "dl"), DLHEAD(2, "DLHEAD", "dlhead"), DLENTRY(
			3, "DLENTRY", "dlentry"), DD(4, "DD", "dd"), DT(5, "DT", "dt"), DDHD(
			6, "DDHD", "ddhd"), DTHD(7, "DTHD", "dthd"), XREF(9, "XREF", "xref"), DESC(
			9, "DESC", "desc"), UL(10, "UL", "ul"), OL(11, "OL", "ol"), LI(12,
			"LI", "li"), SL(13, "SL", "sl"), SLI(14, "SLI", "sli");
	public static final int				P_VALUE			= 0;
	public static final int				DL_VALUE		= 1;
	public static final int				DLHEAD_VALUE	= 2;
	public static final int				DLENTRY_VALUE	= 3;
	public static final int				DD_VALUE		= 4;
	public static final int				DT_VALUE		= 5;
	public static final int				DDHD_VALUE		= 6;
	public static final int				DTHD_VALUE		= 7;
	public static final int				XREF_VALUE		= 8;
	public static final int				DESC_VALUE		= 9;
	public static final int				UL_VALUE		= 10;
	public static final int				OL_VALUE		= 11;
	public static final int				LI_VALUE		= 12;
	public static final int				SL_VALUE		= 13;
	public static final int				SLI_VALUE		= 14;
	private static final BodyTypes[]	VALUES_ARRAY	= new BodyTypes[] { P,
			DL, DLHEAD, DLENTRY, DD, DT, DDHD, DTHD, XREF, UL, OL, LI, SL, SLI };
	public static final List<BodyTypes>	VALUES			= Collections
																.unmodifiableList(Arrays
																		.asList(VALUES_ARRAY));

	public static BodyTypes get(int value) {
		switch (value) {
			case P_VALUE:
				return P;
			case DL_VALUE:
				return DL;
			case DLHEAD_VALUE:
				return DLHEAD;
			case DLENTRY_VALUE:
				return DLENTRY;
			case DD_VALUE:
				return DD;
			case DT_VALUE:
				return DT;
			case DDHD_VALUE:
				return DDHD;
			case DTHD_VALUE:
				return DTHD;
			case XREF_VALUE:
				return XREF;
			case DESC_VALUE:
				return DESC;
			case UL_VALUE:
				return UL;
			case OL_VALUE:
				return OL;
			case LI_VALUE:
				return LI;
			case SL_VALUE:
				return SL;
			case SLI_VALUE:
				return SLI;
		}
		return null;
	}

	public static BodyTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BodyTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static BodyTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BodyTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private BodyTypes(int value, String name, String literal) {
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
