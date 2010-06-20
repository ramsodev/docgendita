/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.IDitaTypes;

/**
 * @author ramso
 */
public abstract class BasicTableAttributes extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public BasicTableAttributes(IDitaTypes type) {
		super(type);
	}

	/**
	 * @param string
	 */
	public void setColSep(Boolean value) {
		if (value) {
			setAttribute("colsep", "1");
		}
		else {
			setAttribute("colsep", "0");
		}
	}

	/**
	 * @param string
	 */
	public void setRowSep(Boolean value) {
		if (value) {
			setAttribute("rowsep", "1");
		}
		else {
			setAttribute("rowsep", "0");
		}
	}
}
