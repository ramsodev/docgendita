/**
 * 
 */
package net.ramso.doc.svg.er;

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
public class ERTreeForTreeLayout implements IShapeTreeForTreeLayout {
	private ArrayList<TableData> tables;
	private Document document;
	private HashMap<String, IShape> shapes = new HashMap<String, IShape>();
	private ArrayList<IShape[]> connectors = new ArrayList<IShape[]>();

	public ERTreeForTreeLayout(ArrayList<TableData> tables, Document document) {
		this.tables = tables;
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
		TableShape root = null;
		for (TableData table : getTables()) {
			TableShape shape = (TableShape) getShapeByName(table.getName());
			if (shape == null) {
				shape = new TableShape(table, document);
				shapes.put(table.getName(), shape);
			}
			if (root == null) {
				root = shape;	
			}
		}
		tree = new DefaultTreeForTreeLayout<IShape>(root);
		System.out.println(root.getId());
		for (TableData table : getTables()) {
			IShape parent = getShapeByName(table.getName());
			System.out.println(parent.getId());
			if (tree.hasNode(parent)) {
				System.out.println(" esta el padre");
				for (Object[] relation : table.getRelations()) {
					IShape child = getShapeByName((String) relation[1]);
					addConnector(parent, child);
					if (!tree.hasNode(child)) {
						tree.addChild(parent, child);
					}else{
						System.out.println("Hijo ya esta");
					}
				}
			}
		}
		return tree;
	}

	/**
	 * @return the objs
	 */
	private ArrayList<TableData> getTables() {
		return tables;
	}

	/**
	 * @return the document
	 */
	private Document getDocument() {
		return document;
	}

	private IShape getShapeByName(String id) {
		return shapes.get(id);
	}

	private void addConnector(IShape parent, IShape child) {
		connectors.add(new IShape[] { parent, child });
	}

	/**
	 * @return the connectors
	 */
	public ArrayList<IShape[]> getConnectors() {
		return connectors;
	}

}
