/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.DitaFactory;

/**
 * @author ramso
 */
public class BookTitle extends BaseDitaElement {
	/**
	 * @param type
	 */
	public BookTitle() {
		super(BookMapTypes.BOOKTITLE);
	}

	public void setTitle(String title) {
		addContent(DitaFactory.createElement(BookMapTypes.MAINBOOKTITLE, title));
	}

	public void setTitleAlts(String title) {
		addContent(DitaFactory.createElement(BookMapTypes.BOOKTITLEALT, title));
	}
}
