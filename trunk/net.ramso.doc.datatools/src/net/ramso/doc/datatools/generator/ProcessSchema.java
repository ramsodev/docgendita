package net.ramso.doc.datatools.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.bookmap.Part;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;
import net.ramso.doc.dita.utils.ResourceUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.modelbase.sql.datatypes.UserDefinedType;
import org.eclipse.datatools.modelbase.sql.routines.Procedure;
import org.eclipse.datatools.modelbase.sql.routines.UserDefinedFunction;
import org.eclipse.datatools.modelbase.sql.schema.Comment;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.schema.Sequence;
import org.eclipse.datatools.modelbase.sql.tables.PersistentTable;
import org.eclipse.datatools.modelbase.sql.tables.Table;
import org.eclipse.datatools.modelbase.sql.tables.ViewTable;

public class ProcessSchema {
	private Part	part;
	private Schema	schema;
	private String	path;
	private String	prefix	= "";	//$NON-NLS-1$

	public ProcessSchema(Schema schema, String path) {
		this.schema = schema;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	/**
	 * @param sequences
	 * @param monitor
	 */
	private void addSequences(List<Sequence> sequences, IProgressMonitor monitor)
			throws IOException {
		if (!sequences.isEmpty()) {
			Chapter chapter = new Chapter();
			String id = getPrefix() + schema.getName() + "_sequences_Chapter"; //$NON-NLS-1$
			chapter.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			topic.setID(id);
			String title = Messages.ProcessSchema_sequence + schema.getName();
			topic.setTitle(title);
			topic.appendSection(Messages.ProcessSchema_description, "des"); //$NON-NLS-2$
			@SuppressWarnings("unused")
			Section section = topic.getSection("des"); //$NON-NLS-1$
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(),
					path + File.separator + topicDocument.getFileName());
			for (Sequence sequence : sequences) {
				chapter.appendTopicRef(createSequence(sequence, monitor));
			}
			getPart().addChapter(chapter);
		}
	}

