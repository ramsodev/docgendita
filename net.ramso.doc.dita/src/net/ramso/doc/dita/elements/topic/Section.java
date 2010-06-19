/**
 * 
 */
package net.ramso.doc.dita.elements.topic;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.TableTypes;
import net.ramso.doc.dita.elements.TopicTypes;
import net.ramso.doc.dita.elements.body.Dl;
import net.ramso.doc.dita.elements.table.SimpleTable;


/**
 * @author ramso
 */
public class Section extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7432447945026467065L;

	/**
	 * 
	 */
	public Section() {
		super(TopicTypes.SECTION);
	}

	public void setTitle(String title) {
		addContent(DitaFactory.createElement(TopicTypes.TITLE, title));
	}

	public void appendP(String text) {
		addContent(DitaFactory.createElement(BodyTypes.P, text));
	}

	/**
	 * @param string
	 */
	public Dl getDL(String id, boolean create) {
		Dl dl = (Dl) getContent(BodyTypes.DL, id);
		if(create && dl == null){
			dl = DitaFactory.createDl();
			dl.setID(id);
			addContent(dl);
		}
		return dl;
	}
	
	public Dl getDL(String id) {
		return getDL(id, false);
		
	}

	/**
	 * @param string
	 * @param heads
	 * @param sizes
	 */
	public void appendSimpleTable(String id, String[] heads, int[] sizes) {
		SimpleTable table = DitaFactory.createSimpleTable();
		table.setID(id);
		table.setHeaders(heads);
		table.setRelColWidth(sizes);
		addContent(table);
		
	}
	public SimpleTable getSimpleTable(String id){
		return (SimpleTable) getContent(TableTypes.SIMPLETABLE,id);
	}
}
