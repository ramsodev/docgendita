/**
 * 
 */
package net.ramso.doc.dita.elements.topic;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.TopicTypes;
import net.ramso.doc.dita.elements.body.Body;

import org.jdom.Element;

/**
 * @author ramso
 */
public class Topic extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3117823641078530780L;

	/**
	 * 
	 */
	public Topic() {
		super(TopicTypes.TOPIC);
	}

	public void addP(String text) {
		Body body = getBody();
		body.appendP(text);
	}

	public Body getBody() {
		Element body = getChild(TopicTypes.BODY.getLiteral());
		Body result;
		if (body == null) {
			result = DitaFactory.createBody();
			addContent(result);
		}
		else {
			result = (Body) body;
		}
		return result;
	}

	public void setTitle(String title) {
		addContent(DitaFactory.createElement(TopicTypes.TITLE, title));
	}

	/**
	 * @param string
	 * @return
	 */
	public void appendSection(String title, String id) {
		Body body = getBody();
		body.appendSection(title, id);
	}

	/**
	 * @param set
	 */
	public Section getSection(String id) {
		return getBody().getSection(id);
		
	}
	
}
