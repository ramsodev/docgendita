/**
 * 
 */
package net.ramso.doc.dita.elements.map;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.MapTypes;
import net.ramso.doc.dita.elements.TopicTypes;


/**
 * @author ramso
 *
 */
public class TopicRef extends BasicMapRef {
	/**
	 * @param type
	 */
	public TopicRef() {
		super(MapTypes.TOPICREF);
//		setLang(Locale.getDefault().getLanguage()+"_"+Locale.getDefault().getCountry());
		setTranslate(true);
		setCollectionType("sequence");
		setType(TopicTypes.TOPIC.getLiteral());
		setToc(true);
	}

	/**
	 * @param b
	 */
	public void setToc(boolean value) {
		if (value) {
			setAttribute("toc", "yes");
		}
		else {
			setAttribute("toc", "no");
		}
	}

	/**
	 * @param string
	 */
	public void setCollectionType(String value) {
		setAttribute("collection-type", value);
	}

	/**
	 * @param b
	 */
	public void setTranslate(boolean value) {
		if (value) {
			setAttribute("translate", "yes");
		}
		else {
			setAttribute("translate", "no");
		}
	}
	
	public void setType(String type){
		setAttribute("type", type);
	}
	
	
}
