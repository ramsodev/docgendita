/**
 * 
 */
package net.ramso.doc.svg.shapes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * @author ramso
 */
public class SVGFactory {
	public static final String	SVGNS		= "http://www.w3.org/2000/svg";
	public static final String	ROOTNAME	= "svg";
	public static final String	PUBLICID	= "-//W3C//DTD SVG 1.0//EN";
	public static final String	SYSTEMID	= "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd";
	public static final int		VERTICAL	= 50;
	public static final int		HORIZONTAL	= 50;

	public static Element createPath(String d, Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "path");
		elemento.setAttributeNS(null, "d", d);
		return elemento;
	}

	public static Node createPathWithStyle(String d, Document doc) {
		Element elemento = createPath(d, doc);
		elemento.setAttributeNS(null, "style",
				"fill:none;stroke:#000000;stroke-width:2");
		return elemento;
	}

	/**
	 * create a ellipse.
	 * 
	 * @param pos
	 *            the cordinates x,y,radio x, radio y
	 * @param doc
	 *            the dom document
	 * @return Node
	 */
	public static Node createEllipse(int[] pos, Document doc) {
		String cal = "";
		Element elemento = doc.createElementNS(SVGNS, "ellipse");
		cal = String.valueOf(pos[0] + (pos[2] / 2));
		elemento.setAttributeNS(null, "cx", cal);
		cal = String.valueOf(pos[1]);
		elemento.setAttributeNS(null, "cy", cal);
		cal = String.valueOf(pos[2] / 2);
		elemento.setAttributeNS(null, "rx", cal);
		cal = String.valueOf(pos[3] / 10);
		elemento.setAttributeNS(null, "ry", cal);
		return elemento;
	}

	/**
	 * Create a text element
	 * 
	 * @param text
	 *            the lines of the text
	 * @param pos
	 *            position of the text x,y,width
	 * @param doc
	 *            de DOM document
	 * @return Node
	 */
	public static Node createText(String[] text, int[] pos, Document doc) {
		int x = 0;
		int y = 0;
		String val = "";
		Element elemento = doc.createElementNS(SVGNS, "text");
		elemento.setAttributeNS(
				null,
				"style",
				"fill:black;stroke:none;font-family:Arial;font-style:normal;font-weight:bold;font-size:10;fill-opacity:1;stroke-opacity:1;stroke-width:1pt;stroke-linejoin:miter;stroke-linecap:butt;text-anchor:middle;writing-mode:lr;");
		// X = Cuadro X + width/2
		// Y = Cuadro Y + 15
		x = pos[0] + (pos[2] / 2);
		y = pos[1] + 15;
		for (int i = 0; i < text.length; i++) {
			elemento.appendChild(createTspan(text[i], String.valueOf(x),
					String.valueOf(y), doc));
			y += 15;
		}
		return elemento;
	}

	/**
	 * Create a Tspan
	 * 
	 * @param texto
	 *            the string to print
	 * @param x
	 * @param y
	 * @param doc
	 * @return
	 */
	public static Node createTspan(String texto, String x, String y,
			Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "tspan");
		elemento.setAttributeNS(null, "x", x);
		elemento.setAttributeNS(null, "y", y);
		Text tex = doc.createTextNode(texto);
		elemento.appendChild(tex);
		return elemento;
	}

	/**
	 * Create a link
	 * 
	 * @param id
	 *            the id to link
	 * @param doc
	 * @return
	 */
	public static Element createLink(String id, Document doc) {
		String enlace = id + ".html";
		Element elemento = doc.createElementNS(SVGNS, "a");
		elemento.setAttributeNS(SVGNS, "xlink:href", enlace);
		return elemento;
	}

	/**
	 * Create rectangle
	 * 
	 * @param pos
	 *            x,y, width, height
	 * @param doc
	 * @return
	 */
	public static Element createRectangle(int[] pos, Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "rect");
		elemento.setAttributeNS(null, "x", String.valueOf(pos[0]));
		elemento.setAttributeNS(null, "y", String.valueOf(pos[1]));
		elemento.setAttributeNS(null, "width", String.valueOf(pos[2]));
		elemento.setAttributeNS(null, "height", String.valueOf(pos[3]));
		return elemento;
	}

	/**
	 * Create a G element
	 * 
	 * @param id
	 * @param doc
	 * @return
	 */
	public static Element createG(String id, Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "g");
		elemento.setAttributeNS(null, "id", id);
		elemento.setAttributeNS(null, "fill", "#FFFFFF");
		elemento.setAttributeNS(null, "stroke", "#000000");
		elemento.setAttributeNS(null, "stroke-width", "2");
		return elemento;
	}

	/**
	 * Method create Defs element.
	 * 
	 * @return Element
	 */
	public static Element addDefs(Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "defs");
		return elemento;
	}

	/**
	 * Create a line between the cordinates
	 * 
	 * @param pos
	 *            x1,y1,x2,y2
	 * @param doc
	 * @return Element
	 */
	public static Element createLine(int[] pos, Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "line");
		elemento.setAttributeNS(null, "x1", String.valueOf(pos[0]));
		elemento.setAttributeNS(null, "y1", String.valueOf(pos[1]));
		elemento.setAttributeNS(null, "x2", String.valueOf(pos[2]));
		elemento.setAttributeNS(null, "y2", String.valueOf(pos[3]));
		return elemento;
	}

	public static Element createConnectorPath(int[] pos, Document doc) {
		int alt = pos[1] + SVGFactory.VERTICAL / 2;
		int len;
		if (pos[0] > pos[2]) {
			len = pos[0] - pos[2];
		}
		else {
			len = pos[2] - pos[0];
		}
		String d = "M " + String.valueOf(pos[0]) + ", "
				+ String.valueOf(pos[1]) + "  " + String.valueOf(pos[0]) + ", "
				+ String.valueOf(alt) + " " + String.valueOf(pos[2]) + ", "
				+ String.valueOf(alt) + " " + String.valueOf(pos[2]) + ", "
				+ String.valueOf(pos[3]);
		Element fCon = (Element) SVGFactory.createPathWithStyle(d, doc);
		return fCon;
	}

	/**
	 * @param svgns
	 * @param string
	 * @param object
	 * @return
	 */
	public static Document createDocument() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		DocumentBuilder builder;
		DOMImplementation impl;
		Document svgDoc = null;
		try {
			impl = factory.newDocumentBuilder().getDOMImplementation();
			DocumentType svgDOCTYPE = impl.createDocumentType(ROOTNAME,
					PUBLICID, SYSTEMID);
			svgDoc = impl.createDocument(SVGNS, ROOTNAME, svgDOCTYPE);
			// svgDoc.getDocumentElement().appendChild(
			// svgDoc.createElementNS(SVGNS, ROOTNAME));
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return svgDoc;
	}
}
