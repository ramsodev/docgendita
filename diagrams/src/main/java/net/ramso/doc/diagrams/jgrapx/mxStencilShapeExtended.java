package net.ramso.doc.diagrams.jgrapx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mxgraph.shape.mxStencilShape;

public class mxStencilShapeExtended extends mxStencilShape{
	
	public mxStencilShapeExtended(String nodeXml) {
		super(nodeXml);
	}

	public mxStencilShapeExtended(Document doc) {
		super(doc);
	}

	public Element getSvg(){
		return (Element) root;
		
	}

}
