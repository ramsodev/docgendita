package net.ramso.doc.as400.generator;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.trenti.hdg.data.GetObjUse;
import com.trenti.hdg.data.GetObjUseRow;
import com.trenti.hdg.data.GetPgmObj;
import com.trenti.hdg.data.GetPgmObjRow;
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
public class AddDocPgm {

	private CfgApp cfg;
	private UtilAppFiles utilApp;
	private AddDocBookElements addEle;
	private Document doc;

	/**
	 * Constructor for AddDocPgm.
	 */
	public AddDocPgm() {
		addEle = new AddDocBookElements();
		utilApp = new UtilAppFiles();

	}

	public Element procesar() {

		System.out.println("A�ade documentaci�n de programas");
		Element element =
			addEle.addChap("pgmiseries", cfg.getTexto("pgm.chap"), getDoc());
		String des = cfg.getTexto("pgm.chap.des");
		element.appendChild(addEle.addEpigrafe(des, getDoc()));
		try {
			GetPgmObj ObjP = new GetPgmObj();
			ObjP.execute(
				cfg.getUser(),
				cfg.getPass(),
				cfg.getSys(),
				cfg.getLib(),
				cfg.getJdbcDriver(),
				cfg.getJdbcUrl());
			GetPgmObjRow[] ObjsP = ObjP.getRows();
			String objeto;
			String descrip;
			String trim;
			for (int i = 0; i < ObjsP.length; i++) {
				objeto = (String) ObjsP[i].getDSPOBJD_ODOBNM();
				objeto = objeto.trim();
				descrip = (String) ObjsP[i].getDSPOBJD_ODOBTX();
				descrip = descrip.trim();
				des = objeto + " - " + descrip;
				Element sect1 = addEle.addSect(objeto, des, 1, getDoc());
				sect1.appendChild(addEle.addEpigrafe(descrip, getDoc()));

				//A�adimos los datos del objeto
				sect1.appendChild(addSectd(ObjsP[i]));

				//A�adimos los Objetos que usan el programa
				sect1.appendChild(addSectu(objeto));

				//A�adimos el diagrama de flujo en una secci�n de nivel 2					

				sect1.appendChild(addSectf(objeto));

				//A�adimos el ultimo elemento
				element.appendChild(sect1);
				System.out.println(
					"A�adido  el "
						+ ((i * 100) / ObjsP.length)
						+ "% en el Objeto "
						+ (String) ObjsP[i].getDSPOBJD_ODOBNM());

			}

		} catch (Exception e) {
			System.out.println("Excepcion:" + e);
		}
		return element;
	}

	/**
	 * Method addSectu.
	 * @param objeto
	 * @return Node
	 */
	private Element addSectu(String objeto) {
		Element sect =
			addEle.addSect(null, cfg.getTexto("pgm.use"), 2, getDoc());

		GetObjUse objUse = new GetObjUse();
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
				sect.appendChild(
					addEle.addEpigrafe(cfg.getTexto("pgm.use.des"), getDoc()));
				Element itemlist = addEle.addItemList("", getDoc());
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
	 * Method addSectf.
	 * @return Node
	 */
	private Element addSectf(String objeto) {

		Element sectf =
			addEle.addSect(null, cfg.getTexto("pgm.dig"), 2, getDoc());
		getObjRel objRel = new getObjRel();
		try {
			objRel.execute(
				cfg.getUser(),
				cfg.getPass(),
				cfg.getSys(),
				cfg.getLib(),
				cfg.getJdbcDriver(),
				cfg.getJdbcUrl(),
				objeto);
			getObjRelRow[] objsRel = objRel.getRows();
			
			if (objsRel.length > 0) {
				sectf.appendChild(
					addEle.addEpigrafe(cfg.getTexto("pgm.dig.des"), getDoc()));

				Element itemlist = addEle.addItemList("", getDoc());
				String obj, lib, text;
				for (int i = 0; i < objsRel.length; i++) {

					obj = (String) objsRel[i].getDSPGMREF_WHFNAM();
					lib = (String) objsRel[i].getDSPGMREF_WHLNAM();
					text = (String) objsRel[i].getDSPOBJD_ODOBAT();
					if(obj == null){obj = "";}
					if(lib == null){lib = "";}
					if(text == null){text = "";}
		
					itemlist.appendChild(
						addEle.addItemB(
							obj.trim(),
							lib.trim(),
							text.trim(),
							getDoc()));
				}

				sectf.appendChild(itemlist);
				String img = cfg.getImgDirRel() + "/" + objeto + ".svg";
				String type = "SVG";
				String des = cfg.getTexto("pgm.dig.tit") + " " + objeto;
				String[] size = getSize(objeto);
				sectf.appendChild(
					addEle.addFigura(
						des,
						img,
						type,
						size[0],
						size[1],
						getDoc()));
			} else {
				sectf.appendChild(
					addEle.addAtencion(cfg.getTexto("pgm.dig.att"), getDoc()));
			}
		} catch (SQLException e) {
			System.out.println("Error de SQL " + e);
		}
		return sectf;
	}

	/**
	 * Method addSectd.
	 * @return Node
	 */
	private Element addSectd(GetPgmObjRow objP) {
		String des, trim;
		Element sectd =
			addEle.addSect(null, cfg.getTexto("pgm.obj.des"), 2, getDoc());
		try {

			Element itemlist = addEle.addItemList("", getDoc());

			trim = (String) objP.getDSPOBJD_ODLBNM();

			des = cfg.getTexto("pgm.obj.lib") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODOBTP();
			if (trim.trim().compareTo("*MODULE") == 0) {
				trim = "*SRVPGM";
			}
			des = cfg.getTexto("pgm.obj.type") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODOBAT();
			des = cfg.getTexto("pgm.obj.atr") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			BigDecimal size = (BigDecimal) objP.getDSPOBJD_ODOBSZ();
			trim = size.toString();
			des = cfg.getTexto("pgm.obj.size") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODOBTX();
			des = cfg.getTexto("pgm.obj.txt") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODCDAT();
			des = cfg.getTexto("pgm.obj.dcr") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODCTIM();
			des = cfg.getTexto("pgm.obj.tcr") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODOBOW();
			des = cfg.getTexto("pgm.obj.own") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODSRCF();
			des = cfg.getTexto("pgm.obj.src.file") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODSRCL();
			des = cfg.getTexto("pgm.obj.src.lib") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODSRCM();
			des = cfg.getTexto("pgm.obj.src.mbr") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODCMNM();
			des = cfg.getTexto("pgm.obj.comp") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			trim = (String) objP.getDSPOBJD_ODCMVR();
			des = cfg.getTexto("pgm.obj.level") + " ";
			itemlist.appendChild(addEle.addItem(des, trim.trim(), getDoc()));

			sectd.appendChild(itemlist);
		} catch (SQLException e) {
			System.out.println("Error de SQL " + e);
		}
		return sectd;
	}

	/**
	 * Method getSize.
	 * @param objeto
	 * @return String
	 */
	private String[] getSize(String objeto) {
		String file = cfg.getImgDir() + objeto + ".svg";
		String[] size = new String[2];

		Element svg = utilApp.readDoc(file).getDocumentElement();
		size[0] = svg.getAttribute("width");
		size[1] = svg.getAttribute("height");
		return size;
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

}
