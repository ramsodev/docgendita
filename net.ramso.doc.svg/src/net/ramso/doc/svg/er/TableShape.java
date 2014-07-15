/**
 * 
 */
package net.ramso.doc.svg.er;

import net.ramso.doc.svg.shapes.BasicShape;
import net.ramso.doc.svg.shapes.IShape;
import net.ramso.doc.svg.shapes.SVGException;
import net.ramso.doc.svg.shapes.SVGFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ramso
 */
public class TableShape extends BasicShape implements IShape {
	private TableData		table;
	private Document	document;

	/**
	 * @param obj
	 * @param document
	 * @param pos
	 */
	public TableShape(TableData table, Document document) {
		this.table = table;
		this.document = document;
		setWidth(calcWith());
		setHeight(15 + (table.getPrimaryKeys().size() * 15));
		setId(table.getSchema()+"_"+ table.getName());
	}

	/**
	 * @return
	 */
	private int calcWith() {
		int max = table.getName().length();
		for(String pk: table.getPrimaryKeys()){
			int words = pk.length();
			if(words > max) max = words;
		}
		return (max ) * 8;
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.svg.as400.AS400BasicShape#addShape()
	 */
	@Override
	public void addShape() throws SVGException {
		if (getTable() == null) {
			throw new SVGException("Table is Null");
		}
		else if (getDocument() == null) {
			throw new SVGException("Document is Null");
		}
		Element enlace = SVGFactory.createLink(getTable().getName(),
				getDocument());
		getSvgRoot().appendChild(enlace);
		Element g = SVGFactory.createG(getTable().getName(), getDocument());
		enlace.appendChild(g);
		g.appendChild(SVGFactory
				.createRectangle(getCordinates(), getDocument()));
		int[] pos = { (getWidth()/2)+getX(), getY() + 12 };
		g.appendChild(SVGFactory.createTextLine(table.getName(), pos,
				getDocument()));
		int[] lPos = { getX(), getY()+17, getX()+getWidth(), getY()+17 };
		g.appendChild(SVGFactory.createLine(lPos, getDocument()));
		pos[1] += 4;
		for (String pk : getTable().getPrimaryKeys()) {
			pos[1] += 12;
			g.appendChild(SVGFactory.createTextLine(pk, pos, getDocument()));
		}
	}

	/**
	 * @return the table
	 */
	public TableData getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(TableData table) {
		this.table = table;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
}
