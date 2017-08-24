package net.ramso.doc.diagrams;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import net.ramso.doc.diagrams.as400.AS400Diagram;
import net.ramso.doc.diagrams.as400.AS400Object;

public class Test2 {

	public static void main(String[] args) {
		ArrayList<AS400Object> data = new ArrayList<AS400Object>();
		AS400Object object = new AS400Object();
		object.setName("PARENT0000");
		object.setType("RPGLE");
		object.setAttribute("*SRVPGM");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		object = new AS400Object();
		object.setName("CHILD00001");
		object.setType("PF");
		object.setAttribute("PF-DTA");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		object = new AS400Object();
		object.setName("CHILD00002");
		object.setType("");
		object.setAttribute("DSPF");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		object = new AS400Object();
		object.setName("CHILD00003");
		object.setType("");
		object.setAttribute("PRTF");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		object = new AS400Object();
		object.setName("CHILD00004");
		object.setType("RPGLE");
		object.setAttribute("*SRVPGM");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		object = new AS400Object();
		object.setName("CHILD00005");
		object.setType("PF");
		object.setAttribute("PF-DTA");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		object = new AS400Object();
		object.setName("CHILD00006");
		object.setType("");
		object.setAttribute("DSPF");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		object = new AS400Object();
		object.setName("CHILD00007");
		object.setType("");
		object.setAttribute("PRTF");
		object.setLib("BIBLIOTECA");
		object.setParent("PARENT0000");
		data.add(object);
		AS400Diagram diagram = new AS400Diagram(data);
		diagram.setFileName("diagramaTestCase");
		String path = "/home/jjescudero/temp/dita/";
		diagram.run();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.getContentPane().add(diagram.getGraphComponent());
		frame.pack();
		try {
			diagram.save(path);
			diagram.save(path, "SVG");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
