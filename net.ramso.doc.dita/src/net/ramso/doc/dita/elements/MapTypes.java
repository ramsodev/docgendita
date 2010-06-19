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
public enum MapTypes implements IDitaTypes{
	MAP(0, "MAP", "map"), TOPICREF(1, "TOPIC_REF", "topicref"), TOPICMETA(2,
			"TOPICMETA", "topicmeta");
	public static final int				MAP_VALUE		= 0;
	public static final int				TOPICREF_VALUE	= 1;
	public static final int				TOPICMETA_VALUE	= 2;
	private static final MapTypes[]		VALUES_ARRAY	= new MapTypes[] { MAP,
			TOPICREF, TOPICMETA};
	public static final List<MapTypes>	VALUES			= Collections
																.unmodifiableList(Arrays
																		.asList(VALUES_ARRAY));

	public static MapTypes get(int value) {
		switch (value) {
			case MAP_VALUE:
				return MAP;
			case TOPICREF_VALUE:
				return TOPICREF;
			case TOPICMETA_VALUE:
				return TOPICMETA;
		}
		return null;
	}

	public static MapTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MapTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static MapTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MapTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private MapTypes(int value, String name, String literal) {
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
