/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import java.util.List;

import org.jdom.Attribute;

import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.map.BasicMapRef;
import net.ramso.doc.dita.elements.map.TopicRef;

/**
 * @author ramso
 */
public class Chapter extends BasicMapRef {
	/**
	 * @param type
	 */
	public Chapter() {
		super(BookMapTypes.CHAPTER);
	}
	
	public Chapter(TopicRef topicref){
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
