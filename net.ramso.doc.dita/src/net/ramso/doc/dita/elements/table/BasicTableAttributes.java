/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import net.ramso.doc.dita.attributes.ScaleValues;
import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.IDitaTypes;
import net.ramso.doc.dita.elements.TableTypes;
import net.ramso.doc.dita.elements.TopicTypes;

import org.jdom.Element;

/**
 * @author ramso
 */
public abstract class BasicTableAttributes extends BaseDitaElement {
	/**
	 * @param type
	 */
	public BasicTableAttributes(IDitaTypes type) {
		super(type);
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
}
