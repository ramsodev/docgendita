/**
 * 
 */
package net.ramso.doc.datatools.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.ProgrammingTypes;
import net.ramso.doc.dita.elements.body.Dl;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.connectivity.sqm.core.containment.ContainmentServiceImpl;
import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.datatools.connectivity.sqm.core.rte.DDLGenerator;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.datatools.connectivity.sqm.internal.core.definition.DatabaseDefinitionRegistryImpl;
import org.eclipse.datatools.modelbase.sql.datatypes.ArrayDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.AttributeDefinition;
import org.eclipse.datatools.modelbase.sql.datatypes.DistinctUserDefinedType;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.SQLDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.StructuredUserDefinedType;
import org.eclipse.datatools.modelbase.sql.datatypes.UserDefinedType;
import org.eclipse.datatools.modelbase.sql.schema.Comment;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.schema.TypedElement;
import org.eclipse.emf.ecore.EObject;
import org.jdom.Element;

/**
 * @author ramso
 */
public class ProcessUDT {
	private TopicRef		topicRef;
	private UserDefinedType	udt;
	private String			path;
	private String			prefix	= "";

	public ProcessUDT(UserDefinedType udt, String path) {
		this.udt = udt;
		this.path = path;
		this.prefix = "";
	}

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		topicRef = DitaFactory.createTopicRef();
		String id = getPrefix() + udt.getName();
		topicRef.setHref(id + ".dita");
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = "Tipo definido por el usuario " + udt.getName();
		if (udt.getDescription() != null) {
			title += " - " + udt.getDescription();
		}
		topic.setTitle(title);
		topic.getBody().addContent(
				DitaFactory.createElement(BodyTypes.P, udt.getDescription()));
		List<Comment> comments = udt.getComments();
		for (Comment comment : comments) {
			topic.getBody().addContent(
					DitaFactory.createElement(BodyTypes.P, comment
							.getDescription()));
		}
		addInfo(topic, monitor);
		addDDL(topic, monitor);
		topicDocument.save(path);
		return null;
	}

	/**
	 * @param topic
	 * @param monitor
	 */
	private void addInfo(Topic topic, IProgressMonitor monitor) {
		Dl dl = new Dl();
		if (udt instanceof DistinctUserDefinedType) {
			dl.addContent(addType((DistinctUserDefinedType) udt));
		}
		else if (udt instanceof StructuredUserDefinedType) {
			dl.addContent(addType((StructuredUserDefinedType) udt));
		}
//		else if (udt instanceof ArrayDataType) {
//			dl.addContent(addType((StructuredUserDefinedType) udt));
//		}
		topic.getBody().addContent(dl);
	}

	/**
	 * @param udt2
	 * @return
	 */
	private List<Element> addType(StructuredUserDefinedType udt) {
		List<Element> entrys = new ArrayList<Element>(2);
		entrys.add(Dl.getEntry("Tipo", "Distinct"));
		List<AttributeDefinition> atrs = udt.getAttributes();
		if (!atrs.isEmpty()) {
			Element sl = DitaFactory.createElement(BodyTypes.SL);
			for (AttributeDefinition attributeDefinition : atrs) {
				sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
						attributeDefinition.getName() + " ("
								+ getType(attributeDefinition, udt.getSchema())
								+ ")"));
			}
			entrys.add(Dl.getEntry("Atributos", sl));
		}
		
		return entrys;
	}

	/**
	 * @param udt2
	 * @return
	 */
	private List<Element> addType(DistinctUserDefinedType udt) {
		List<Element> entrys = new ArrayList<Element>(2);
		entrys.add(Dl.getEntry("Tipo", "Distinct"));
		entrys.add(Dl.getEntry("Tipo", getType(udt
				.getPredefinedRepresentation())));
		return entrys;
	}

	/**
	 * @param monitor
	 * @param topic
	 */
	private void addDDL(Topic topic, IProgressMonitor monitor) {
		Database database = null;
		if (udt.getSchema().getDatabase() != null) {
			database = udt.getSchema().getDatabase();
		}
		else if (udt.getSchema().getCatalog() != null) {
			database = udt.getSchema().getCatalog().getDatabase();
		}
		if (database != null) {
			DatabaseDefinition databaseDefinition = RDBCorePlugin.getDefault()
					.getDatabaseDefinitionRegistry().getDefinition(
							database.getVendor(), database.getVersion());
			DDLGenerator feProvider = databaseDefinition.getDDLGenerator();
			// String[] ddlScripts = feProvider.generateDDL(
			// new SQLObject[] { procedure }, monitor);
			String[] ddlScripts = feProvider.createSQLObjects(
					new SQLObject[] { udt }, false, false, monitor);
			String ddlscript = "";
			for (int i = 0; i < ddlScripts.length; i++) {
				ddlscript += ddlScripts[i] + ";\n\n";
			}
			topic.appendSection("DDL", "ddl");
			Section section = topic.getSection("ddl");
			section.addContent(DitaFactory.createElement(
					ProgrammingTypes.CODEBLOCK, ddlscript));
		}
	}

	/**
	 * @param containedType
	 * @return
	 */
	private String getType(TypedElement typedElement, Schema schema) {
		SQLDataType containedType = typedElement.getContainedType();
		if (containedType != null) {
			if (containedType instanceof PredefinedDataType) {
				EObject root = ContainmentServiceImpl.INSTANCE
						.getRootElement(typedElement);
				if (root instanceof Database) {
					DatabaseDefinition def = DatabaseDefinitionRegistryImpl.INSTANCE
							.getDefinition((Database) root);
					return def
							.getPredefinedDataTypeFormattedName((PredefinedDataType) containedType);
				}
			}
		}
		else {
			UserDefinedType referencedType = typedElement.getReferencedType();
			if (referencedType != null) {
				if (referencedType.getSchema() != schema) {
					return referencedType.getSchema().getName() + "."
							+ referencedType.getName();
				}
				else {
					return referencedType.getName();
				}
			}
		}
		return null;
	}

	private String getType(PredefinedDataType type) {
		EObject root = ContainmentServiceImpl.INSTANCE.getRootElement(type);
		if (root instanceof Database) {
			DatabaseDefinition def = DatabaseDefinitionRegistryImpl.INSTANCE
					.getDefinition((Database) root);
			return def.getPredefinedDataTypeFormattedName(type);
		}
		return null;
	}

	/**
	 * @return
	 */
	public TopicRef getTopicRef() {
		return topicRef;
	}

	public Chapter getChapter() {
		Chapter chapter = new Chapter(getTopicRef());
		return chapter;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
