package net.ramso.doc.diagrams.as400;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import com.mxgraph.view.mxGraph;

public class AS400DiagramTestCase {

	private AS400Diagram diagram;
	private String path;

	@Before
	public void setUp() throws Exception {
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
		diagram = new AS400Diagram(data);
		diagram.setFileName("diagramaTestCase");
		path = "temp/";
	}

	@Test
	public void testRun() {
		try {
			diagram.run();
//			diagram.save(path);
			diagram.save(path, "SVG");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Fallo con excepcion: " + e.getMessage());
		}
	}

}
