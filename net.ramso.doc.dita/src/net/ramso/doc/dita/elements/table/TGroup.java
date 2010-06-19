/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import org.jdom.Element;

import net.ramso.doc.dita.attributes.AlignValues;
import net.ramso.doc.dita.elements.TableTypes;

/**
 * @author ramso
 */
public class TGroup extends BasicTableAttributes {
	/**
	 * @param type
	 */
	public TGroup() {
		super(TableTypes.TGROUP);
	}

	public void setAlign(AlignValues value) {
		setAttribute("align", value.getLiteral());
	}

	public void setCols(int cols) {
		setAttribute("cols", String.valueOf(cols));
	}

	public void appendColSpec(ColSpec colSpec) {
		int pos = getLastElementIdx(TableTypes.COLSPEC);
		pos++;
		int s = getChildren().size();
		if (pos >= getChildren().size()) {
			addContent(colSpec);
		}
		else {
			addContent(pos, colSpec);
		}
	}

	public void setTHead(THead thead) {
		if (getTHead() == null) {
			int pos = getLastElementIdx(TableTypes.TBODY);
			if (pos < 0) {
				addContent(thead);
			}
			else {
				pos--;
				addContent(pos, thead);
			}
		}
	}

	public THead getTHead() {
		return (THead) getChild(TableTypes.THEAD.getLiteral());
	}

	public void setTBody(TBody tbody) {
		if (getTBody() == null) {
			addContent(tbody);
		}
	}

	public TBody getTBody() {
		return (TBody) getChild(TableTypes.TBODY.getLiteral());
	}
}
