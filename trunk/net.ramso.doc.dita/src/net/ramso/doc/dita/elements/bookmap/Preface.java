/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import java.util.List;

import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.map.BasicMapRef;
import net.ramso.doc.dita.elements.map.TopicRef;

import org.jdom.Attribute;

/**
 * @author ramso
 */
public class Preface extends BasicMapRef {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Preface() {
		super(BookMapTypes.PREFACE);
	}

	public Preface(TopicRef topicref) {
		super(BookMapTypes.PREFACE);
		importFromTopic(topicref);
	}

	/**
	 * @param topicref
	 */
	private void importFromTopic(TopicRef topicref) {
		List<Attribute> attrs = topicref.getAttributes();
		for (Attribute attribute : attrs) {
			setAttribute(attribute.getName(), attribute.getValue());
		}
		setContent(topicref.cloneContent());
	}
}