	/**
	 * @param procedures
	 * @param monitor
	 */
	private void addStoreProcedures(List<Procedure> procedures,
			IProgressMonitor monitor) throws IOException {
		if (!procedures.isEmpty()) {
			Chapter chapter = new Chapter();
			String id = getPrefix() + schema.getName() + "_procedures_Chapter"; //$NON-NLS-1$
			chapter.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			topic.setID(id);
			String title = Messages.ProcessSchema_procedure + schema.getName();
			topic.setTitle(title);
			topic.appendSection(Messages.ProcessSchema_description, "des"); //$NON-NLS-2$
			@SuppressWarnings("unused")
			Section section = topic.getSection("des"); //$NON-NLS-1$
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(),
					path + File.separator + topicDocument.getFileName());
			for (Procedure procedure : procedures) {
				chapter.appendTopicRef(createProcedure(procedure, monitor));
			}
			getPart().addChapter(chapter);
		}
	}

	private void addTables(List<Table> tables, IProgressMonitor monitor)
			throws IOException {
		if (!tables.isEmpty()) {
			Chapter chapter = new Chapter();
			String id = getPrefix() + schema.getName() + "_tables_chapter"; //$NON-NLS-1$
			chapter.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			topic.setID(id);
			String title = Messages.ProcessSchema_tables + schema.getName();
			topic.setTitle(title);
			topic.appendSection(Messages.ProcessSchema_description, "des"); //$NON-NLS-2$
			@SuppressWarnings("unused")
			Section section = topic.getSection("des"); //$NON-NLS-1$
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(),
					path + File.separator + topicDocument.getFileName());
			for (Table table : tables) {
				if (table instanceof PersistentTable) {
					chapter.appendTopicRef(createTable((PersistentTable) table,
							monitor));
				}
			}
			getPart().addChapter(chapter);
		}
	}

	/**
	 * @param monitor
	 * @param udFs
	 */
	private void addUDFs(List<UserDefinedFunction> udfs,
			IProgressMonitor monitor) throws IOException {
		if (!udfs.isEmpty()) {
			Chapter chapter = new Chapter();
			String id = getPrefix() + schema.getName() + "_udfs_Chapter"; //$NON-NLS-1$
			chapter.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			topic.setID(id);
			String title = Messages.ProcessSchema_udfs + schema.getName();
			topic.setTitle(title);
			topic.appendSection(Messages.ProcessSchema_description, "des"); //$NON-NLS-2$
			@SuppressWarnings("unused")
			Section section = topic.getSection("des"); //$NON-NLS-1$
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(),
					path + File.separator + topicDocument.getFileName());
			for (UserDefinedFunction udf : udfs) {
				chapter.appendTopicRef(createUDF(udf, monitor));
			}
			getPart().addChapter(chapter);
		}
	}

	/**
	 * @param userDefinedTypes
	 * @param monitor
	 */
	private void addUDTs(List<UserDefinedType> udts, IProgressMonitor monitor)
			throws IOException {
		if (!udts.isEmpty()) {
			Chapter chapter = new Chapter();
			String id = getPrefix() + schema.getName() + "_udfs_Chapter"; //$NON-NLS-1$
			chapter.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			topic.setID(id);
			String title = Messages.ProcessSchema_udts + schema.getName();
			topic.setTitle(title);
			topic.appendSection(Messages.ProcessSchema_description, "des"); //$NON-NLS-2$
			@SuppressWarnings("unused")
			Section section = topic.getSection("des"); //$NON-NLS-1$
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(),
					path + File.separator + topicDocument.getFileName());
			for (UserDefinedType udf : udts) {
				chapter.appendTopicRef(createUDT(udf, monitor));
			}
			getPart().addChapter(chapter);
		}
	}

	/**
	 * @param tables
	 * @param topic
	 * @param monitor
	 * @throws IOException
	 */
	private void addViews(List<Table> tables, IProgressMonitor monitor)
			throws IOException {
		ArrayList<ViewTable> views = new ArrayList<ViewTable>();
		for (Table table : tables) {
			if (table instanceof ViewTable) {
				views.add((ViewTable) table);
			}
		}
		if (!views.isEmpty()) {
			Chapter chapter = new Chapter();
			String id = getPrefix() + schema.getName() + "_views_Chapter"; //$NON-NLS-1$
			chapter.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			topic.setID(id);
			String title = Messages.ProcessSchema_views + schema.getName();
			topic.setTitle(title);
			topic.appendSection(Messages.ProcessSchema_description, "des"); //$NON-NLS-2$
			@SuppressWarnings("unused")
			Section section = topic.getSection("des"); //$NON-NLS-1$
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(),
					path + File.separator + topicDocument.getFileName());
			for (ViewTable view : views) {
				if (view instanceof ViewTable) {
					chapter.appendTopicRef(createView(view, monitor));
				}
			}
			getPart().addChapter(chapter);
		}
	}

	/**
	 * @param procedure
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createProcedure(Procedure procedure,
			IProgressMonitor monitor) throws IOException {
		ProcessProcedure pProcedure = new ProcessProcedure(procedure, path);
		pProcedure.setPrefix(prefix + schema.getName() + "_"); //$NON-NLS-1$
		pProcedure.process(monitor);
		return pProcedure.getTopicRef();
	}

	/**
	 * @param sequence
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createSequence(Sequence sequence, IProgressMonitor monitor)
			throws IOException {
		ProcessSequence pView = new ProcessSequence(sequence, path);
		pView.setPrefix(prefix + schema.getName() + "_"); //$NON-NLS-1$
		pView.process(monitor);
		return pView.getTopicRef();
	}

	/**
	 * @param table
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createTable(PersistentTable table, IProgressMonitor monitor)
			throws IOException {
		ProcessTable pSchema = new ProcessTable(table, path);
		pSchema.setPrefix(prefix + schema.getName() + "_"); //$NON-NLS-1$
		pSchema.process(monitor);
		return pSchema.getTopicRef();
	}

	/**
	 * @param udf
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createUDF(UserDefinedFunction udf, IProgressMonitor monitor)
			throws IOException {
		ProcessUDF pProcedure = new ProcessUDF(udf, path);
		pProcedure.setPrefix(prefix + schema.getName() + "_"); //$NON-NLS-1$
		pProcedure.process(monitor);
		return pProcedure.getTopicRef();
	}

	/**
	 * @param udf
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createUDT(UserDefinedType udf, IProgressMonitor monitor)
			throws IOException {
		ProcessUDT pProcedure = new ProcessUDT(udf, path);
		pProcedure.setPrefix(prefix + schema.getName() + "_"); //$NON-NLS-1$
		pProcedure.process(monitor);
		return pProcedure.getTopicRef();
	}

	/**
	 * @param view
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createView(ViewTable view, IProgressMonitor monitor)
			throws IOException {
		ProcessView pView = new ProcessView(view, path);
		pView.setPrefix(prefix + schema.getName() + "_"); //$NON-NLS-1$
		pView.process(monitor);
		return pView.getTopicRef();
	}

	public Part getPart() {
		return part;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		part = new Part();
		String id = getPrefix() + schema.getName();
		part.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = Messages.ProcessSchema_title + schema.getName();
		if (schema.getLabel() != null) {
			title += " - " + schema.getLabel(); //$NON-NLS-1$
		}
		else if (schema.getDescription() != null) {
			title += " - " + schema.getDescription(); //$NON-NLS-1$
		}
		topic.setTitle(title);
		topic.appendSection(Messages.ProcessSchema_description, "des"); //$NON-NLS-2$
		Section section = topic.getSection("des"); //$NON-NLS-1$
		section.appendP(schema.getDescription());
		List<Comment> comments = schema.getComments();
		for (Comment comment : comments) {
			section.addContent(DitaFactory.createElement(BodyTypes.P, comment
					.getDescription()));
		}
		addTables(schema.getTables(), monitor);
		addViews(schema.getTables(), monitor);
		addSequences(schema.getSequences(), monitor);
		addStoreProcedures(schema.getProcedures(), monitor);
		addUDFs(schema.getUDFs(), monitor);
		addUDTs(schema.getUserDefinedTypes(), monitor);
		ResourceUtils.getInstance().saveDitaFileAsResource(
				topicDocument.getDocumentContent(),
				path + File.separator + topicDocument.getFileName());
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
