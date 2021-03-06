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
public class THead extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public THead() {
		super(TableTypes.THEAD);
	}

	public void appendRow(Row row) {
		addContent(row);
	}

	@SuppressWarnings("unchecked")
	public List<Row> getRows() {
		return getChildren();
	}

	public void setVAlign(VerticalAlignValues value) {
		setAttribute("valign", value.getLiteral());
	}
}
