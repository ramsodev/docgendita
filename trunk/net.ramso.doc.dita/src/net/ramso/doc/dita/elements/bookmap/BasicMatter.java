/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import java.text.DateFormat;
import java.util.Date;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.IDitaTypes;
import net.ramso.doc.dita.elements.MapTypes;
import net.ramso.doc.dita.elements.PrologTypes;
import net.ramso.doc.dita.elements.TopicTypes;
import net.ramso.doc.dita.elements.map.TopicRef;

import org.jdom.Element;

/**
 * @author ramso
 */
public class BasicMatter extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public BasicMatter(IDitaTypes type) {
		super(type);
	}

	public void addBookLists(BookLists bookLists) {
		addContent(bookLists);
	}

	public void addTopicRef(TopicRef topicRef) {
		addContent(topicRef);
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
}
