/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import java.util.List;

import net.ramso.doc.dita.attributes.VerticalAlignValues;
import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.TableTypes;

/**
 * @author ramso
 */
public class Row extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Row() {
		super(TableTypes.ROW);
	}

	public void appendEntry(Entry entry) {
		addContent(entry);
	}

	@SuppressWarnings("unchecked")
	public List<Entry> getEntrys() {
		return getChildren();
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

	public void setVAlign(VerticalAlignValues value) {
		setAttribute("valign", value.getLiteral());
	}
}
