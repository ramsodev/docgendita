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
public enum TopicTypes implements IDitaTypes {
	TOPIC(0, "TOPIC", "topic"), TITLE(1, "TITLE", "title"), BODY(2, "BODY",
			"body"), SECTION(3, "SECTION", "section"), SHORTDES(4, "SHORTDES",
			"shortdesc");
	public static final int					TOPIC_VALUE		= 0;
	public static final int					TITLE_VALUE		= 1;
	public static final int					BODY_VALUE		= 2;
	public static final int					SECTION_VALUE	= 3;
	public static final int					SHORTDES_VALUE	= 4;
	private static final TopicTypes[]		VALUES_ARRAY	= new TopicTypes[] {
			TOPIC, BODY, TITLE, SECTION, SHORTDES			};
	public static final List<TopicTypes>	VALUES			= Collections
																	.unmodifiableList(Arrays
																			.asList(VALUES_ARRAY));

	public static TopicTypes get(int value) {
		switch (value) {
			case TOPIC_VALUE:
				return TOPIC;
			case BODY_VALUE:
				return BODY;
			case TITLE_VALUE:
				return TITLE;
			case SECTION_VALUE:
				return SECTION;
			case SHORTDES_VALUE:
				return SHORTDES;
		}
		return null;
	}

	public static TopicTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TopicTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static TopicTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TopicTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private TopicTypes(int value, String name, String literal) {
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
