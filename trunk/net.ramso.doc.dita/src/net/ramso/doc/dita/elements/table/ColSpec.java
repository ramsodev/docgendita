/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import net.ramso.doc.dita.attributes.AlignValues;
import net.ramso.doc.dita.elements.TableTypes;

/**
 * @author ramso
 */
public class ColSpec extends BasicTableAttributes {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public ColSpec() {
		super(TableTypes.COLSPEC);
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

	public void setColNum(int col) {
		setAttribute("colnum", String.valueOf(col));
	}

	public void setColWidth(String colwidth) {
		setAttribute("colwidth", colwidth);
	}
}
