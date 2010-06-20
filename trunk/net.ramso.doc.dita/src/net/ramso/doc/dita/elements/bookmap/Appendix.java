/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.map.BasicMapRef;

/**
 * @author ramso
 */
public class Appendix extends BasicMapRef {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Appendix() {
		super(BookMapTypes.APPENDIX);
	}
}
