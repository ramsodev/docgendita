/**
 * 
 */
package net.ramso.doc.datatools.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.datatools.utils.ResourceUtils;
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
import org.eclipse.datatools.connectivity.sqm.core.rte.EngineeringOption;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.datatools.connectivity.sqm.internal.core.definition.DatabaseDefinitionRegistryImpl;
import org.eclipse.datatools.modelbase.sql.datatypes.ArrayDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.AttributeDefinition;
import org.eclipse.datatools.modelbase.sql.datatypes.DataType;
import org.eclipse.datatools.modelbase.sql.datatypes.DistinctUserDefinedType;
import org.eclipse.datatools.modelbase.sql.datatypes.Field;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.RowDataType;
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
	private String			prefix	= "";	//$NON-NLS-1$

	public ProcessUDT(UserDefinedType udt, String path) {
		this.udt = udt;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
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
			@SuppressWarnings("unused")
			EngineeringOption[] opts = feProvider
					.getOptions(new SQLObject[] { udt });
			String[] ddlScripts = feProvider.generateDDL(
					new SQLObject[] { udt }, monitor);
			String ddlscript = ""; //$NON-NLS-1$
			for (int i = 0; i < ddlScripts.length; i++) {
				ddlscript += ddlScripts[i] + ";\n\n"; //$NON-NLS-1$
			}
			topic.appendSection("DDL", "ddl"); //$NON-NLS-1$ //$NON-NLS-2$
			Section section = topic.getSection("ddl"); //$NON-NLS-1$
			section.addContent(DitaFactory.createElement(
					ProgrammingTypes.CODEBLOCK, ddlscript));
		}
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
		else if (udt instanceof ArrayDataType) {
			dl.addContent(addType((ArrayDataType) udt));
		}
		else if (udt instanceof RowDataType) {
			dl.addContent(addType((RowDataType) udt));
		}
		else {
			return;
		}
		topic.getBody().addContent(dl);
	}

	@SuppressWarnings("unchecked")
	private List<Element> addType(ArrayDataType udt) {
		List<Element> entrys = new ArrayList<Element>(3);
		entrys.add(Dl.getEntry(Messages.ProcessUDT_type_udt,
				Messages.ProcessUDT_array));
		entrys.add(Dl.getEntry(Messages.ProcessUDT_max_cardinality, String
				.valueOf(udt.getMaxCardinality())));
		// Para Eclipse 3.5
		List<DataType> types = udt.getElement();
		if (!types.isEmpty()) {
			for (DataType dataType : types) {
				entrys.add(Dl.getEntry(Messages.ProcessUDT_type,
						getType(dataType)));
			}
		}
		// Para Eclipse 3.6 Helios y IBM Data Studio 2.2
		// ElementType type = udt.getElementType();
		// entrys.add(Dl.getEntry("Tipo", getType(type, this.udt.getSchema())));
		return entrys;
	}

	/**
	 * @param udt2
	 * @return
	 */
	private List<Element> addType(DistinctUserDefinedType udt) {
		List<Element> entrys = new ArrayList<Element>(2);
		entrys.add(Dl.getEntry(Messages.ProcessUDT_type_udt,
				Messages.ProcessUDT_distinct));
		entrys.add(Dl.getEntry(Messages.ProcessUDT_type, getType(udt
				.getPredefinedRepresentation())));
		return entrys;
	}

	@SuppressWarnings("unchecked")
	private List<Element> addType(RowDataType udt) {
		List<Element> entrys = new ArrayList<Element>(2);
		entrys.add(Dl.getEntry(Messages.ProcessUDT_type_udt,
				Messages.ProcessUDT_row));
		List<Field> fields = udt.getFields();
		if (!fields.isEmpty()) {
			Element sl = DitaFactory.createElement(BodyTypes.SL);
			for (Field field : fields) {
				sl.addContent(DitaFactory.createElement(BodyTypes.SLI, field
						.getName()
						+ " (" + getType(field, this.udt.getSchema()) + ")")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			entrys.add(Dl.getEntry(Messages.ProcessUDT_fields, sl));
		}
		return entrys;
	}

	/**
	 * @param udt2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Element> addType(StructuredUserDefinedType udt) {
		List<Element> entrys = new ArrayList<Element>(2);
		entrys.add(Dl.getEntry(Messages.ProcessUDT_type_udt,
				Messages.ProcessUDT_structured));
		List<AttributeDefinition> atrs = udt.getAttributes();
		if (!atrs.isEmpty()) {
			Element sl = DitaFactory.createElement(BodyTypes.SL);
			for (AttributeDefinition attributeDefinition : atrs) {
				sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
						attributeDefinition.getName()
								+ " (" //$NON-NLS-1$
								+ getType(attributeDefinition, this.udt
										.getSchema()) + ")")); //$NON-NLS-1$
			}
			entrys.add(Dl.getEntry(Messages.ProcessUDT_attibutes, sl));
		}
		return entrys;
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
	 * @return
	 */
	public TopicRef getTopicRef() {
		return topicRef;
	}

	private String getType(DataType type) {
		if (type instanceof PredefinedDataType) {
			EObject root = ContainmentServiceImpl.INSTANCE.getRootElement(type);
			if (root instanceof Database) {
				DatabaseDefinition def = DatabaseDefinitionRegistryImpl.INSTANCE
						.getDefinition((Database) root);
				return def
						.getPredefinedDataTypeFormattedName((PredefinedDataType) type);
			}
		}
		return null;
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
					return referencedType.getSchema().getName() + "." //$NON-NLS-1$
							+ referencedType.getName();
				}
				else {
					return referencedType.getName();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		topicRef = DitaFactory.createTopicRef();
		String id = getPrefix() + udt.getName();
		topicRef.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = Messages.ProcessUDT_title + udt.getName();
		if (udt.getDescription() != null) {
			title += " - " + udt.getDescription(); //$NON-NLS-1$
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
		path += File.separator + topicDocument.getFileName();
		ResourceUtils.getInstance().saveDitaFileAsResource(
				topicDocument.getDocumentContent(), path);
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
