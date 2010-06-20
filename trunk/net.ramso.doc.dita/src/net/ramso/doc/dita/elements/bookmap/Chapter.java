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
public class Chapter extends BasicMapRef {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Chapter() {
		super(BookMapTypes.CHAPTER);
	}

	public Chapter(TopicRef topicref) {
		super(BookMapTypes.CHAPTER);
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
