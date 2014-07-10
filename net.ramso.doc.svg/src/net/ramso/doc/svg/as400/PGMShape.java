/**
 * 
 */
package net.ramso.doc.svg.as400;

import net.ramso.doc.svg.shapes.IShape;
import net.ramso.doc.svg.shapes.SVGFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ramso
 */
public class PGMShape extends AS400BasicShape implements IShape {
	

	/**
	 * @param obj
	 * @param document
	 * @param pos
	 */
	public PGMShape(AS400Object obj, Document document) {
		super(obj,document);
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
		g.appendChild(SVGFactory
				.createRectangle(getCordinates(), getDocument()));
		g.appendChild(SVGFactory.createText(new String[] {
				getAS400Object().getName(), getAS400Object().getLib(),
				getAS400Object().getAttribute() + "/" + getAS400Object().getType() }, getCordinates(),
				getDocument()));
	}
}
