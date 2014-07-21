/**
 * 
 */
package net.ramso.doc.dita.elements.topic;

import org.jdom.Element;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.ProgrammingTypes;
import net.ramso.doc.dita.elements.TableTypes;
import net.ramso.doc.dita.elements.TopicTypes;
import net.ramso.doc.dita.elements.body.Dl;
import net.ramso.doc.dita.elements.table.SimpleTable;
import net.ramso.doc.dita.utils.TextUtils;

/**
 * @author ramso
 */
public class Section extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7432447945026467065L;

	/**
	 * 
	 */
	public Section() {
		super(TopicTypes.SECTION);
	}

	public void appendP(String text) {
		addContent(DitaFactory.createElement(BodyTypes.P, text));
	}

	/**
	 * @param string
	 * @param heads
	 * @param sizes
	 */
	public void appendSimpleTable(String id, String[] heads, int[] sizes) {
		id = TextUtils.clean(id);
		SimpleTable table = DitaFactory.createSimpleTable();
		table.setID(id);
		table.setHeaders(heads);
		table.setRelColWidth(sizes);
		addContent(table);
	}

	public Dl getDL(String id) {
		return getDL(id, false);
	}

	/**
	 * @param string
	 */
	public Dl getDL(String id, boolean create) {
		id = TextUtils.clean(id);
		Dl dl = (Dl) getContent(BodyTypes.DL, id);
		if (create && dl == null) {
			dl = DitaFactory.createDl();
			dl.setID(id);
			addContent(dl);
		}
		return dl;
	}

	public SimpleTable getSimpleTable(String id) {
		id = TextUtils.clean(id);
		return (SimpleTable) getContent(TableTypes.SIMPLETABLE, id);
	}

	public void setTitle(String title) {
		addContent(DitaFactory.createElement(TopicTypes.TITLE, title));
	}

	public void appendFigure(String id, String title, String url, boolean scale) {
		id = TextUtils.clean(id);
		Element fig = DitaFactory.createElement(BodyTypes.FIG);
		fig.setAttribute("expanse", "page");
		// fig.setAttribute("scale","100");
		fig.addContent(DitaFactory.createElement(TopicTypes.TITLE, title));
		Element img = DitaFactory.createElement(BodyTypes.IMAGE);
		img.setAttribute("href", url);
		img.setAttribute("align", "center");
		if (scale) {
			img.setAttribute("scalefit", "yes");
			img.setAttribute("width", "16cm");
		}
		fig.addContent(img);
		addContent(fig);
	}
}
