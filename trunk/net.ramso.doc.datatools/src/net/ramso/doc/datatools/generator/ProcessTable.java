/**
 * 
 */
package net.ramso.doc.datatools.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.datatools.utils.ResourceUtils;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.attributes.AlignValues;
import net.ramso.doc.dita.attributes.FrameValues;
import net.ramso.doc.dita.attributes.ScaleValues;
import net.ramso.doc.dita.attributes.VerticalAlignValues;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.ProgrammingTypes;
import net.ramso.doc.dita.elements.body.Dl;
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
import org.eclipse.datatools.modelbase.sql.constraints.CheckConstraint;
import org.eclipse.datatools.modelbase.sql.constraints.ForeignKey;
import org.eclipse.datatools.modelbase.sql.constraints.Index;
import org.eclipse.datatools.modelbase.sql.constraints.IndexMember;
import org.eclipse.datatools.modelbase.sql.constraints.PrimaryKey;
import org.eclipse.datatools.modelbase.sql.constraints.TableConstraint;
import org.eclipse.datatools.modelbase.sql.constraints.UniqueConstraint;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.SQLDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.UserDefinedType;
import org.eclipse.datatools.modelbase.sql.schema.Comment;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.IdentitySpecifier;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.schema.TypedElement;
import org.eclipse.datatools.modelbase.sql.statements.SQLStatement;
import org.eclipse.datatools.modelbase.sql.tables.Column;
import org.eclipse.datatools.modelbase.sql.tables.PersistentTable;
import org.eclipse.datatools.modelbase.sql.tables.Trigger;
import org.eclipse.emf.ecore.EObject;
import org.jdom.Element;

/**
 * @author ramso
 */
public class ProcessTable {
	private TopicRef		topicRef;
	private PersistentTable	persistentTable;
	private String			path;
	private String			prefix	= "";		//$NON-NLS-1$

