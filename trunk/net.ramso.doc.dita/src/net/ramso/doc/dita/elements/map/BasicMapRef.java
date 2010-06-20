/**
 * 
 */
package net.ramso.doc.dita.elements.map;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.IDitaTypes;
import net.ramso.doc.dita.elements.MapTypes;
import net.ramso.doc.dita.elements.PrologTypes;
import net.ramso.doc.dita.elements.TopicTypes;

import org.jdom.Element;

/**
 * @author ramso
 */
public abstract class BasicMapRef extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3469185595919872388L;

	/**
	 * @param type
	 */
	public BasicMapRef(IDitaTypes type) {
		super(type);
	}

	/**
	 * @param des
	 * @param autor
	 */
	public void appedTopicMeta(String des, String autor) {
		Element meta = DitaFactory.createElement(MapTypes.TOPICMETA);
		meta.addContent(DitaFactory.createElement(TopicTypes.SHORTDES, des));
		meta.addContent(DitaFactory.createElement(PrologTypes.AUTHOR, autor));
		Element critDates = DitaFactory.createElement(PrologTypes.CRITDATES);
		Element created = DitaFactory.createElement(PrologTypes.CREATED);
		String date = DateFormat.getDateInstance(DateFormat.SHORT).format(
				new Date(System.currentTimeMillis()));
		created.setAttribute("date", date);
		critDates.addContent(created);
		meta.addContent(critDates);
		addContent(meta);
	}

	/**
	 * @param topicRefs
	 */
	public void appendTopicRef(List<BaseDitaElement> topicRefs) {
		addContent(topicRefs);
	}

	public void appendTopicRef(TopicRef topicRef) {
		addContent(topicRef);
	}

	public List<BaseDitaElement> getTopicRef() {
		return getChilds(MapTypes.TOPICREF);
	}

	public void setCopyTo(String value) {
		setAttribute("copy-to", value);
	}

	public void setHref(String value) {
		setAttribute("href", value);
	}

	public void setNavTitle(String value) {
		setAttribute("navtitle", value);
	}
}
