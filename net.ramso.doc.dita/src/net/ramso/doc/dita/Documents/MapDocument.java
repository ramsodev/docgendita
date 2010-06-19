/**
 * 
 */
package net.ramso.doc.dita.Documents;

import net.ramso.doc.dita.elements.MapTypes;
import net.ramso.doc.dita.elements.map.Map;

/**
 * @author ramso
 */
public class MapDocument extends BaseDitaDocument {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6974655256197945020L;
	public static final String	PUBLIC_ID			= "-//OASIS//DTD DITA Map//EN";
	public static final String	SYSTEM_ID			= "http://docs.oasis-open.org/dita/v1.1/OS/dtd/map.dtd";

	/**
	 * 
	 */
	public MapDocument() {
		super(MapTypes.MAP, PUBLIC_ID, SYSTEM_ID);
		setRootElement(new Map());
		EXT = ".ditamap";
	}

	public Map getMap() {
		return (Map) getElementRoot();
	}
}
