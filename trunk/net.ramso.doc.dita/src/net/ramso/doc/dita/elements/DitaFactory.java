package net.ramso.doc.dita.elements;

import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.body.Body;
import net.ramso.doc.dita.elements.body.Dl;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.table.SimpleTable;
import net.ramso.doc.dita.elements.topic.Section;

import org.jdom.DefaultJDOMFactory;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;

public class DitaFactory {
	private static DefaultJDOMFactory	factory		= new DefaultJDOMFactory();
	public static final String			BASE_URI	= null;
	

	public static Body createBody() {
		return new Body();
	}

	public static DocType createDocType(IDitaTypes type, String publicId, String systemId) {
		return factory
				.docType(type.getLiteral(), publicId, systemId );
	}

	public static Element createElement(IDitaTypes type) {
		return factory.element(type.getLiteral());
	}

	public static Element createElement(IDitaTypes type, String title) {
		Element element = factory.element(type.getLiteral());
		element.setText(title);
		return element;
	}

	public static Document createTopicDocument() {
		return new TopicDocument();
	}

	/**
	 * @return
	 */
	public static Section createSection() {
		return new Section();
	}

	/**
	 * @return
	 */
	public static TopicRef createTopicRef() {
		return new TopicRef();
	}

	/**
	 * @return
	 */
	public static Dl createDl() {
		return new Dl();
	}

	/**
	 * @return
	 */
	public static SimpleTable createSimpleTable() {
		return new SimpleTable();
	}
}
