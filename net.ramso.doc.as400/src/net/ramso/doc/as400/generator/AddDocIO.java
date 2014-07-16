package net.ramso.doc.as400.generator;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.trenti.hdg.data.GetCampos;
import com.trenti.hdg.data.GetCamposRow;
import com.trenti.hdg.data.GetFilesTipo;
import com.trenti.hdg.data.GetFilesTipoRow;
import com.trenti.hdg.data.GetKeyLf;
import com.trenti.hdg.data.GetKeyLfRow;
import com.trenti.hdg.data.GetObjUse;
import com.trenti.hdg.data.GetObjUseRow;
import com.trenti.hdg.data.getObjRel;
import com.trenti.hdg.data.getObjRelRow;
import com.trenti.hdg.utils.CfgApp;
import com.trenti.hdg.utils.UtilAppFiles;

/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AddDocIO {

	private CfgApp cfg;
	private UtilAppFiles utilApp;
	private AddDocBookElements addEle;
	private Document doc;

	private String[] tablas;
	private String tipo;

	/**
	 * Constructor for AddDocPgm.
	 */
	public AddDocIO() {
		addEle = new AddDocBookElements();
		utilApp = new UtilAppFiles();

	}

	public Element procesar() {

		System.out.println("A�ade documentaci�n de Entrada Salida " + getTipo());
		Element element =
			addEle.addChap(
				getTipo().toLowerCase() + "objs",
				cfg.getTexto(getTipo().toLowerCase() + ".chap"),
				getDoc());
		String des = cfg.getTexto(getTipo().toLowerCase() + ".chap.des");
		element.appendChild(addEle.addEpigrafe(des, getDoc()));
		try {
			GetFilesTipo _PF = new GetFilesTipo();
			_PF.execute(
				cfg.getUser(),
				cfg.getPass(),
				cfg.getSys(),
				cfg.getLib(),
				cfg.getJdbcDriver(),
				cfg.getJdbcUrl(),
				getTipo());
			GetFilesTipoRow[] _Pfs = _PF.getRows();
			String objeto;
			String descrip;
			//			int i = 0;
			for (int i = 0; i < _Pfs.length; i++) {
				objeto = (String) _Pfs[i].getDSPFD_ATFILE();
				objeto = objeto.trim();
				descrip = (String) _Pfs[i].getDSPFD_ATTXT();
				descrip = descrip.trim();
				des = objeto + " - " + descrip;
				Element sect1 = addEle.addSect(objeto, des, 1, getDoc());
				sect1.appendChild(addEle.addEpigrafe(descrip, getDoc()));
				//A�adimos los datos del objeto
				sect1.appendChild(addAtr(_Pfs[i]));
				//A�adimos los campos del fichero
				sect1.appendChild(addCam(objeto));
				//A�adimos los Objetos que usan el programa
				sect1.appendChild(addSectu(objeto));

				//A�adimos el ultimo elemento
				element.appendChild(sect1);
				System.out.println(
					"A�adido  el "
						+ ((i * 100) / _Pfs.length)
						+ "% en el Objeto "
						+ (String) _Pfs[i].getDSPFD_ATFILE());
			}

		} catch (Exception e) {
			System.out.println("Excepcion:" + e);
		}
		return element;
	}

	/**
	 * Method addCam.
	 * @param objeto
	 * @return Node
	 */
	private Element addCam(String objeto) {
		Element sect =
			addEle.addSect(
				null,
				cfg.getTexto(getTipo().toLowerCase() + ".reg"),
				2,
				getDoc());
		sect.appendChild(
			addEle.addEpigrafe(
				cfg.getTexto(getTipo().toLowerCase() + ".reg.des"),
				getDoc()));
		GetCampos reg = new GetCampos();
		try {
			reg.execute(
				cfg.getUser(),
				cfg.getPass(),
				cfg.getSys(),
				cfg.getLib(),
				cfg.getJdbcDriver(),
				cfg.getJdbcUrl(),
				objeto);
			GetCamposRow[] regs = reg.getRows();

			String rcd = "";
			String val = "";
			String[] text = new String[7];
			Element tabla = null;
			Element tgroup = null;
			Element tbody = null;
			boolean cargar = false;
			for (int i = 0; i < regs.length; i++) {
				val = (String) regs[i].getDSPFFD_WHNAME();
				val.trim();
				if (rcd.compareTo(val) != 0) {
					rcd = val;
					if (cargar) {
						tgroup.appendChild(tbody);
						tabla.appendChild(tgroup);
						sect.appendChild(tabla);
					} else {
						cargar = true;
					}
					String des =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.tti")
							+ " "
							+ rcd
							+ " - "
							+ (String) regs[i].getDSPFFD_WHTEXT();
					;
					tabla = addEle.addTable(des, getDoc());
					tgroup = addEle.addTgroup(7, 1, getDoc());

					text[0] =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.cab1");
					text[1] =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.cab2");
					text[2] =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.cab3");
					text[3] =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.cab4");
					text[4] =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.cab5");
					text[5] =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.cab6");
					text[6] =
						cfg.getTexto(getTipo().toLowerCase() + ".reg.cab7");
					tgroup.appendChild(addEle.addCabTab(text, getDoc()));
					tbody = addEle.addTbody(getDoc());
				}
				text[0] = (String) regs[i].getDSPFFD_WHFLDI();
				text[1] = (String) regs[i].getDSPFFD_WHFTXT();
				text[3] = (String) regs[i].getDSPFFD_WHFLDT();
				text[4] = (String) regs[i].getDSPFFD_WHCHD1();
				text[5] = (String) regs[i].getDSPFFD_WHCHD2();
				text[6] = (String) regs[i].getDSPFFD_WHCHD3();
				BigDecimal num = (BigDecimal) regs[i].getDSPFFD_WHFLDD();

				if (num.intValue() == 0) {
					num = (BigDecimal) regs[i].getDSPFFD_WHFLDB();
					text[2] = num.toString();
				} else {
					text[2] = num.toString();
					num = (BigDecimal) regs[i].getDSPFFD_WHFLDP();
					text[2] = text[2] + "," + num.toString();
				}
				tbody.appendChild(addEle.addRowTab(text, getDoc()));

			}
			tgroup.appendChild(tbody);
			tabla.appendChild(tgroup);
			sect.appendChild(tabla);
		} catch (SQLException e) {
			System.out.println("Error de SQL " + e);
		}
		return sect;
	}

	/**
	 * Method addAtr.
	 * @param getFilesTipoRow
	 * @return Node
	 */
	private Element addAtr(GetFilesTipoRow _Pfs) {
		String trim, des;
		Element sect =
			addEle.addSect(
				null,
				cfg.getTexto(getTipo().toLowerCase() + ".obj.des"),
				2,
				getDoc());
		try {
			Element itemlist = addEle.addItemList("", getDoc());

			trim = (String) _Pfs.getDSPFD_ATLIB();
			des = cfg.getTexto(getTipo().toLowerCase() + ".obj.lib") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) _Pfs.getDSPFD_ATFILA();
			des = cfg.getTexto(getTipo().toLowerCase() + ".obj.tip") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) _Pfs.getDSPFD_ATTXT();
			des = cfg.getTexto(getTipo().toLowerCase() + ".obj.txt") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) _Pfs.getDSPFD_ATFCDT();
			des = cfg.getTexto(getTipo().toLowerCase() + ".obj.dat") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) _Pfs.getDSPFD_ATFCTM();
			des = cfg.getTexto(getTipo().toLowerCase() + ".obj.tim") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			sect.appendChild(itemlist);
		} catch (SQLException e) {
			System.out.println("Error de SQL " + e);
		}
		return sect;
	}

	private Element addSectu(String objeto) {
		Element sect =
			addEle.addSect(
				null,
				cfg.getTexto(getTipo().toLowerCase() + ".use"),
				2,
				getDoc());

		GetObjUse objUse = new GetObjUse();
		sect.appendChild(
			addEle.addEpigrafe(
				cfg.getTexto(getTipo().toLowerCase() + ".use.des"),
				getDoc()));
		Element itemlist = addEle.addItemList("", getDoc());

		try {
			objUse.execute(
				cfg.getUser(),
				cfg.getPass(),
				cfg.getSys(),
				cfg.getLib(),
				cfg.getJdbcDriver(),
				cfg.getJdbcUrl(),
				objeto);
			GetObjUseRow[] _ObjUse = objUse.getRows();
			if (_ObjUse.length > 0) {

				for (int i = 0; i < _ObjUse.length; i++) {
					String obj = (String) _ObjUse[i].getDSPPGMREF_WHPNAM();
					String lib = (String) _ObjUse[i].getDSPPGMREF_WHLIB();
					String text = (String) _ObjUse[i].getDSPPGMREF_WHTEXT();
					itemlist.appendChild(
						addEle.addItemB(
							obj.trim(),
							lib.trim(),
							text.trim(),
							getDoc()));
				}
				sect.appendChild(itemlist);
			} else {
				sect.appendChild(
					addEle.addAtencion(cfg.getTexto("pgm.use.att"), getDoc()));
			}
		} catch (SQLException e) {
			System.out.println("Error de SQL " + e);
		}

		return sect;
	}

	/**
	 * Returns the cfg.
	 * @return CfgApp
	 */
	public CfgApp getCfg() {
		return cfg;
	}

	/**
	 * Sets the cfg.
	 * @param cfg The cfg to set
	 */
	public void setCfg(CfgApp cfg) {
		this.cfg = cfg;
	}

	/**
	 * Returns the doc.
	 * @return Document
	 */
	public Document getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 * @param doc The doc to set
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	/**
	 * Returns the tipo.
	 * @return String
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 * @param tipo The tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
