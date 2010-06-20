/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import net.ramso.doc.dita.attributes.AlignValues;
import net.ramso.doc.dita.attributes.VerticalAlignValues;
import net.ramso.doc.dita.elements.TableTypes;

/**
 * @author ramso
 */
public class Entry extends BasicTableAttributes {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Entry() {
		super(TableTypes.ENTRY);
	}

	public void setAlign(AlignValues value) {
		setAttribute("align", value.getLiteral());
	}

	public void setChar(char value) {
		setAttribute("char", String.valueOf(value));
	}

	/**
	 * @param charoff
	 *            between 0 100
	 */
	public void setCharOff(int charoff) {
		setAttribute("charoff", String.valueOf(charoff));
	}

	public void setColName(String name) {
		setAttribute("colname", name);
	}

	public void setMoreRows(int rows) {
		setAttribute("morerows", String.valueOf(rows));
	}

	public void setNameEnd(String name) {
		setAttribute("nameend", name);
	}

	public void setNamest(String name) {
		setAttribute("namest", name);
	}

	public void setVAlign(VerticalAlignValues value) {
		setAttribute("valign", value.getLiteral());
	}
}
