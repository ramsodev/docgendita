/**
 * 
 */
package net.ramso.doc.svg.shapes;

import org.w3c.dom.Document;

/**
 * Common option for a shape
 * 
 * @author ramso
 */
public interface IShape {
	/**
	 * Get the parent document of the dom tree
	 * 
	 * @return the dom document
	 */
	Document getDocument();

	/**
	 * set the parent dom Document
	 * 
	 * @param doc
	 *            the dom document
	 */
	void setDocument(Document doc);

	/**
	 * @return the height
	 */
	int getHeight();

	void setHeight(int height);

	/**
	 * @return the width
	 */
	int getWidth();

	void setWidth(int width);

	/**
	 * @return the x
	 */
	int getX();

	void setX(int x);

	/**
	 * @return the y
	 */
	int getY();

	void setY(int y);
	
	/**
	 * @return
	 */
	String getId();

	/**
	 * create a shape in the svg document
	 * 
	 * @throws SVGException
	 */
	void addShape() throws SVGException;
	
}
