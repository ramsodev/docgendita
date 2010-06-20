/**
 * 
 */
package net.ramso.doc.datatools.generator;

import java.io.IOException;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.attributes.AlignValues;
import net.ramso.doc.dita.attributes.FrameValues;
import net.ramso.doc.dita.attributes.VerticalAlignValues;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.ProgrammingTypes;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.table.ColSpec;
import net.ramso.doc.dita.elements.table.Entry;
import net.ramso.doc.dita.elements.table.Row;
import net.ramso.doc.dita.elements.table.TBody;
import net.ramso.doc.dita.elements.table.TGroup;
import net.ramso.doc.dita.elements.table.THead;
import net.ramso.doc.dita.elements.table.Table;
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
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.schema.TypedElement;
import org.eclipse.datatools.modelbase.sql.tables.Column;
import org.eclipse.datatools.modelbase.sql.tables.ViewTable;
import org.eclipse.emf.ecore.EObject;

/**
 * @author ramso
 */
public class ProcessView {
	private TopicRef	topicRef;
	private ViewTable	view;
	private String		path;
	private String		prefix	= "";	//$NON-NLS-1$

	public ProcessView(ViewTable view, String path) {
		this.view = view;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	/**
	 * @param columns
	 * @param topic
	 */
	private void addColumns(List<Column> columns, Topic topic) {
		if (columns.isEmpty())
			return;
		String[] heads = { Messages.ProcessView_columns_headers_name,
				Messages.ProcessView_columns_headers_description,
				Messages.ProcessView_columns_headers_type };
		String[] sizes = { "2*", "6*", "2*" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		AlignValues[] aligns = { AlignValues.LEFT, AlignValues.LEFT,
				AlignValues.LEFT };
		Table table = new Table();
		table.setFrame(FrameValues.ALL);
		table.setColSep(true);
		table.setRowSep(true);
		table.setPGWide(true);
		// table.setScale(ScaleValues._80);
		table.setID("table_columns"); //$NON-NLS-1$
		table.setTitle(Messages.ProcessView_columns_table_title);
		table.setTGroup(new TGroup());
		table.getTGroup().setCols(heads.length);
		for (int i = 0; i < heads.length; i++) {
			ColSpec colSpec = new ColSpec();
			colSpec.setAlign(aligns[i]);
			colSpec.setColName(heads[i]);
			colSpec.setColNum(i + 1);
			colSpec.setColWidth(sizes[i]);
			table.getTGroup().appendColSpec(colSpec);
		}
		table.getTGroup().setTHead(new THead());
		Row row = new Row();
		row.setVAlign(VerticalAlignValues.MIDDLE);
		for (String head : heads) {
			Entry entry = new Entry();
			entry.setText(head);
			entry.setAlign(AlignValues.CENTER);
			row.addContent(entry);
		}
		table.getTGroup().getTHead().appendRow(row);
		table.getTGroup().setTBody(new TBody());
		for (Column column : columns) {
			row = new Row();
			row.setVAlign(VerticalAlignValues.MIDDLE);
			Entry entry = new Entry();
			entry.setText(column.getName());
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(column.getDescription());
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(getType(column, view.getSchema()));
			row.appendEntry(entry);
			table.getTGroup().getTBody().appendRow(row);
		}
		topic.getBody().addContent(table);
	}

	/**
	 * @param monitor
	 * @param topic
	 */
	private void addDDL(Topic topic, IProgressMonitor monitor) {
		Database database = null;
		if (view.getSchema().getDatabase() != null) {
			database = view.getSchema().getDatabase();
		}
		else if (view.getSchema().getCatalog() != null) {
			database = view.getSchema().getCatalog().getDatabase();
		}
		if (database != null) {
			DatabaseDefinition databaseDefinition = RDBCorePlugin.getDefault()
					.getDatabaseDefinitionRegistry().getDefinition(
							database.getVendor(), database.getVersion());
			DDLGenerator feProvider = databaseDefinition.getDDLGenerator();
			@SuppressWarnings("unused")
			EngineeringOption[] opts = feProvider
					.getOptions(new SQLObject[] { view });
			String[] ddlScripts = new String[0];
			try {
				ddlScripts = feProvider.generateDDL(new SQLObject[] { view },
						monitor);
			}
			catch (Exception e) {
			}
			String ddlscript = ""; //$NON-NLS-1$
			for (int i = 0; i < ddlScripts.length; i++) {
				ddlscript += ddlScripts[i] + ";\n\n"; //$NON-NLS-1$
			}
			topic.appendSection(Messages.ProcessView_ddl, "ddl"); //$NON-NLS-2$
			Section section = topic.getSection("ddl"); //$NON-NLS-1$
			section.addContent(DitaFactory.createElement(
					ProgrammingTypes.CODEBLOCK, ddlscript));
		}
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
		String id = getPrefix() + view.getName();
		topicRef.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = Messages.ProcessView_title + view.getName();
		if (view.getDescription() != null) {
			title += " - " + view.getDescription(); //$NON-NLS-1$
		}
		topic.setTitle(title);
		topic.getBody().addContent(
				DitaFactory.createElement(BodyTypes.P, view.getDescription()));
		List<Comment> comments = view.getComments();
		for (Comment comment : comments) {
			topic.getBody().addContent(
					DitaFactory.createElement(BodyTypes.P, comment
							.getDescription()));
		}
		addColumns(view.getColumns(), topic);
		addDDL(topic, monitor);
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
