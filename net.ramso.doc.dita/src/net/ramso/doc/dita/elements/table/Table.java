/**
 * 
 */
package net.ramso.doc.dita.elements.table;

import net.ramso.doc.dita.attributes.FrameValues;
import net.ramso.doc.dita.attributes.ScaleValues;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.TableTypes;
import net.ramso.doc.dita.elements.TopicTypes;

/**
 * @author ramso
 */
public class Table extends BasicTableAttributes {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Table() {
		super(TableTypes.TABLE);
		setFrame(FrameValues.ALL);
	}

	/**
	 * @return
	 */
	public TGroup getTGroup() {
		return (TGroup) getChild(TableTypes.TGROUP.getLiteral());
	}

	public void setDesc(String desc) {
		addContent(DitaFactory.createElement(BodyTypes.DESC, desc));
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

	/**
	 * @param string
	 */
	public void setPGWide(Boolean value) {
		if (value) {
			setAttribute("pgwide", "1");
		}
		else {
			setAttribute("pgwide", "0");
		}
	}

	/**
	 * @param string
	 */
	public void setScale(ScaleValues value) {
		setAttribute("scale", value.getLiteral());
	}

	public void setTGroup(TGroup tgroup) {
		if (getTGroup() == null) {
			addContent(tgroup);
		}
	}

	public void setTitle(String title) {
		addContent(DitaFactory.createElement(TopicTypes.TITLE, title));
	}
}
