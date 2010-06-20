package net.ramso.doc.datatools.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.bookmap.Part;
import net.ramso.doc.dita.elements.bookmap.Preface;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Schema;

public class ProcessCatalog {
	private Catalog			catalog;
	private String			path;
	private TopicRef		topicRef;
	private String			prefix	= ""; //$NON-NLS-1$
	private boolean			isPreface;
	private Preface			preface;
	private ArrayList<Part>	parts;

	public ProcessCatalog(Catalog calatog, String path) {
		this(calatog, path, false);
	}

	/**
	 * @param catalog
	 * @param path2
	 * @param b
	 */
	public ProcessCatalog(Catalog catalog, String path, boolean preface) {
		this.catalog = catalog;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
		isPreface = preface;
	}

	/**
	 * @param schemas
	 * @param topic
	 * @param monitor
	 * @throws IOException
	 */
	private void addSchemas(List<Schema> schemas, Topic topic,
			IProgressMonitor monitor) throws IOException {
		if (!schemas.isEmpty()) {
			parts = new ArrayList<Part>(schemas.size());
			for (Schema schema : schemas) {
				parts.add(createSchema(schema, monitor));
			}
		}
	}

	/**
	 * @param schema
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private Part createSchema(Schema schema, IProgressMonitor monitor)
			throws IOException {
		ProcessSchema pSchema = new ProcessSchema(schema, path);
		pSchema.setPrefix(getPrefix() + catalog.getName() + "_"); //$NON-NLS-1$
		pSchema.process(monitor);
		return pSchema.getPart();
	}

	public Chapter getChapter() {
		Chapter chapter = new Chapter(getTopicRef());
		return chapter;
	}

	/**
	 * @return the parts
	 */
	public ArrayList<Part> getParts() {
		return parts;
	}

	/**
	 * @return the preface
	 */
	public Preface getPreface() {
		return preface;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @return
	 */
	public TopicRef getTopicRef() {
		return topicRef;
	}

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		String id = getPrefix() + catalog.getName();
		if (isPreface) {
			preface = new Preface();
			preface.setHref(id + ".dita"); //$NON-NLS-1$
		}
		else {
			topicRef = DitaFactory.createTopicRef();
			topicRef.setHref(id + ".dita"); //$NON-NLS-1$
		}
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = Messages.ProcessCatalog_title + catalog.getName();
		if (catalog.getDescription() != null) {
			title += " - " + catalog.getDescription(); //$NON-NLS-1$
		}
		topic.setTitle(title);
		topic.appendSection(Messages.ProcessCatalog_description, "des"); //$NON-NLS-2$
		Section section = topic.getSection("des"); //$NON-NLS-1$
		section.appendP(catalog.getDescription());
		addSchemas(catalog.getSchemas(), topic, monitor);
		topicDocument.save(path);
		return null;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
