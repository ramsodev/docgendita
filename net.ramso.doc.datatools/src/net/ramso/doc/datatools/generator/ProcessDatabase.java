package net.ramso.doc.datatools.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.bookmap.Part;
import net.ramso.doc.dita.elements.bookmap.Preface;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;

public class ProcessDatabase {
	private Database	database;
	private String		path;
	private Preface		preface;
	private List<Part>	parts;

	public ProcessDatabase(Database object, String path) {
		this.database = object;
		this.path = path;
	}

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		preface = new Preface();
		preface.setHref(database.getName() + ".dita");
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(database.getName());
		String title = "Base de Datos " + database.getName();
		if (database.getDescription() != null) {
			title += " - " + database.getDescription();
		}
		topic.setTitle(title);
		topic.appendSection("Descripción", "des");
		Section section = topic.getSection("des");
		section.appendP(database.getDescription());
		section.appendP("Fabricante: " + database.getVendor());
		section.appendP("Version: " + database.getVersion());
		if (database.getCatalogs().isEmpty()) {
			addSchemas(database.getSchemas(), topic, monitor);
		}
		else {
			addCatalogs(database.getCatalogs(), topic, monitor);
		}
		topicDocument.save(path);
		return null;
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
	 * @param catalog
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createCatalog(Catalog catalog, IProgressMonitor monitor)
			throws IOException {
		ProcessCatalog pCatalog = new ProcessCatalog(catalog, path);
		pCatalog.setPrefix(database.getName() + "_");
		pCatalog.process(monitor);
		parts = pCatalog.getParts();
		return pCatalog.getTopicRef();
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
		pSchema.setPrefix(database.getName() + "_");
		pSchema.process(monitor);
		return pSchema.getPart();
	}

	/**
	 * @return
	 */
	public Preface getPreface() {
		return preface;
	}

	/**
	 * @return the parts
	 */
	public List<Part> getParts() {
		return parts;
	}
}
