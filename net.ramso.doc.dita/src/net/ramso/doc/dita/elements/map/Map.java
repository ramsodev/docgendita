package net.ramso.doc.dita.elements.map;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.MapTypes;
import net.ramso.doc.dita.elements.PrologTypes;
import net.ramso.doc.dita.elements.TopicTypes;

import org.jdom.Element;

/**
 * @author ramso
 */
public class Map extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public Map() {
		super(MapTypes.MAP);
		setLang(Locale.getDefault().getLanguage() + "_"
				+ Locale.getDefault().getCountry());
		setTranslate(true);
		setCollectionType("sequence");
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
	 * @param topicRef
	 */
	public void appendTopicRef(TopicRef topicRef) {
		addContent(topicRef);
	}

	/**
	 * @param string
	 */
	public void setCollectionType(String value) {
		setAttribute("collection-type", value);
	}

	public void setTitle(String title) {
		setAttribute("title", title);
	}

	/**
	 * @param b
	 */
	public void setTranslate(boolean value) {
		if (value) {
			setAttribute("translate", "yes");
		}
		else {
			setAttribute("translate", "no");
		}
	}
}