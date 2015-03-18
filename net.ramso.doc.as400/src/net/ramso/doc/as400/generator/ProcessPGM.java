/**
 * 
 */
package net.ramso.doc.as400.generator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import net.ramso.doc.as400.Messages;
import net.ramso.doc.as400.data.GetObjUse;
import net.ramso.doc.as400.data.GetObjUseRow;
import net.ramso.doc.as400.data.GetPgmObjRow;
import net.ramso.doc.as400.data.getObjRel;
import net.ramso.doc.as400.data.getObjRelRow;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.ui.wizards.DocumentationWizard;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.attributes.NoteTypeValues;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.body.Dl;
import net.ramso.doc.dita.elements.body.Note;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.map.TopicRef;
import net.ramso.doc.dita.elements.topic.Section;
import net.ramso.doc.dita.elements.topic.Topic;
import net.ramso.doc.dita.utils.ResourceUtils;
import net.ramso.doc.dita.utils.TextUtils;
import net.ramso.doc.svg.as400.AS400Diagram;
import net.ramso.doc.svg.as400.AS400Object;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author ramso
 */
public class ProcessPGM {
	private TopicRef		topicRef;
	private GetPgmObjRow	as400PGM;
	private String			path;
	private String			prefix	= "";	//$NON-NLS-1$
	private String[]		tablas;
	private String			lib;

	public ProcessPGM(GetPgmObjRow pgm, String path) {
		this.as400PGM = pgm;
		try {
			this.lib = pgm.getDSPOBJD_ODLBNM();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		this.path = path;
		prefix = ""; //$NON-NLS-1$
	}

	public Chapter getChapter() {
		Chapter chapter = new Chapter(getTopicRef());
		return chapter;
	}

	/**
	 * @param trigger
	 * @return
	 */
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

	@SuppressWarnings("unchecked")
	public String process(IProgressMonitor monitor) throws IOException {
		topicRef = DitaFactory.createTopicRef();
		try {
			String id = TextUtils.clean(getPrefix() + as400PGM.getDSPOBJD_ODOBNM().trim());
			topicRef.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			
			topic.setID(id);
			String title = Messages.pgm_chap + " "
					+ as400PGM.getDSPOBJD_ODOBNM().trim();
			topic.setTitle(title);
			topic.setShortDescription(as400PGM.getDSPOBJD_ODOBTX());
			addAttributes(as400PGM, topic);
			addDiagram(as400PGM, topic);
			addRelated(as400PGM.getDSPOBJD_ODOBNM().trim(), topic);
			path += File.separator + topicDocument.getFileName();
			// topicDocument.save(path);
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(), path);
		}
		catch (SQLException e) {
		}
		return null;
	}

