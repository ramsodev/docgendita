/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import net.ramso.doc.dita.attributes.FrameValues;
import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.TableTypes;

import org.jdom.Element;

/**
 * @author ramso
 */
public class SimpleTable extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public SimpleTable() {
		super(TableTypes.SIMPLETABLE);
		setFrame(FrameValues.ALL);
		setExpanse("page");
	}

	public void addRow(String[] values) {
		Element row = DitaFactory.createElement(TableTypes.STROW);
		for (String value : values) {
			row
					.addContent(DitaFactory.createElement(TableTypes.STENTRY,
							value));
		}
		addContent(row);
	}

	/**
	 * @param string
	 */
	public void setExpanse(String value) {
		setAttribute("expanse", value);
	}

	/**
	 * @param string
	 */
	public void setFrame(FrameValues value) {
		setAttribute("frame", value.getLiteral());
	}

	public void setHeaders(String[] heads) {
		Element head = DitaFactory.createElement(TableTypes.STHEAD);
		for (String headValue : heads) {
			head.addContent(DitaFactory.createElement(TableTypes.STENTRY,
					headValue));
		}
		addContent(head);
	}

	public void setRelColWidth(int[] sizes) {
		String value = "";
		for (int i : sizes) {
			value += +i + "* ";
		}
		setAttribute("relcolwidth", value);
	}
}
