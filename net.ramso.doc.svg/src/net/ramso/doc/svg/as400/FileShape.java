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
public class FileShape extends AS400BasicShape implements IShape {
	/**
	 * @param obj
	 * @param document
	 * @param pos
	 */
	public FileShape(AS400Object obj, Document document) {
		super(obj,document);
//		setHeight(75);
//		setWidth(102);
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
		g.appendChild(addPath());
		g.appendChild(SVGFactory.createEllipse(getCordinates(),getDocument()));
		g.appendChild(SVGFactory.createText(new String[] {
				getAS400Object().getName(), getAS400Object().getLib(),
				getAS400Object().getAttribute() }, getCordinates(),
				getDocument()));
	}
	
	private Node addPath() {
		String d = "M" + String.valueOf(getCordinates()[0]) + ", " + String.valueOf(getCordinates()[1])
				+ " L" + String.valueOf(getCordinates()[0]) + ", "
				+ String.valueOf(getCordinates()[1] + getCordinates()[3]) + " S"
				+ String.valueOf(getCordinates()[0] + (getCordinates()[2] / 2)) + ", "
				+ String.valueOf(getCordinates()[1] + getCordinates()[3] + (getCordinates()[3] / 4)) + " "
				+ String.valueOf((getCordinates()[0] + getCordinates()[2])) + ", "
				+ String.valueOf(getCordinates()[1] + getCordinates()[3]) + " L"
				+ String.valueOf(getCordinates()[0] + getCordinates()[2]) + ", " + String.valueOf(getCordinates()[1]);
		return SVGFactory.createPath(d, getDocument());
	}
}
