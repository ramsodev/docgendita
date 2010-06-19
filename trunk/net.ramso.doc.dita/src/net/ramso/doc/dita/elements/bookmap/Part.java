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
 *
 */
public class Part extends BasicMapRef {
	/**
	 * @param type
	 */
	public Part() {
		super(BookMapTypes.PART);
	}
	
	public void addChapter(Chapter chapter){
		addContent(chapter);
	}
	
	public Chapter getChapter(String id){
		return (Chapter) getContent(BookMapTypes.CHAPTER, id);
	}
	
}
