/**
 * 
 */
package net.ramso.doc.dita.elements;

import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;

/**
 * @author ramso
 */
public class BaseDitaElement extends Element {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2975984834944997256L;

	public BaseDitaElement(IDitaTypes type) {
		super(type.getLiteral());
	}

	@SuppressWarnings("unchecked")
	public List<BaseDitaElement> getChilds(IDitaTypes type) {
		return getChildren(type.getLiteral());
	}

	public Element getContent(IDitaTypes type, String id) {
		List<BaseDitaElement> childs = getChilds(type);
		for (BaseDitaElement child : childs) {
			if (child.isID(id)) {
				return child;
			}
		}
		return null;
	}

	public String getID() {
		return getAttribute("id").getValue();
	}

	/**
	 * @param type
	 * @return
	 */
	public int getLastElementIdx(TableTypes type) {
		List<BaseDitaElement> childs = getChilds(type);
		if (childs.isEmpty()) {
			return -1;
		}
		else {
			return indexOf(childs.get(childs.size() - 1));
		}
	}

	public boolean isID(String id) {
		return getID().equals(id);
	}

	public void setID(String id) {
		setAttribute("id", id);
	}

	public void setLang(String lang) {
		setAttribute("lang", lang, Namespace.XML_NAMESPACE);
	}
}
