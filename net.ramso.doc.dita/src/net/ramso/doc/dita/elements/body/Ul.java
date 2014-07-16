/**
 * 
 */
package net.ramso.doc.dita.elements.body;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BodyTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.TextTypes;

import org.jdom.Element;

/**
 * @author ramso
 */
public class Ul extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4816817046336784770L;



	/**
	 * 
	 */
	public Ul() {
		super(BodyTypes.UL);
	}
	
	public Element getLi(String id){
		return getLi(id, false);
	}

	public Element getLi(String id, boolean create ){
		 Element il = getContent(BodyTypes.LI, id);
		if (create && il == null) {
			il = DitaFactory.createElement(BodyTypes.LI);
			il.setAttribute("id", id);
			addContent(il);
		}
		return il;
	}
	
	public void setBText(Element il, String text){
		Element b = DitaFactory.createElement(TextTypes.B);
		b.setText(text);
		il.addContent(b);
	}

	
}
