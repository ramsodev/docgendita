/**
 * 
 */
package net.ramso.doc.dita.elements.body;

import net.ramso.doc.dita.attributes.AlignValues;
import net.ramso.doc.dita.attributes.NoteTypeValues;
import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.utils.TextUtils;

import org.jdom.Element;

/**
 * @author ramso
 */
public class Note extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4816817046336784770L;



	/**
	 * 
	 */
	public Note() {
		super(BodyTypes.NOTE);
	}

	/**
	 * @param dt
	 * @param table
	 */
	public void creteNote(String id, String text, NoteTypeValues type) {
//		Element note = DitaFactory.createElement(BodyTypes.NOTE);
		id=TextUtils.clean(id);
		setAttribute("id", id);
		setAttribute("type", type.getLiteral());
		
	}

	
}
