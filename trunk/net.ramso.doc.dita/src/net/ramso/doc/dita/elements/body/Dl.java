/**
 * 
 */
package net.ramso.doc.dita.elements.body;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;

import org.jdom.Element;

/**
 * @author ramso
 */
public class Dl extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4816817046336784770L;

	/**
	 * 
	 */
	public Dl() {
		super(BodyTypes.DL);
	}

	public void setHeaders(String dt, String dd) {
		Element dlHead = DitaFactory.createElement(BodyTypes.DLHEAD);
		dlHead.addContent(DitaFactory.createElement(BodyTypes.DTHD, dt));
		dlHead.addContent(DitaFactory.createElement(BodyTypes.DDHD, dd));
		addContent(dlHead);
	}

	public void addItem(String dt, String dd) {
		Element dlentry = DitaFactory.createElement(BodyTypes.DLENTRY);
		dlentry.addContent(DitaFactory.createElement(BodyTypes.DT, dt));
		dlentry.addContent(DitaFactory.createElement(BodyTypes.DD, dd));
		addContent(dlentry);
	}

	/**
	 * @param dt
	 * @param table
	 */
	public void addItem(String dt, Element dd) {
		Element dlentry = DitaFactory.createElement(BodyTypes.DLENTRY);
		dlentry.addContent(DitaFactory.createElement(BodyTypes.DT, dt));
		if (dd.getName().equals(BodyTypes.DD.getLiteral())) {
			dlentry.addContent(dd);
		}
		else {
			Element dde = DitaFactory.createElement(BodyTypes.DD);
			dde.addContent(dd);
			dlentry.addContent(dde);
		}
		addContent(dlentry);
	}

	/**
	 * @param dt
	 * @param table
	 */
	public void addItem(Element dt, String dd) {
		Element dlentry = DitaFactory.createElement(BodyTypes.DLENTRY);
		if (dt.getName().equals(BodyTypes.DD.getLiteral())) {
			dlentry.addContent(dt);
		}
		else {
			Element dte = DitaFactory.createElement(BodyTypes.DT);
			dte.addContent(dt);
			dlentry.addContent(dte);
		}
		dlentry.addContent(DitaFactory.createElement(BodyTypes.DD, dd));
		addContent(dlentry);
	}

	public static Element getEntry(String dt, String dd) {
		Element dlentry = DitaFactory.createElement(BodyTypes.DLENTRY);
		dlentry.addContent(DitaFactory.createElement(BodyTypes.DT, dt));
		dlentry.addContent(DitaFactory.createElement(BodyTypes.DD, dd));
		return dlentry;
	}

	/**
	 * @param dt
	 * @param sl
	 * @return
	 */
	public static Element getEntry(String dt, Element dd) {
		Element dlentry = DitaFactory.createElement(BodyTypes.DLENTRY);
		dlentry.addContent(DitaFactory.createElement(BodyTypes.DT, dt));
		if (dd.getName().equals(BodyTypes.DD.getLiteral())) {
			dlentry.addContent(dd);
		}
		else {
			Element dde = DitaFactory.createElement(BodyTypes.DD);
			dde.addContent(dd);
			dlentry.addContent(dde);
		}
		return dlentry;
	}
}
