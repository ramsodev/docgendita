/**
 * 
 */
package net.ramso.doc.as400.generator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import net.ramso.doc.as400.Messages;
import net.ramso.doc.as400.data.GetCampos;
import net.ramso.doc.as400.data.GetCamposRow;
import net.ramso.doc.as400.data.GetFilesTipoRow;
import net.ramso.doc.as400.data.GetObjUse;
import net.ramso.doc.as400.data.GetObjUseRow;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.dita.Documents.TopicDocument;
import net.ramso.doc.dita.attributes.AlignValues;
import net.ramso.doc.dita.attributes.FrameValues;
import net.ramso.doc.dita.attributes.NoteTypeValues;
import net.ramso.doc.dita.attributes.ScaleValues;
import net.ramso.doc.dita.attributes.VerticalAlignValues;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.body.Dl;
import net.ramso.doc.dita.elements.body.Note;
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
import net.ramso.doc.dita.utils.ResourceUtils;
import net.ramso.doc.dita.utils.TextUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.util.NLS;
import org.jdom.Element;

/**
 * @author ramso
 */
public class ProcessIO {
	private TopicRef topicRef;
	private GetFilesTipoRow as400File;
	private String path;
	private String prefix = ""; //$NON-NLS-1$
	private String[] tablas;
	private String lib;
	private String text;
	private String type;

