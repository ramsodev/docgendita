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
		String[] atr = { "*PGM", "*FILE", "*FILE", "*FILE", "*FILE", "*PGM",
				"*FILE", "*FILE", "*FILE", "*FILE", "*FILE", "*PGM", "*FILE",
				"*FILE", "*FILE", "*FILE" };
		String[] types = { "RPG", "PF_DTA", "LF", "DSPF", "PRTF", "RPG",
				"PF_DTA", "LF", "DSPF", "PRTF", "LF", "DSPF", "PRTF", "LF",
				"DSPF", "PRTF" };
		String parent = "";
		for (int i = 0; i < 16; i++) {
			AS400Object obj = new AS400Object();
			obj.setParent(parent);
			obj.setName("OBJ" + i);
			obj.setLib("LIBRERIA");
			obj.setType(types[i]);
			obj.setAttribute(atr[i]);
			objs.add(obj);
			if (i == 0) {
				parent = obj.getName();
//			} else if (i == 3 | i == 6) {
//				String level1 = obj.getName();
//				for (int j = 3; j < 9; j++) {
//					obj = new AS400Object();
//					obj.setParent(level1);
//					obj.setName("OBJ" + i + "." + j);
//					obj.setLib("LIBRERIA");
//					obj.setType(types[j]);
//					obj.setAttribute(atr[j]);
//					objs.add(obj);
//					if (j == 5) {
//						String level2 = obj.getName();
//						for (int h = 3; h < 9; h++) {
//							obj = new AS400Object();
//							obj.setParent(level2);
//							obj.setName("OBJ" + i + "." + j + "." + h);
//							obj.setLib("LIBRERIA");
//							obj.setType(types[h]);
//							obj.setAttribute(atr[h]);
//							objs.add(obj);
//						}
//					}
//				}
			}

		}

		AS400Diagram diagram = new AS400Diagram(objs);
		diagram.run();
		try {
			diagram.save("/home/jescudero/temp/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
}