	public ProcessTable(PersistentTable persistentTable, String path) {
		this.persistentTable = persistentTable;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	/**
	 * @param constraints
	 * @param topic
	 */
	private void addCK(List<TableConstraint> constraints, Topic topic) {
		ArrayList<CheckConstraint> checks = new ArrayList<CheckConstraint>();
		for (TableConstraint constraint : constraints) {
			if (constraint instanceof CheckConstraint) {
				checks.add((CheckConstraint) constraint);
			}
		}
		if (!checks.isEmpty()) {
			for (CheckConstraint check : checks) {
				topic.appendSection(Messages.ProcessTable_check
						+ check.getName(), "ck_" //$NON-NLS-2$
						+ check.getName());
				Section section = topic.getSection("ck_" + check.getName()); //$NON-NLS-1$
				if (check.getDescription() != null) {
					section.appendP(check.getDescription());
				}
				section.addContent(DitaFactory.createElement(
						ProgrammingTypes.CODEBLOCK, check.getSearchCondition()
								.getSQL()));
			}
		}
	}

	/**
	 * @param columns
	 * @param topic
	 */
	private void addColumns(List<Column> columns, Topic topic) {
		String[] heads = { Messages.ProcessTable_colums_headers_name,
				Messages.ProcessTable_colums_headers_type,
				Messages.ProcessTable_colums_headers_defvalue,
				Messages.ProcessTable_colums_headers_id,
				Messages.ProcessTable_colums_headers_null,
				Messages.ProcessTable_colums_headers_pk,
				Messages.ProcessTable_colums_headers_fk,
				Messages.ProcessTable_colums_headers_uq };
		String[] sizes = { "2*", "2*", "3*", "1*", "1*", "1*", "1*", "1*" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		AlignValues[] aligns = { AlignValues.LEFT, AlignValues.LEFT,
				AlignValues.LEFT, AlignValues.CENTER, AlignValues.CENTER,
				AlignValues.CENTER, AlignValues.CENTER, AlignValues.CENTER };
		Table table = new Table();
		table.setFrame(FrameValues.ALL);
		table.setColSep(true);
		table.setRowSep(true);
		table.setPGWide(true);
		table.setScale(ScaleValues._80);
		table.setID("table_columns"); //$NON-NLS-1$
		table.setTitle(Messages.ProcessTable_colums_table_title);
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
			entry.setMoreRows(1);
			entry.setText(column.getName());
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(getType(column, persistentTable.getSchema()));
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(column.getDefaultValue());
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(String.valueOf(getID(column.getIdentitySpecifier())));
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(String.valueOf(getBoleanChar(column.isNullable())));
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(String.valueOf(getBoleanChar(column
					.isPartOfPrimaryKey())));
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(String.valueOf(getBoleanChar(column
					.isPartOfForeignKey())));
			row.appendEntry(entry);
			entry = new Entry();
			entry.setText(String.valueOf(getBoleanChar(column
					.isPartOfUniqueConstraint())));
			row.appendEntry(entry);
			table.getTGroup().getTBody().appendRow(row);
			row = new Row();
			entry = new Entry();
			entry.setText(column.getDescription());
			entry.setColName("DES"); //$NON-NLS-1$
			entry.setNamest(heads[1]);
			entry.setNameEnd(heads[7]);
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
		if (persistentTable.getSchema().getDatabase() != null) {
			database = persistentTable.getSchema().getDatabase();
		}
		else if (persistentTable.getSchema().getCatalog() != null) {
			database = persistentTable.getSchema().getCatalog().getDatabase();
		}
		if (database != null) {
			DatabaseDefinition databaseDefinition = RDBCorePlugin.getDefault()
					.getDatabaseDefinitionRegistry().getDefinition(
							database.getVendor(), database.getVersion());
			DDLGenerator feProvider = databaseDefinition.getDDLGenerator();
			@SuppressWarnings("unused")
			EngineeringOption[] opts = feProvider
					.getOptions(new SQLObject[] { persistentTable });
			String[] ddlScripts = feProvider.generateDDL(
					new SQLObject[] { persistentTable }, monitor);
			String ddlscript = ""; //$NON-NLS-1$
			for (int i = 0; i < ddlScripts.length; i++) {
				ddlscript += ddlScripts[i] + ";\n\n"; //$NON-NLS-1$
			}
			topic.appendSection(Messages.ProcessTable_ddl, "ddl"); //$NON-NLS-2$
			Section section = topic.getSection("ddl"); //$NON-NLS-1$
			section.addContent(DitaFactory.createElement(
					ProgrammingTypes.CODEBLOCK, ddlscript));
		}
	}

	/**
	 * @param foreignKeys
	 * @param topic
	 */
	@SuppressWarnings("unchecked")
	private void addFK(List<ForeignKey> foreignKeys, Topic topic) {
		if (!foreignKeys.isEmpty()) {
			for (ForeignKey foreignKey : foreignKeys) {
				topic.appendSection(Messages.ProcessTable_fk
						+ foreignKey.getName(), "fk_" + foreignKey.getName()); //$NON-NLS-1$
				Section section = topic
						.getSection("fk_" + foreignKey.getName()); //$NON-NLS-1$
				if (foreignKey.getDescription() != null) {
					section.appendP(foreignKey.getDescription());
				}
				Dl dl = section.getDL("fk_dl_" + foreignKey.getName(), true); //$NON-NLS-1$
				List<Column> columns = foreignKey.getMembers();
				if (!columns.isEmpty()) {
					Element sl = DitaFactory.createElement(BodyTypes.SL);
					for (Column column : columns) {
						sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
								column.getName()));
					}
					dl.addItem(Messages.ProcessTable_columns, sl);
				}
				UniqueConstraint uniqueConstraint = foreignKey
						.getUniqueConstraint();
				Index index = foreignKey.getUniqueIndex();
				org.eclipse.datatools.modelbase.sql.tables.Table parentTable = null;
				List<Column> relColumns = null;
				if (uniqueConstraint != null) {
					parentTable = uniqueConstraint.getBaseTable();
					relColumns = uniqueConstraint.getMembers();
				}
				else if (index != null) {
					parentTable = index.getTable();
					relColumns = index.getMembers();
				}
				if (parentTable != null && !relColumns.isEmpty()) {
					Element sl = DitaFactory.createElement(BodyTypes.SL);
					for (Column column : relColumns) {
						sl
								.addContent(DitaFactory.createElement(
										BodyTypes.SLI, parentTable.getName()
												+ "." + column.getName())); //$NON-NLS-1$
					}
					dl.addItem(Messages.ProcessTable_ref_columns, sl);
				}
				dl.addItem(Messages.ProcessTable_onupdate, foreignKey
						.getOnUpdate().getLiteral());
				dl.addItem(Messages.ProcessTable_ondelete, foreignKey
						.getOnDelete().getLiteral());
			}
		}
	}

