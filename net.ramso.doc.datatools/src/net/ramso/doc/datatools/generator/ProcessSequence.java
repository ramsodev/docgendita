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
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.SQLDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.UserDefinedType;
import org.eclipse.datatools.modelbase.sql.schema.Comment;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.IdentitySpecifier;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.schema.Sequence;
import org.eclipse.datatools.modelbase.sql.schema.TypedElement;
import org.eclipse.emf.ecore.EObject;
import org.jdom.Element;

/**
 * @author ramso
 */
public class ProcessSequence {
	private TopicRef	topicRef;
	private Sequence	sequence;
	private String		path;
	private String		prefix	= "";	//$NON-NLS-1$

	public ProcessSequence(Sequence sequence, String path) {
		this.sequence = sequence;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	/**
	 * @param monitor
	 * @param topic
	 */
	private void addDDL(Topic topic, IProgressMonitor monitor) {
		Database database = null;
		if (sequence.getSchema().getDatabase() != null) {
			database = sequence.getSchema().getDatabase();
		}
		else if (sequence.getSchema().getCatalog() != null) {
			database = sequence.getSchema().getCatalog().getDatabase();
		}
		if (database != null) {
			DatabaseDefinition databaseDefinition = RDBCorePlugin.getDefault()
					.getDatabaseDefinitionRegistry().getDefinition(
							database.getVendor(), database.getVersion());
			DDLGenerator feProvider = databaseDefinition.getDDLGenerator();
			@SuppressWarnings("unused")
			EngineeringOption[] opts = feProvider
					.getOptions(new SQLObject[] { sequence });
			String[] ddlScripts = feProvider.generateDDL(
					new SQLObject[] { sequence }, monitor);
			ddlScripts = feProvider.createSQLObjects(
					new SQLObject[] { sequence }, false, false, monitor);
			String ddlscript = ""; //$NON-NLS-1$
			for (int i = 0; i < ddlScripts.length; i++) {
				ddlscript += ddlScripts[i] + ";\n\n"; //$NON-NLS-1$
			}
			topic.appendSection(Messages.ProcessSequence_ddl, "ddl"); //$NON-NLS-2$
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
		dl.addItem(Messages.ProcessSequence_type, getType(sequence, sequence
				.getSchema()));
		dl.addContent(getID(sequence.getIdentity()));
		topic.getBody().addContent(dl);
	}

	public Chapter getChapter() {
		Chapter chapter = new Chapter(getTopicRef());
		return chapter;
	}

	private List<Element> getID(IdentitySpecifier id) {
		List<Element> dlentrys = new ArrayList<Element>(7);
		dlentrys.add(Dl.getEntry(Messages.ProcessSequence_start, String
				.valueOf(id.getStartValue())));
		dlentrys.add(Dl.getEntry(Messages.ProcessSequence_increment, String
				.valueOf(id.getIncrement())));
		dlentrys.add(Dl.getEntry(Messages.ProcessSequence_minimum, String
				.valueOf(id.getMinimum())));
		dlentrys.add(Dl.getEntry(Messages.ProcessSequence_maximum, String
				.valueOf(id.getMaximum())));
		dlentrys.add(Dl.getEntry(Messages.ProcessSequence_start, String
				.valueOf(id.getStartValue())));
		dlentrys.add(Dl.getEntry(Messages.ProcessSequence_cycle, String
				.valueOf(id.isCycleOption())));
		return dlentrys;
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
		String id = getPrefix() + sequence.getName();
		topicRef.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = Messages.ProcessSequence_title + sequence.getName();
		if (sequence.getDescription() != null) {
			title += " - " + sequence.getDescription(); //$NON-NLS-1$
		}
		topic.setTitle(title);
		topic.getBody().addContent(
				DitaFactory.createElement(BodyTypes.P, sequence
						.getDescription()));
		List<Comment> comments = sequence.getComments();
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
