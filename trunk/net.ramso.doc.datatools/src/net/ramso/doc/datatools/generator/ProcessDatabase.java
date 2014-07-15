package net.ramso.doc.datatools.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.bookmap.Part;
import net.ramso.doc.dita.elements.bookmap.Preface;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;
import net.ramso.doc.dita.utils.ResourceUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Comment;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;

public class ProcessDatabase {
	private Database	database;
	private String		path;
	private Preface		preface;
	private List<Part>	parts;

	public ProcessDatabase(Database object, String path) {
		database = object;
		this.path = path;
	}

	/**
	 * @param catalogs
	 * @param topic
	 * @param monitor
	 * @throws IOException
	 */
	private void addCatalogs(List<Catalog> catalogs, Topic topic,
			IProgressMonitor monitor) throws IOException {
		if (!catalogs.isEmpty()) {
			for (Catalog catalog : catalogs) {
				getPreface().appendTopicRef(createCatalog(catalog, monitor));
			}
		}
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
	 * @param catalog
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createCatalog(Catalog catalog, IProgressMonitor monitor)
			throws IOException {
		ProcessCatalog pCatalog = new ProcessCatalog(catalog, path);
		pCatalog.setPrefix(database.getName() + "_"); //$NON-NLS-1$
		pCatalog.process(monitor);
		parts = pCatalog.getParts();
		return pCatalog.getTopicRef();
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
		pSchema.setPrefix(database.getName() + "_"); //$NON-NLS-1$
		pSchema.process(monitor);
		return pSchema.getPart();
	}

	/**
	 * @return the parts
	 */
	public List<Part> getParts() {
		return parts;
	}

	/**
	 * @return
	 */
	public Preface getPreface() {
		return preface;
	}

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		preface = new Preface();
		preface.setHref(database.getName() + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(database.getName());
		String title = Messages.ProcessDatabase_title + database.getName();
		if (database.getLabel() != null) {
			title += " - " + database.getLabel(); //$NON-NLS-1$
		}
		else if (database.getDescription() != null) {
			title += " - " + database.getDescription(); //$NON-NLS-1$
		}
		topic.setTitle(title);
		topic.appendSection(Messages.ProcessDatabase_description, "des"); //$NON-NLS-2$
		Section section = topic.getSection("des"); //$NON-NLS-1$
		section.appendP(database.getDescription());
		section.appendP(Messages.ProcessDatabase_vendor + database.getVendor());
		section.appendP(Messages.ProcessDatabase_version
				+ database.getVersion());
		List<Comment> comments = database.getComments();
		for (Comment comment : comments) {
			section.addContent(DitaFactory.createElement(BodyTypes.P, comment
					.getDescription()));
		}
		if (database.getCatalogs().isEmpty()) {
			addSchemas(database.getSchemas(), topic, monitor);
		}
		else {
			addCatalogs(database.getCatalogs(), topic, monitor);
		}
		path += File.separator + topicDocument.getFileName();
		ResourceUtils.getInstance().saveDitaFileAsResource(
				topicDocument.getDocumentContent(), path);
		return null;
	}
}
