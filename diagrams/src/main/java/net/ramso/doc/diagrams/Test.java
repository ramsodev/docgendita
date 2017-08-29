package net.ramso.doc.diagrams;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;

import net.ramso.doc.diagrams.db.ERDiagram;
import net.ramso.doc.diagrams.db.TableData;

public class Test extends ERDiagram {

	

	public Test(ArrayList<TableData> objs) {
		super(objs);
		// TODO Auto-generated constructor stub
	}

	private static ERDiagram test;

	public static void main(String[] args) {
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
		test = new ERDiagram(objs);
		test.run();
		try {
			test.save("/home/jjescudero/temp/dita/", "SVG");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame frame = new JFrame();
		frame.getContentPane().add(test.getGraphComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);

	}



}