	public ProcessIO(GetFilesTipoRow file, String type, String text, String path) {
		this.as400File = file;
		try {
			this.lib = file.getDSPFD_ATLIB().trim();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.path = path;
		this.text = text;
		this.type = type;
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
			String id = TextUtils.clean(TextUtils.clean(getPrefix()
					+ as400File.getDSPFD_ATFILE().trim()));
			topicRef.setHref(id + ".dita"); //$NON-NLS-1$
			TopicDocument topicDocument = new TopicDocument();
			Topic topic = topicDocument.getTopic();
			topic.setID(id);
			String title = text + " " + as400File.getDSPFD_ATFILE();
			topic.setTitle(title);
			topic.setShortDescription(as400File.getDSPFD_ATTXT());

			addAttributes(as400File, topic);
			addColumns(as400File.getDSPFD_ATFILE(), topic);
			addRelated(as400File.getDSPFD_ATFILE(), topic);
			path += File.separator + topicDocument.getFileName();
			// topicDocument.save(path);
			ResourceUtils.getInstance().saveDitaFileAsResource(
					topicDocument.getDocumentContent(), path);
		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Method addAtr.
	 * 
	 * @param topic
	 * @param getFilesTipoRow
	 * @return Node
	 */
	private void addAttributes(GetFilesTipoRow _Pfs, Topic topic) {
		try {
			topic.appendSection(NLS.bind(Messages.io_obj_des, text),
					"io_" + _Pfs.getDSPFD_ATFILE()); //$NON-NLS-1$
			Section section = topic.getSection("io_" + _Pfs.getDSPFD_ATFILE()); //$NON-NLS-1$
			// if (_Pfs.getDSPFD_ATTXT() != null) {
			// section.appendP(_Pfs.getDSPFD_ATTXT());
			// }
			Dl dl = section.getDL("io_dl_" + _Pfs.getDSPFD_ATFILE(), true); //$NON-NLS-1$
			String trim = _Pfs.getDSPFD_ATLIB();
			String des = NLS.bind(Messages.io_obj_lib, text) + " ";
			dl.addItem(des, trim.trim());
			trim = (String) _Pfs.getDSPFD_ATFILA();
			des = NLS.bind(Messages.io_obj_tip, text) + " ";
			dl.addItem(des, trim.trim());
			trim = (String) _Pfs.getDSPFD_ATTXT();
			des = NLS.bind(Messages.io_obj_txt, text) + " ";
			dl.addItem(des, trim.trim());
			// TODO:Formatear la fecha
			trim = (String) _Pfs.getDSPFD_ATFCDT();
			des = NLS.bind(Messages.io_obj_dat, text) + " ";
			dl.addItem(des, trim.trim());
			// TODO:Formatear la hora
			trim = (String) _Pfs.getDSPFD_ATFCTM();
			des = NLS.bind(Messages.io_obj_tim, text) + " ";
			dl.addItem(des, trim.trim());
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	private Element addColumns(String objeto, Topic topic) {
		try {
			GetCampos reg = new GetCampos();
			GetCamposRow[] regs = (GetCamposRow[]) reg.read(objeto, lib);
			String des = Messages.io_reg_tti + " " + objeto.trim();
			String[] heads = new String[6];
			heads[0] = Messages.io_reg_cab1.trim();
			// heads[1] = Messages.io_reg_cab2;
			heads[1] = Messages.io_reg_cab3;
			heads[2] = Messages.io_reg_cab4;
			heads[3] = Messages.io_reg_cab5;
			heads[4] = Messages.io_reg_cab6;
			heads[5] = Messages.io_reg_cab7;
			String[] sizes = { "2*", "1*", "1*", "2*", "2*", "2*" };
			AlignValues[] aligns = { AlignValues.LEFT, AlignValues.RIGHT,
					AlignValues.CENTER, AlignValues.CENTER, AlignValues.CENTER,
					AlignValues.CENTER };
			Table table = new Table();
			table.setFrame(FrameValues.ALL);
			table.setColSep(true);
			table.setRowSep(true);
			table.setPGWide(true);
			table.setScale(ScaleValues._80);
			table.setID("table_columns"); //$NON-NLS-1$
			table.setTitle(des);
			table.setTGroup(new TGroup());
			table.getTGroup().setCols(heads.length);
			for (int i = 0; i < heads.length; i++) {
				ColSpec colSpec = new ColSpec();
				colSpec.setAlign(aligns[i]);
				colSpec.setColName(heads[i].replace(" ", "_"));
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
			for (int i = 0; i < regs.length; i++) {
				String[] values = new String[6];
				values[0] = (String) regs[i].getDSPFFD_WHFLDI();
				// heads[1] = (String) regs[i].getDSPFFD_WHFTXT();
				values[2] = (String) regs[i].getDSPFFD_WHFLDT();
				values[3] = (String) regs[i].getDSPFFD_WHCHD1();
				values[4] = (String) regs[i].getDSPFFD_WHCHD2();
				values[5] = (String) regs[i].getDSPFFD_WHCHD3();
				BigDecimal num = (BigDecimal) regs[i].getDSPFFD_WHFLDD();
				if (num.intValue() == 0) {
					num = regs[i].getDSPFFD_WHFLDB();
					values[1] = num.toString();
				} else {
					values[1] = num.toString();
					num = regs[i].getDSPFFD_WHFLDP();
					values[1] = values[1] + "," + num.toString();
				}
				row = new Row();
				row.setVAlign(VerticalAlignValues.MIDDLE);
				Entry entry = new Entry();
				entry.setMoreRows(1);
				entry.setText(values[0]);
				row.appendEntry(entry);
				entry = new Entry();
				entry.setText(values[1]);
				row.appendEntry(entry);
				entry = new Entry();
				entry.setText(values[2]);
				row.appendEntry(entry);
				entry = new Entry();
				entry.setText(values[3]);
				row.appendEntry(entry);
				entry = new Entry();
				entry.setText(values[4]);
				row.appendEntry(entry);
				entry = new Entry();
				entry.setText(values[5]);
				row.appendEntry(entry);
				table.getTGroup().getTBody().appendRow(row);
				row = new Row();
				entry = new Entry();
				des = regs[i].getDSPFFD_WHFTXT();
				entry.setText(des);
				entry.setAlign(AlignValues.LEFT);
				entry.setColName("DES"); //$NON-NLS-1$
				entry.setNamest(heads[1].replace(" ", "_"));
				entry.setNameEnd(heads[5].replace(" ", "_"));
				row.appendEntry(entry);
				table.getTGroup().getTBody().appendRow(row);
			}
			topic.getBody().addContent(table);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TableOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return topic;
	}

	private void addRelated(String objeto, Topic topic) {
		topic.appendSection(NLS.bind(Messages.io_use, text), "use"); //$NON-NLS-1$
		Section section = topic.getSection("use"); //$NON-NLS-1$
		section.appendP(NLS.bind(Messages.io_use_des, text));
		GetObjUse objUse = new GetObjUse();
		Dl dl = null;
		boolean vacio = true;

		try {
			if (objeto != null) {
				GetObjUseRow[] _ObjUse = objUse.read(objeto);
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
		} catch (SQLException e) {
			System.out.println("Error de SQL " + e);
		} catch (CriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TableOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (vacio) {
			Note note = new Note();
			note.creteNote("use_note", "No hay Nada", NoteTypeValues.NOTICE);
			note.setText(NLS.bind(Messages.io_use_att, text));
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
