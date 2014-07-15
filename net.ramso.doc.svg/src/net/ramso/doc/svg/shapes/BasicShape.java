/**
 * 
 */
package net.ramso.doc.svg.shapes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ramso
 */
public abstract class BasicShape implements IShape {
	
	private Document	doc	= null;
	public int height = 0;
	public  int width = 0;
	public  int x = 0;
	public  int y = 0;
	private String id;
	
	
	/**
	 * 
	 */
	public BasicShape() {
		super();
	}
	
	public BasicShape (Document doc, int height, int width){
		super();
		this.height = height;
		this.width = width;
		setDocument(doc);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.svg.shapes.IShape#getDocument()
	 */
	public Document getDocument() {
		return doc;
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.svg.shapes.IShape#setDocument(org.w3c.dom.Document)
	 */
	public void setDocument(Document doc) {
		this.doc = doc;
	}
	
	public Element getSvgRoot(){
//		return getDocument().getDocumentElement();
		
		return (Element) getDocument().getDocumentElement().getFirstChild();
	}

	
	
	/**
	 * create a shape in the svg document
	 * @throws SVGException
	 */
	public abstract void addShape() throws SVGException;

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	public int[] getCordinates(){
		return new int[] {getX(),getY(),getWidth(),getHeight()};
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
}
