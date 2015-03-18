package net.ramso.doc.as400.generator;

import java.io.File;
import java.io.IOException;

import net.ramso.doc.as400.Messages;
import net.ramso.doc.as400.data.GetFilesTipo;
import net.ramso.doc.as400.data.GetFilesTipoRow;
import net.ramso.doc.as400.data.GetPgmObj;
import net.ramso.doc.as400.data.GetPgmObjRow;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.bookmap.Part;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;
import net.ramso.doc.dita.utils.ResourceUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.util.NLS;

public class ProcessLib {
	private Part part;
	private String lib;
	private String path;
	private String prefix = ""; //$NON-NLS-1$
	private String textValuePrtf = "Informes (PRTF)";
	private String textValueDspf = "Pantallas (DSPF)";

	public ProcessLib(String lib, String path) {
		this.lib = lib;
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	private void addTables(String lib, IProgressMonitor monitor)
			throws IOException {
		GetFilesTipo _PF = new GetFilesTipo();
		GetFilesTipoRow[] pfs;
		try {
			pfs = _PF.read("PF", lib.toUpperCase());
			if (pfs.length > 0) {
				Chapter chapter = new Chapter();
				String id = getPrefix() + lib + "_tables_chapter"; //$NON-NLS-1$
				chapter.setHref(id + ".dita"); //$NON-NLS-1$
				TopicDocument topicDocument = new TopicDocument();
				Topic topic = topicDocument.getTopic();
				topic.setID(id);
				String title = Messages.file_chap + lib;
				topic.setTitle(title);
				topic.appendSection(Messages.file_chap_des, "des"); //$NON-NLS-2$
				@SuppressWarnings("unused")
				Section section = topic.getSection("des"); //$NON-NLS-1$
				ResourceUtils.getInstance().saveDitaFileAsResource(
						topicDocument.getDocumentContent(),
						path + File.separator + topicDocument.getFileName());
				for (GetFilesTipoRow pf : pfs) {
					chapter.appendTopicRef(createTable(pf, monitor));
				}
				getPart().addChapter(chapter);
			}
		} catch (CriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TableOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param table
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createTable(GetFilesTipoRow table, IProgressMonitor monitor)
			throws IOException {
		ProcessTable pSchema = new ProcessTable(table, path);
		pSchema.setPrefix(prefix + lib + "_FILE_"); //$NON-NLS-1$
		pSchema.process(monitor);
		return pSchema.getTopicRef();
	}

	private void addPGM(String lib, IProgressMonitor monitor)
			throws IOException {
		GetPgmObj obj = new GetPgmObj();
		GetPgmObjRow[] pgms;
		try {
			pgms = obj.read(lib.toUpperCase());
			if (pgms.length > 0) {
				Chapter chapter = new Chapter();
				String id = getPrefix() + lib + "_pgms_chapter"; //$NON-NLS-1$
				chapter.setHref(id + ".dita"); //$NON-NLS-1$
				TopicDocument topicDocument = new TopicDocument();
				Topic topic = topicDocument.getTopic();
				topic.setID(id);
				String title = Messages.pgm_chap + lib;
				topic.setTitle(title);
				topic.appendSection(Messages.pgm_chap_des, "des"); //$NON-NLS-2$
				@SuppressWarnings("unused")
				Section section = topic.getSection("des"); //$NON-NLS-1$
				ResourceUtils.getInstance().saveDitaFileAsResource(
						topicDocument.getDocumentContent(),
						path + File.separator + topicDocument.getFileName());
				for (GetPgmObjRow pgm : pgms) {
					chapter.appendTopicRef(createPGM(pgm, monitor));
				}
				getPart().addChapter(chapter);
			}
		} catch (CriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TableOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param pgm
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createPGM(GetPgmObjRow pgm, IProgressMonitor monitor)
			throws IOException {
		ProcessPGM pSchema = new ProcessPGM(pgm, path);
		pSchema.setPrefix(prefix + lib + "_PGM_"); //$NON-NLS-1$
		pSchema.process(monitor);
		return pSchema.getTopicRef();
	}

	private void addIO(String type, String lib, String text, IProgressMonitor monitor)
			throws IOException {
		GetFilesTipo _PF = new GetFilesTipo();
		GetFilesTipoRow[] pfs;
		try {
			pfs = _PF.read(type, lib.toUpperCase());
			if (pfs.length > 0) {
				Chapter chapter = new Chapter();
				String id = getPrefix() + lib
						+ "_" + type.toLowerCase() + "_chapter"; //$NON-NLS-1$
				chapter.setHref(id + ".dita"); //$NON-NLS-1$
				TopicDocument topicDocument = new TopicDocument();
				Topic topic = topicDocument.getTopic();
				topic.setID(id);
				
				String title = NLS.bind(Messages.io_chap, text) + lib;
				topic.setTitle(title);
				topic.appendSection(NLS.bind(Messages.file_chap_des,text), "des"); //$NON-NLS-2$
				@SuppressWarnings("unused")
				Section section = topic.getSection("des"); //$NON-NLS-1$
				ResourceUtils.getInstance().saveDitaFileAsResource(
						topicDocument.getDocumentContent(),
						path + File.separator + topicDocument.getFileName());
				for (GetFilesTipoRow pf : pfs) {
					chapter.appendTopicRef(createIO(pf, type, text, monitor));
				}
				getPart().addChapter(chapter);
			}
		} catch (CriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TableOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param table
	 * @param text 
	 * @param type 
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	private TopicRef createIO(GetFilesTipoRow table, String type, String text, IProgressMonitor monitor)
			throws IOException {
		ProcessIO pSchema = new ProcessIO(table,type, text, path);
		pSchema.setPrefix(prefix + lib + "_" + type.trim().toUpperCase()+"_"); //$NON-NLS-1$
		pSchema.process(monitor);
		return pSchema.getTopicRef();
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
		String id = getPrefix() + lib;
		part.setHref(id + ".dita"); //$NON-NLS-1$
		TopicDocument topicDocument = new TopicDocument();
		Topic topic = topicDocument.getTopic();
		topic.setID(id);
		String title = "Biblioteca: " + lib;
		topic.setTitle(title);
		topic.appendSection("Biblioteca", "lib"); //$NON-NLS-2$
		Section section = topic.getSection("lib"); //$NON-NLS-1$
		section.appendP(lib);
		addTables(lib, monitor);
		addPGM(lib, monitor);
		addIO("PRTF", lib, textValuePrtf, monitor);
		addIO("DSPF", lib, textValueDspf, monitor);
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
