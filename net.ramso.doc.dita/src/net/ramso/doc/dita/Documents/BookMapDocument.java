/**
 * 
 */
package net.ramso.doc.dita.Documents;

import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.bookmap.BookMap;
import net.ramso.doc.dita.elements.map.Map;

/**
 * @author ramso
 */
public class BookMapDocument extends BaseDitaDocument {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6974655256197945020L;
	public static final String	PUBLIC_ID			= "-//OASIS//DTD DITA BookMap//EN";
	public static final String	SYSTEM_ID			= "http://docs.oasis-open.org/dita/v1.1/OS/dtd/bookmap.dtd";

	/**
	 * 
	 */
	public BookMapDocument() {
		super(BookMapTypes.BOOKMAP, PUBLIC_ID, SYSTEM_ID);
		setRootElement(new BookMap());
		EXT = ".ditamap";
	}

	public BookMap getMap() {
		return (BookMap) getElementRoot();
	}
}
