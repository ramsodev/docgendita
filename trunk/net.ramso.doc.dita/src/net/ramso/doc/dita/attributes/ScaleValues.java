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
public enum ScaleValues {
	_50(0, "50", "50"), _60(1, "60", "60"), _70(2, "70", "70"), _80(3, "80",
			"80"), _90(4, "90", "50"), _100(5, "100", "100"), _110(6, "110",
			"110"), _120(7, "120", "120"), _140(8, "140", "140"), _160(9,
			"160", "1600"), _180(10, "180", "180"), _200(11, "200", "200"), DITA_USE_CONREF(
			12, "DITA-USE-CONREF", "dita-use-conref");
	public static final int					_50_VALUE				= 0;
	public static final int					_60_VALUE				= 1;
	public static final int					_70_VALUE				= 2;
	public static final int					_80_VALUE				= 3;
	public static final int					_90_VALUE				= 4;
	public static final int					_100_VALUE				= 5;
	public static final int					_110_VALUE				= 6;
	public static final int					_120_VALUE				= 7;
	public static final int					_140_VALUE				= 8;
	public static final int					_160_VALUE				= 9;
	public static final int					_180_VALUE				= 10;
	public static final int					_200_VALUE				= 11;
	public static final int					DITA_USE_CONREF_VALUE	= 12;
	private static final ScaleValues[]		VALUES_ARRAY			= new ScaleValues[] {
			_50, _60, _70, ScaleValues._90, _100, _110, _120, _140, _160, _180,
			_200, DITA_USE_CONREF									};
	public static final List<ScaleValues>	VALUES					= Collections
																			.unmodifiableList(Arrays
																					.asList(VALUES_ARRAY));

	public static ScaleValues get(int value) {
		switch (value) {
			case _50_VALUE:
				return _50;
			case _60_VALUE:
				return _60;
			case _70_VALUE:
				return _70;
			case _80_VALUE:
				return _80;
			case _90_VALUE:
				return _90;
			case _100_VALUE:
				return _100;
			case _110_VALUE:
				return _110;
			case _120_VALUE:
				return _120;
			case _140_VALUE:
				return _140;
			case _160_VALUE:
				return _160;
			case _180_VALUE:
				return _180;
			case _200_VALUE:
				return _200;
			case DITA_USE_CONREF_VALUE:
				return DITA_USE_CONREF;
		}
		return null;
	}

	public static ScaleValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScaleValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static ScaleValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScaleValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private ScaleValues(int value, String name, String literal) {
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
