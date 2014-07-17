package net.ramso.doc.dita.elements.body;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.TopicTypes;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.utils.TextUtils;

public class Body extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public Body() {
		super(TopicTypes.BODY);
	}

	public void appendP(String text) {
		addContent(DitaFactory.createElement(BodyTypes.P, text));
	}

	/**
	 * @param title
	 */
	public void appendSection(String title, String id) {
		id=TextUtils.clean(id);
		Section section = DitaFactory.createSection();
		section.setID(id);
		section.setTitle(title);
		addContent(section);
	}

	public Section getSection(String id) {
		id = TextUtils.clean(id);
		return (Section) getContent(TopicTypes.SECTION, id);
	}
}
