/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.IDitaTypes;

/**
 * @author ramso
 */
public class FrontMatter extends BasicMatter {
	/**
	 * @param type
	 */
	public FrontMatter() {
		super(BookMapTypes.FRONTMATTER);
	}

	public void setPreface(Preface preface) {
		addContent(preface);
	}
}