	/**
	 * @param obj
	 * @param topic
	 */
	private void addDiagram(GetPgmObjRow as400pgm2, Topic topic) {
		ArrayList<AS400Object> objsSVG = new ArrayList<AS400Object>();
		String obj;
		try {
			obj = as400pgm2.getDSPOBJD_ODOBNM().trim();
			AS400Object objSvg = new AS400Object();
			objSvg.setName(((String) as400pgm2.getDSPOBJD_ODOBNM().trim()));
			objSvg.setLib(((String) as400pgm2.getDSPOBJD_ODLBNM().trim()));
			objSvg.setAttribute(((String) as400pgm2.getDSPOBJD_ODOBAT()).trim());
			objSvg.setType(((String) as400pgm2.getDSPOBJD_ODOBTP()).trim());
			objSvg.setParent(obj);
			objsSVG.add(objSvg);
			getObjRel objRel = new getObjRel();
			getObjRelRow[] objs = objRel.read(obj);
			for (getObjRelRow row : objs) {
				objSvg = new AS400Object();
				objSvg.setName(((String) row.getDSPGMREF_WHFNAM()).trim());
				objSvg.setLib(((String) row.getDSPGMREF_WHLNAM()).trim());
				String des = (String) row.getDSPOBJD_ODOBAT();
				if(des == null){
					des=(String) row.getDSPGMREF_WHOTYP();
				}
				objSvg.setAttribute((des).trim());
				objSvg.setType(((String) row.getDSPGMREF_WHOTYP()).trim());
				objSvg.setParent(obj);
				objsSVG.add(objSvg);
			}
		}
		catch (CriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TableOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AS400Diagram diagram = new AS400Diagram(objsSVG);
		diagram.run();
		String pathi = path + File.separator + DocumentationWizard.IMGFOLDER
				+ File.separator + diagram.getFileName();
		try {
			net.ramso.doc.svg.utils.ResourceUtils.getInstance()
					.saveSVGFileAsResource(diagram.getDocumentContent(), pathi);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "." + File.separator + DocumentationWizard.IMGFOLDER
				+ File.separator + diagram.getFileName() + "."
				+ net.ramso.doc.svg.utils.ResourceUtils.SVG_FILE_EXTENSION;
		topic.appendSection(Messages.pgm_dig_tit, "erd"); 
		Section section = topic.getSection("erd"); //$NON-NLS-1$
		section.appendFigure(diagram.getFileName(), Messages.pgm_dig
				+ diagram.getFileName(), url);
	}

	/**
	 * Method addAtr.
	 * 
	 * @param topic
	 * @param getFilesTipoRow
	 * @return Node
	 */
	private void addAttributes(GetPgmObjRow pgm, Topic topic) {
		try {
			topic.appendSection(Messages.pgm_obj_des,
					"pgm_" + pgm.getDSPOBJD_ODOBNM()); //$NON-NLS-1$
			Section section = topic
					.getSection("pgm_" + pgm.getDSPOBJD_ODOBNM()); //$NON-NLS-1$
			Dl dl = section.getDL("pgm_dl_" + pgm.getDSPOBJD_ODOBNM(), true); //$NON-NLS-1$
			String trim = (String) pgm.getDSPOBJD_ODLBNM();
			String des = Messages.pgm_obj_lib;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODOBTP();
			if (trim.trim().compareTo("*MODULE") == 0) {
				trim = "*SRVPGM";
			}
			des = Messages.pgm_obj_type;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODOBAT();
			des = Messages.pgm_obj_atr;
			dl.addItem(des, trim.trim());
			BigDecimal size = (BigDecimal) pgm.getDSPOBJD_ODOBSZ();
			trim = size.toString();
			des = Messages.pgm_obj_size;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODOBTX();
			des = Messages.pgm_obj_txt;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODCDAT();
			des = Messages.pgm_obj_dcr;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODCTIM();
			des = Messages.pgm_obj_tcr;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODOBOW();
			des = Messages.pgm_obj_own;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODSRCF();
			des = Messages.pgm_obj_src_file;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODSRCL();
			des = Messages.pgm_obj_src_lib;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODSRCM();
			des = Messages.pgm_obj_src_mbr;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODCMNM();
			des = Messages.pgm_obj_comp;
			dl.addItem(des, trim.trim());
			trim = (String) pgm.getDSPOBJD_ODCMVR();
			des = Messages.pgm_obj_level;
			dl.addItem(des, trim.trim());
		}
		catch (SQLException e) {
			// TODO: handle exception
		}
	}

	private void addRelated(String pgm, Topic topic) {
		topic.appendSection(Messages.file_use, "use"); //$NON-NLS-1$
		Section section = topic.getSection("use"); //$NON-NLS-1$
		section.appendP(Messages.file_use_des);
		GetObjUse objUse = new GetObjUse();
		Dl dl = null;
		boolean vacio = true;
		try {
			GetObjUseRow[] _ObjUse = objUse.read(pgm);
			if (_ObjUse.length > 0) {
				vacio = false;
				if (dl == null) {
					dl = section.getDL("use_dl_", true);
				}
				for (int i = 0; i < _ObjUse.length; i++) {
					String obj = (String) _ObjUse[i].getDSPPGMREF_WHPNAM();
					String lib = (String) _ObjUse[i].getDSPPGMREF_WHLIB();
					String text = (String) _ObjUse[i].getDSPPGMREF_WHTEXT();
					dl.addItem(lib.trim() + "/" + obj.trim(), text.trim());
				}
			}
		}
		catch (SQLException e) {
			System.out.println("Error de SQL " + e);
		}
		catch (CriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TableOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vacio) {
			Note note = new Note();
			note.creteNote("use_note", "No hay Nada", NoteTypeValues.NOTICE);
			note.setText(Messages.pgm_dig_att);
			section.addContent(note);
		}
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Returns the tablas.
	 * 
	 * @return String[]
	 */
	public String[] getTablas() {
		return tablas;
	}

	/**
	 * Sets the tablas.
	 * 
	 * @param tablas
	 *            The tablas to set
	 */
	public void setTablas(String[] tablas) {
		this.tablas = tablas;
	}
}
