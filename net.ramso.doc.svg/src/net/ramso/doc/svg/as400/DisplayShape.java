/**
 * 
 */
package net.ramso.doc.svg.as400;

import net.ramso.doc.svg.shapes.IShape;
import net.ramso.doc.svg.shapes.SVGFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author ramso
 */
public class DisplayShape extends AS400BasicShape implements IShape {
	/**
	 * @param obj
	 * @param document
	 * @param pos
	 */
	public DisplayShape(AS400Object obj, Document document) {
		super(obj,document);
//		setWidth(110);
//		setHeight(80);
	}
	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.svg.as400.AS400BasicShape#addShape()
	 */
	@Override
	public void addShape() throws AS400SVGException {
		if (getAS400Object() == null) {
			throw new AS400SVGException("AS400Object is Null");
		}
		else if (getDocument() == null) {
			throw new AS400SVGException("Document is Null");
		}
		Element enlace = SVGFactory.createLink(getAS400Object().getName(),
				getDocument());
		getSvgRoot().appendChild(enlace);
		Element g = SVGFactory.createG(getAS400Object().getName(),
				getDocument());
		enlace.appendChild(g);
		g.appendChild(addPath1());
		g.appendChild(addPath2());
		g.appendChild(addPath3());
		getCordinates()[0] += 10;
		g.appendChild(SVGFactory.createText(new String[] {
				getAS400Object().getName(), getAS400Object().getLib(),
				getAS400Object().getAttribute() }, getCordinates(),
				getDocument()));
	}

	
	private Node addPath1() {		
		String d = "M" + String.valueOf(getCordinates()[0] + getCordinates()[2]) + ", "
				+ String.valueOf(getCordinates()[1]) + " S"
				+ String.valueOf(getCordinates()[0] + getCordinates()[2] + (getCordinates()[2] / 8)) + ", "
				+ String.valueOf(getCordinates()[1] + (getCordinates()[3] / 2)) + " "
				+ String.valueOf(getCordinates()[0] + getCordinates()[2]) + ", "
				+ String.valueOf(getCordinates()[1] + getCordinates()[3]);
		return SVGFactory.createPath(d, getDocument());
	}

	private Node addPath2() {		
		String d = "M" + String.valueOf(getCordinates()[0] + getCordinates()[2]) + ", "
				+ String.valueOf(getCordinates()[1] + getCordinates()[3]) + " S"
				+ String.valueOf(getCordinates()[0] + (getCordinates()[2] / 2)) + ", "
				+ String.valueOf(getCordinates()[1] + getCordinates()[3] + (getCordinates()[3] / 2)) + " "
				+ String.valueOf(getCordinates()[0]) + ", "
				+ String.valueOf(getCordinates()[1] + (getCordinates()[3] / 2));
		return SVGFactory.createPath(d, getDocument());
	}

	private Node addPath3() {		
		String d = "M" + String.valueOf(getCordinates()[0] + getCordinates()[2]) + ", "
				+ String.valueOf(getCordinates()[1]) + " S"
				+ String.valueOf(getCordinates()[0] + (getCordinates()[2] / 2)) + ", "
				+ String.valueOf(getCordinates()[1] - (getCordinates()[3] / 2)) + " "
				+ String.valueOf(getCordinates()[0]) + ", "
				+ String.valueOf(getCordinates()[1] + (getCordinates()[3] / 2));
		return SVGFactory.createPath(d, getDocument());
	}
}
