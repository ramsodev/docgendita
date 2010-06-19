package net.ramso.doc.dita.Documents;

import net.ramso.doc.dita.elements.TopicTypes;
import net.ramso.doc.dita.elements.topic.Topic;

public class TopicDocument extends BaseDitaDocument {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	public static final String	PUBLIC_ID			= "-//OASIS//DTD DITA Topic//EN";
	public static final String	SYSTEM_ID			= "http://docs.oasis-open.org/dita/v1.1/OS/dtd/topic.dtd";

	public TopicDocument() {
		super(TopicTypes.TOPIC, PUBLIC_ID, SYSTEM_ID);
		setRootElement(new Topic());
		EXT = ".dita";
	}

	/**
	 * @return
	 */
	public Topic getTopic() {
		return (Topic) getElementRoot();
	}
}
