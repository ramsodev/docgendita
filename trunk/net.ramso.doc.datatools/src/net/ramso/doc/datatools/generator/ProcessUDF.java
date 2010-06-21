/**
 * 
 */
package net.ramso.doc.datatools.generator;

import java.io.File;
import java.io.IOException;
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
import org.eclipse.datatools.modelbase.sql.routines.Parameter;
import org.eclipse.datatools.modelbase.sql.routines.RoutineResultTable;
import org.eclipse.datatools.modelbase.sql.routines.UserDefinedFunction;
import org.eclipse.datatools.modelbase.sql.schema.Comment;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.schema.TypedElement;
import org.eclipse.datatools.modelbase.sql.tables.Column;
import org.eclipse.emf.ecore.EObject;
import org.jdom.Element;

/**
 * @author ramso
 */
public class ProcessUDF {
	private TopicRef			topicRef;
	private UserDefinedFunction	udf;
	private String				path;
	private String				prefix	= "";	//$NON-NLS-1$

	public ProcessUDF(UserDefinedFunction udf, String path) {
		this.udf = udf;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	/**
	 * @param monitor
	 * @param topic
	 */
	private void addDDL(Topic topic, IProgressMonitor monitor) {
		Database database = null;
		if (udf.getSchema().getDatabase() != null) {
			database = udf.getSchema().getDatabase();
		}
		else if (udf.getSchema().getCatalog() != null) {
			database = udf.getSchema().getCatalog().getDatabase();
		}
		if (database != null) {
			DatabaseDefinition databaseDefinition = RDBCorePlugin.getDefault()
					.getDatabaseDefinitionRegistry().getDefinition(
							database.getVendor(), database.getVersion());
			DDLGenerator feProvider = databaseDefinition.getDDLGenerator();
			@SuppressWarnings("unused")
			EngineeringOption[] opts = feProvider
					.getOptions(new SQLObject[] { udf });
			String[] ddlScripts = feProvider.generateDDL(
					new SQLObject[] { udf }, monitor);
			String ddlscript = ""; //$NON-NLS-1$
			for (int i = 0; i < ddlScripts.length; i++) {
				ddlscript += ddlScripts[i] + ";\n\n"; //$NON-NLS-1$
			}
			topic.appendSection(Messages.ProcessUDF_ddl, "ddl"); //$NON-NLS-2$
			Section section = topic.getSection("ddl"); //$NON-NLS-1$
			section.addContent(DitaFactory.createElement(
					ProgrammingTypes.CODEBLOCK, ddlscript));
		}
	}

	/**
	 * @param topic
	 * @param monitor
	 */
	@SuppressWarnings("unchecked")
	private void addInfo(Topic topic, IProgressMonitor monitor) {
		Dl dl = new Dl();
		dl.addItem(Messages.ProcessUDF_specific_name, udf.getSpecificName());
		dl.addItem(Messages.ProcessUDF_language, udf.getLanguage());
		dl.addItem(Messages.ProcessUDF_sql_data_access, udf.getSqlDataAccess()
				.getLiteral());
		dl.addItem(Messages.ProcessUDF_deterministic, String.valueOf(udf
				.isDeterministic()));
		Parameter rparams = udf.getReturnCast();
		if (rparams != null) {
			String text = ""; //$NON-NLS-1$
			if (rparams.getName() != null) {
				text += rparams.getName();
			}
			text += " (" + getType(rparams, udf.getSchema()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			dl.addItem(Messages.ProcessUDF_return_cast, text);
		}
		rparams = udf.getReturnScalar();
		if (rparams != null) {
			String text = ""; //$NON-NLS-1$
			if (rparams.getName() != null) {
				text += rparams.getName();
			}
			text += " (" + getType(rparams, udf.getSchema()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			dl.addItem(Messages.ProcessUDF_return_scalar, text);
		}
		RoutineResultTable rpt = udf.getReturnTable();
		if (rpt != null) {
			List<Column> columns = rpt.getColumns();
			if (!columns.isEmpty()) {
				Element sl = DitaFactory.createElement(BodyTypes.SL);
				for (Column column : columns) {
					sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
							column.getName() + " (" //$NON-NLS-1$
									+ getType(column, udf.getSchema()) + ")")); //$NON-NLS-1$
				}
				dl.addItem(Messages.ProcessUDF_return, sl);
			}
		}
		List<Parameter> in = udf.getParameters();
		if (!in.isEmpty()) {
			Element sl = DitaFactory.createElement(BodyTypes.SL);
			for (Parameter column : in) {
				String text = ""; //$NON-NLS-1$
				if (column.getName() != null) {
					text += column.getName();
				}
				text += " (" + getType(column, udf.getSchema()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
				sl.addContent(DitaFactory.createElement(BodyTypes.SLI, text));
			}
			dl.addItem(Messages.ProcessUDF_parameters, sl);
		}
		List<Parameter> out = udf.getOutputParameters();
		if (!out.isEmpty()) {
			Element sl = DitaFactory.createElement(BodyTypes.SL);
			for (Parameter column : out) {
				sl.addContent(DitaFactory.createElement(BodyTypes.SLI, column
						.getName()
						+ " (" + getType(column, udf.getSchema()) + ")")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			dl.addItem(Messages.ProcessUDF_output_parameters, sl);
		}
		topic.getBody().addContent(dl);
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
		String id = getPrefix() + udf.getName();
		topicRef.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = Messages.ProcessUDF_title + udf.getName();
		if (udf.getDescription() != null) {
			title += " - " + udf.getDescription(); //$NON-NLS-1$
		}
		topic.setTitle(title);
		topic.getBody().addContent(
				DitaFactory.createElement(BodyTypes.P, udf.getDescription()));
		List<Comment> comments = udf.getComments();
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
