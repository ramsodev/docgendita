/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import net.ramso.doc.dita.elements.BookMapTypes;

/**
 * @author ramso
 */
public class FrontMatter extends BasicMatter {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

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
