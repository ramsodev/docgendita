/**
 * 
 */
package net.ramso.doc.svg.as400;

import java.util.ArrayList;
import java.util.HashMap;

import net.ramso.doc.svg.layout.IShapeTreeForTreeLayout;
import net.ramso.doc.svg.shapes.IShape;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import org.w3c.dom.Document;

/**
 * @author ramso
 */
public class AS400TreeForTreeLayout implements IShapeTreeForTreeLayout {
	private ArrayList<AS400Object>	objs;
	private Document				document;
	private HashMap<String, IShape> shapes = new HashMap<String, IShape>();

	public AS400TreeForTreeLayout(ArrayList<AS400Object> objs, Document document) {
		this.objs = objs;
		this.document = document;
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.svg.layout.IShapeTreeForTreeLayout#getTree()
	 */
	@Override
	public TreeForTreeLayout<IShape> getTree() {
		DefaultTreeForTreeLayout<IShape> tree = null;
		AS400BasicShape root = null;
		for (AS400Object obj : getObjs()) {
			AS400BasicShape shape;
			if ((obj.getAttribute().compareTo("*PGM") == 0)
					| (obj.getAttribute().compareTo("*SRVPGM") == 0)) {
				shape = new PGMShape(obj, getDocument());
			}
			else if ((obj.getAttribute().compareTo("PF-DTA") == 0)
					| (obj.getAttribute().compareTo("LF") == 0)) {
				shape = new FileShape(obj, getDocument());
			}
			else if (obj.getAttribute().compareTo("DSPF") == 0) {
				shape = new DisplayShape(obj, getDocument());
			}
			else if (obj.getAttribute().compareTo("PRTF") == 0) {
				shape = new ReportShape(obj, getDocument());
			}
			else if (obj.getType().compareTo("*FILE") == 0) {
				shape = new FileShape(obj, getDocument());
			}
			else {
				shape = new PGMShape(obj, getDocument());
			}
			if (tree == null) {
				root = shape;
				tree = new DefaultTreeForTreeLayout<IShape>(shape);
			}
			else {
				tree.addChild(getSpapeByName(obj.getParent()), shape);
			}
			shapes.put(obj.getName(), shape);
		}
		return tree;
	}

	/**
	 * @return the objs
	 */
	private ArrayList<AS400Object> getObjs() {
		return objs;
	}

	/**
	 * @return the document
	 */
	private Document getDocument() {
		return document;
	}
	
	private IShape getSpapeByName(String id){
		return shapes.get(id);
	}

	
}
