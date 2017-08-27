package net.ramso.doc.diagrams.db;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ERDiagramTest {

	private ERDiagram diagram;
	private String path;

	@Before
	public void setUp() throws Exception {
		ArrayList<TableData> objs = new ArrayList<TableData>();
		TableData t = new TableData();
		t.setName("parent");
		t.setSchema("pruebas");
		t.addPrimaryKey("pk01");
		t.addRelation(TableData.ONETOMANY, "child");
		objs.add(t);
		t = new TableData();
		t.setName("child");
		t.setSchema("pruebas");
		t.addPrimaryKey("pk01");
		t.addPrimaryKey("pk02");
		objs.add(t);
		diagram = new ERDiagram(objs);
		diagram.setFileName(this.getClass().getSimpleName());
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
