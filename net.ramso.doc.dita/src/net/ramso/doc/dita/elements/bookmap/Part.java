/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.map.BasicMapRef;

/**
 * @author ramso
 */
public class Part extends BasicMapRef {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Part() {
		super(BookMapTypes.PART);
	}

	public void addChapter(Chapter chapter) {
		addContent(chapter);
	}

	public Chapter getChapter(String id) {
		return (Chapter) getContent(BookMapTypes.CHAPTER, id);
	}
}
