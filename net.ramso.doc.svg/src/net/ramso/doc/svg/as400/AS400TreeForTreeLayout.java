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
	private ArrayList<AS400Object> objs;
	private Document document;
	private HashMap<String, IShape> shapes = new HashMap<String, IShape>();

	private static int objForLevel = 5;
	private ArrayList<IShape> rootLevels = new ArrayList<IShape>();

	public AS400TreeForTreeLayout(ArrayList<AS400Object> objs, Document document) {
		this.objs = objs;
		this.document = document;
	}

	/*
	 * (non-Javadoc)
	 * 
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
			} else if ((obj.getAttribute().compareTo("PF-DTA") == 0)
					| (obj.getAttribute().compareTo("LF") == 0)) {
				shape = new FileShape(obj, getDocument());
			} else if (obj.getAttribute().compareTo("DSPF") == 0) {
				shape = new DisplayShape(obj, getDocument());
			} else if (obj.getAttribute().compareTo("PRTF") == 0) {
				shape = new ReportShape(obj, getDocument());
			} else if (obj.getType().compareTo("*FILE") == 0) {
				shape = new FileShape(obj, getDocument());
			} else {
				shape = new PGMShape(obj, getDocument());
			}
			if (rootLevels.isEmpty()) {
				addRoot(shape, null);
			} else {
				addRoot(getSpapeByName(obj.getParent()), shape);
			}
			// if (tree == null) {
			// root = shape;
			// tree = new DefaultTreeForTreeLayout<IShape>(shape);
			// }
			// else {
			// tree.addChild(getSpapeByName(obj.getParent()), shape);
			// }
			shapes.put(obj.getName(), shape);
		}
		IShape temp = null;
		int i = 2;
		int dif = 1;
		for (IShape parent : rootLevels) {
			if (tree == null) {
				temp = parent;
				tree = new DefaultTreeForTreeLayout<IShape>(parent);
			} else {
				try {
					tree.addChild(temp, parent);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage() + parent.getId());
				}
				if (i > objForLevel) {
					//TODO Balancear derecha izquierda
					if (tree.getChildrenList(temp).size() > (objForLevel/2)) {
						temp = tree.getChildrenList(temp).get((objForLevel/2));
					} else {
						temp = parent;
					}
					i = 1;
					objForLevel += dif;
					dif = dif * -1;
				}
				i++;

			}

			for (IShape child : parent.getChilds()) {
				try {
					tree.addChild(temp, child);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage() + child.getId());
				}
				if (i > objForLevel) {
					if (tree.getChildrenList(temp).size() > 2) {
						temp = tree.getChildrenList(temp).get(2);
					} else {
						temp = child;
					}
					i = 1;
					objForLevel += dif;
					dif = dif * -1;
				}
				i++;
			}
		}
		return tree;
	}

	private void addRoot(IShape shape, IShape child) {
		for (IShape parent : rootLevels) {
			if (parent.getId() == shape.getId()) {
				parent.addChild(child);
				return;
			}
		}
		rootLevels.add(shape);
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

	private IShape getSpapeByName(String id) {
		return shapes.get(id);
	}

}
