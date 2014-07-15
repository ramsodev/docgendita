import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import net.ramso.doc.svg.as400.AS400Diagram;
import net.ramso.doc.svg.as400.AS400Object;

/**
 * 
 */
/**
 * @author ramso
 */
public class test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<AS400Object> objs = new ArrayList<AS400Object>();
		String[] atr = { "*PGM", "*FILE", "*FILE", "*FILE", "*FILE" };
		String[] types = { "RPG", "PF_DTA", "LF", "DSPF", "PRTF" };
		String parent = "";
		for (int i = 0; i < 5; i++) {
			AS400Object obj = new AS400Object();
			obj.setParent(parent);
			obj.setName("OBJ" + i);
			obj.setLib("LIBRERIA");
			obj.setType(types[i]);
			obj.setAttribute(atr[i]);
			objs.add(obj);
			if( i == 0 || i == 3){
				parent = obj.getName();
			}
		}
		AS400Diagram diagram = new  AS400Diagram(objs);
		diagram.run();
		try {
			diagram.save("/home/ramso/temp_files/");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
}
