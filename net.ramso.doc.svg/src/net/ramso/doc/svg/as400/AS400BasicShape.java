/**
 * 
 */
package net.ramso.doc.svg.as400;

import net.ramso.doc.svg.shapes.BasicShape;

import org.w3c.dom.Document;

/**
 * @author ramso
 */
public abstract class AS400BasicShape extends BasicShape {
	
	private AS400Object obj = null;
	
	
	/**
	 * 
	 */
	public AS400BasicShape() {
		super();
	}
	
	public AS400BasicShape(AS400Object obj, Document doc){
		super(doc, 60, 100);
		setAS400Object(obj);		
	}
	
	

	/**
	 * @return the obj
	 */
	public AS400Object getAS400Object() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setAS400Object(AS400Object obj) {
		this.obj = obj;
	}
	
	/**
	 * create a shape in the svg document
	 * @throws AS400SVGException
	 */
	public abstract void addShape() throws AS400SVGException;

	
}
