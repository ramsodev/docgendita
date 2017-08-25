package net.ramso.doc.diagrams.jgrapx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mxgraph.shape.mxStencilShape;

public class mxStencilShapeExtended extends mxStencilShape{
	
	public mxStencilShapeExtended(String nodeXml) {
		super(nodeXml);
	}

	public Element getSvg(){
		return (Element) root;
		
	}

}
