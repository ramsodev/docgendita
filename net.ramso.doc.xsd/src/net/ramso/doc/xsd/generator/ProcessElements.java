/**
 * 
 */
package net.ramso.doc.xsd.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.bookmap.Part;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.table.Table;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;
import net.ramso.doc.xsd.utils.ResourceUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xsd.XSDAnnotation;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Element;

/**
 * @author ramso
 */
public class ProcessElements {
	private List<XSDElementDeclaration>	elements;
	private String						path;
	private String						prefix	= "";	//$NON-NLS-1$
	private Chapter						chapter;

	/**
	 * @param elements
	 * @param path
	 */
	public ProcessElements(List<XSDElementDeclaration> elements, String path) {
		this.elements = elements;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		chapter = new Chapter();
		String id = getPrefix() + "_elements_chapter"; //$NON-NLS-1$
		chapter.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = "Elements";
		topic.setTitle(title);
		topic.appendSection("Description", "des"); //$NON-NLS-2$
		@SuppressWarnings("unused")
		Section section = topic.getSection("des"); //$NON-NLS-1$
		ResourceUtils.getInstance().saveDitaFileAsResource(
				topicDocument.getDocumentContent(),
				path + File.separator + topicDocument.getFileName());
		for (XSDElementDeclaration element : elements) {
			System.out.println(element.getName());
			chapter.appendTopicRef(createElement(element));
		}
		ResourceUtils.getInstance().saveDitaFileAsResource(
				topicDocument.getDocumentContent(),
				path + File.separator + topicDocument.getFileName());
		return null;
	}

	/**
	 * @param element
	 * @return
	 * @throws IOException
	 */
	private TopicRef createElement(XSDElementDeclaration element)
			throws IOException {
		TopicRef topicRef = DitaFactory.createTopicRef();
		String id = getPrefix() + element.getName();
		topicRef.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = "Element " + element.getName();
		topic.setTitle(title);
		createGeneral(element.getTypeDefinition(), topic);
		topic.appendSection("Annotations", "des"); //$NON-NLS-2$
		Section section = topic.getSection("des"); //$NON-NLS-1$
		// EList<Element> appInfo =
		// element.getAnnotation().getApplicationInformation();
		// for (Element appinfo : appInfo) {
		// section.appendP(appinfo.getTextContent());
		// }
		try {
			EList<Element> userInfo = element.getAnnotation()
					.getUserInformation();
			for (Element userinfo : userInfo) {
				section.appendP(userinfo.getTextContent());
			}
		}
		catch (Exception e) {
		}
		topic.appendSection("Type", "type"); //$NON-NLS-2$
		section = topic.getSection("type"); //$NON-NLS-1$
		XSDTypeDefinition t = element.getType();
		XSDParticle tc = t.getComplexType();
		System.out.println(tc);
		section.appendP(element.getType().getQName());
		section.appendP(element.getType().getBaseType().getName());
		try {
			EList<Element> userInfo = t.getAnnotation()
					.getUserInformation();
			for (Element userinfo : userInfo) {
				section.appendP(userinfo.getTextContent());
			}
		}
		catch (Exception e) {
		}
		ResourceUtils.getInstance().saveDitaFileAsResource(
				topicDocument.getDocumentContent(),
				path + File.separator + topicDocument.getFileName());
		return topicRef;
	}

	/**
	 * @param typeDefinition
	 * @param topic 
	 * @return
	 */
	private Section createGeneral(XSDTypeDefinition typeDefinition, Topic topic) {
		topic.appendSection("General", "gen");
		Section section = topic.getSection("gen");
		return section;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the chapter
	 */
	public Chapter getChapter() {
		return chapter;
	}
}