	/**
	 * @param index
	 * @param topic
	 */
	@SuppressWarnings("unchecked")
	private void addIndex(List<Index> index, Topic topic) {
		if (!index.isEmpty()) {
			for (Index idx : index) {
				topic.appendSection(
						Messages.ProcessTable_index + idx.getName(), "idx_" //$NON-NLS-2$
								+ idx.getName());
				Section section = topic.getSection("idx_" + idx.getName()); //$NON-NLS-1$
				if (idx.getDescription() != null) {
					section.appendP(idx.getDescription());
				}
				Dl dl = section.getDL("idx" + idx.getName(), true); //$NON-NLS-1$
				List<IndexMember> cols = idx.getMembers();
				if (!cols.isEmpty()) {
					Element sl = DitaFactory.createElement(BodyTypes.SL);
					for (IndexMember column : cols) {
						sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
								column.getColumn().getName()
										+ " (" //$NON-NLS-1$
										+ column.getIncrementType()
												.getLiteral() + ")")); //$NON-NLS-1$
					}
					dl.addItem(Messages.ProcessTable_columns, sl);
				}
				dl.addItem(Messages.ProcessTable_unique, String.valueOf(idx
						.isUnique()));
			}
		}
	}

	/**
	 * @param primaryKey
	 * @param topic
	 */
	@SuppressWarnings("unchecked")
	private void addPK(PrimaryKey primaryKey, Topic topic) {
		if (primaryKey != null) {
			topic.appendSection(
					Messages.ProcessTable_pk + primaryKey.getName(), "pk"); //$NON-NLS-2$
			Section section = topic.getSection("pk"); //$NON-NLS-1$
			if (primaryKey.getDescription() != null) {
				section.appendP(primaryKey.getDescription());
			}
			List<Column> columns = primaryKey.getMembers();
			if (!columns.isEmpty()) {
				Dl dl = new Dl();
				Element sl = DitaFactory.createElement(BodyTypes.SL);
				for (Column column : columns) {
					sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
							column.getName()));
				}
				dl.addItem(Messages.ProcessTable_columns, sl);
				section.addContent(dl);
			}
		}
	}

	/**
	 * @param triggers
	 * @param topic
	 */
	@SuppressWarnings("unchecked")
	private void addTrigers(List<Trigger> triggers, Topic topic) {
		if (!triggers.isEmpty()) {
			for (Trigger trigger : triggers) {
				topic.appendSection(Messages.ProcessTable_trigger
						+ trigger.getName(), "trg_" //$NON-NLS-2$
						+ trigger.getName());
				Section section = topic.getSection("trg_" + trigger.getName()); //$NON-NLS-1$
				Dl dl = section.getDL("trg_" + trigger.getName(), true); //$NON-NLS-1$
				dl.addItem(Messages.ProcessTable_actiontime, trigger
						.getActionTime().getLiteral());
				dl.addItem(Messages.ProcessTable_granularity, trigger
						.getActionGranularity().getLiteral());
				dl.addItem(Messages.ProcessTable_eventtype,
						getEventType(trigger));
				if (trigger.getOldTable() != null
						&& !trigger.getOldTable().isEmpty()) {
					dl.addItem(Messages.ProcessTable_oldtable, trigger
							.getOldTable());
				}
				if (trigger.getOldRow() != null
						&& !trigger.getOldRow().isEmpty()) {
					dl.addItem(Messages.ProcessTable_oldrow, trigger
							.getOldRow());
				}
				if (trigger.getNewTable() != null
						&& !trigger.getNewTable().isEmpty()) {
					dl.addItem(Messages.ProcessTable_newtable, trigger
							.getNewTable());
				}
				if (trigger.getNewRow() != null
						&& !trigger.getNewRow().isEmpty()) {
					dl.addItem(Messages.ProcessTable_newrow, trigger
							.getNewRow());
				}
				dl.addItem(Messages.ProcessTable_when, DitaFactory
						.createElement(ProgrammingTypes.CODEBLOCK, trigger
								.getWhen().getSQL()));
				String triggerBody = ""; //$NON-NLS-1$
				Collection<SQLStatement> body = trigger.getActionStatement();
				for (SQLStatement sqlStatement : body) {
					triggerBody += sqlStatement.getSQL() + "\n"; //$NON-NLS-1$
				}
				dl
						.addItem(Messages.ProcessTable_body, DitaFactory
								.createElement(ProgrammingTypes.CODEBLOCK,
										triggerBody));
			}
		}
	}

	/**
	 * @param uniqueConstraints
	 * @param topic
	 */
	@SuppressWarnings("unchecked")
	private void addUQ(List<UniqueConstraint> uniqueConstraints, Topic topic) {
		if (!uniqueConstraints.isEmpty()) {
			for (UniqueConstraint uniqueConstraint : uniqueConstraints) {
				topic.appendSection(Messages.ProcessTable_uq
						+ uniqueConstraint.getName(), "uq_" //$NON-NLS-2$
						+ uniqueConstraint.getName());
				Section section = topic.getSection("uq_" //$NON-NLS-1$
						+ uniqueConstraint.getName());
				if (uniqueConstraint.getDescription() != null) {
					section.appendP(uniqueConstraint.getDescription());
				}
				Dl dl = section.getDL("uk_dl_" + uniqueConstraint.getName(), //$NON-NLS-1$
						true);
				List<Column> columns = uniqueConstraint.getMembers();
				if (!columns.isEmpty()) {
					Element sl = DitaFactory.createElement(BodyTypes.SL);
					for (Column column : columns) {
						sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
								column.getName()));
					}
					dl.addItem(Messages.ProcessTable_columns, sl);
				}
			}
		}
	}

	/**
	 * @param nullable
	 * @return
	 */
	private String getBoleanChar(boolean b) {
		if (b) {
			return Messages.ProcessTable_true;
		}
		else {
			return Messages.ProcessTable_false;
		}
	}

	public Chapter getChapter() {
		Chapter chapter = new Chapter(getTopicRef());
		return chapter;
	}

	/**
	 * @param trigger
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Element getEventType(Trigger trigger) {
		String statement = ""; //$NON-NLS-1$
		Element sl = null;
		if (trigger.isDeleteType()) {
			statement += Messages.ProcessTable_delete;
		}
		else if (trigger.isInsertType()) {
			statement += Messages.ProcessTable_insert;
		}
		else if (trigger.isUpdateType()) {
			statement += Messages.ProcessTable_update;
			Collection<Column> updateColumns = trigger.getTriggerColumn();
			if (!updateColumns.isEmpty()) {
				sl = DitaFactory.createElement(BodyTypes.SL);
				for (Column column : updateColumns) {
					sl.addContent(DitaFactory.createElement(BodyTypes.SLI,
							column.getName()));
				}
			}
		}
		Element dd = DitaFactory.createElement(BodyTypes.DD, statement);
		if (sl != null)
			dd.addContent(sl);
		return dd;
	}

	/**
	 * @param identitySpecifier
	 * @return
	 */
	private String getID(IdentitySpecifier identitySpecifier) {
		if (identitySpecifier == null) {
			return Messages.ProcessTable_false;
		}
		return Messages.ProcessTable_start
				+ identitySpecifier.getStartValue().toString()
				+ Messages.ProcessTable_increment
				+ identitySpecifier.getIncrement().toString();
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
		String id = getPrefix() + persistentTable.getName();
		topicRef.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = Messages.ProcessTable_title + persistentTable.getName();
		if (persistentTable.getDescription() != null) {
			title += " - " + persistentTable.getDescription(); //$NON-NLS-1$
		}
		topic.setTitle(title);
		topic.getBody().addContent(
				DitaFactory.createElement(BodyTypes.P, persistentTable
						.getDescription()));
		List<Comment> comments = persistentTable.getComments();
		for (Comment comment : comments) {
			topic.getBody().addContent(
					DitaFactory.createElement(BodyTypes.P, comment
							.getDescription()));
		}
		addColumns(persistentTable.getColumns(), topic);
		addPK(persistentTable.getPrimaryKey(), topic);
		addFK(persistentTable.getForeignKeys(), topic);
		addUQ(persistentTable.getUniqueConstraints(), topic);
		addCK(persistentTable.getConstraints(), topic);
		addTrigers(persistentTable.getTriggers(), topic);
		addIndex(persistentTable.getIndex(), topic);
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
