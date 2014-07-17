/**
 * 
 */
package net.ramso.doc.svg.shapes;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	public static final String SVGNS = "http://www.w3.org/2000/svg";
	public static final String ROOTNAME = "svg";
	public static final String PUBLICID = "-//W3C//DTD SVG 1.0//EN";
	public static final String SYSTEMID = "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd";
	public static final int VERTICAL = 50;
	public static final int HORIZONTAL = 50;
	public static final int VERTICALDIR = 0;
	public static final int HOROZONTALDIR = 1;
	private static final String groupStyle = "fill:none;stroke:#b0b0b0;stroke-width:1.08270001;stroke-linecap:butt;stroke-dasharray:none";
	private static final String connectorStyle = "fill:none;stroke:#b0b0b0;stroke-width:1.08270001;stroke-linecap:butt;stroke-dasharray:none";
	private static final String connectorDashStyle = "fill:none;stroke:#b0b0b0;stroke-width:1.08270001;stroke-linecap:butt;stroke-dasharray:6.4961, 3.248";
	private static final String textStyle = "fill:#000000;stroke:none;text-anchor:middle";
	public static final String GROUP = "group_principal";
	public static final int CONNECTOR_ER_ONETOMANY = 0;
	public static final int CONNECTOR_ER_ONETOONE = 1;
	public static final int CONNECTOR_BASIC = 2;

	public static Element createPath(String d, Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "path");
		elemento.setAttributeNS(null, "d", d);
		return elemento;
	}

	public static Node createPathWithStyle(String d, Document doc,
			String connector) {
		Element elemento = createPath(d, doc);
		elemento.setAttributeNS(null, "style", connector);
		// "fill:none;stroke:#000000;");
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
		Element elemento = doc.createElementNS(SVGNS, "text");
		elemento.setAttributeNS(null, "style", textStyle);
		x = pos[0] + (pos[2] / 2);
		y = pos[1] + 15;
		for (int i = 0; i < text.length; i++) {
			elemento.appendChild(createTspan(text[i], String.valueOf(x),
					String.valueOf(y), doc));
			y += 15;
		}
		return elemento;
	}

	public static Element createTextLine(String text, int[] pos, Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "text");
		elemento.setAttributeNS(null, "style", textStyle);
		elemento.appendChild(createTspan(text, String.valueOf(pos[0]),
				String.valueOf(pos[1]), doc));
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
		elemento.setAttributeNS(null, "style", groupStyle);
		// elemento.setAttributeNS(null, "fill", "#FFFFFF");
		// elemento.setAttributeNS(null, "stroke", "#000000");
		// elemento.setAttributeNS(null, "stroke-width", "1");
		return elemento;
	}

	/**
	 * Create a G element
	 * 
	 * @param id
	 * @param doc
	 * @return
	 */
	public static Element createG(String id, Document doc, int x, int y) {
		Element elemento = doc.createElementNS(SVGNS, "g");
		elemento.setAttributeNS(null, "id", id);
		elemento.setAttributeNS(null, "transform",
				"translate(" + String.valueOf(x) + ", " + String.valueOf(y)
						+ ")");
		// "style", groupStyle);
		// elemento.setAttributeNS(null, "fill", "#FFFFFF");
		// elemento.setAttributeNS(null, "stroke", "#000000");
		// elemento.setAttributeNS(null, "stroke-width", "1");
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

	/**
	 * Create a circle
	 * 
	 * @param pos
	 *            x,y,r
	 * @param doc
	 * @return
	 */
	public static Element createCircle(int[] pos, Document doc) {
		Element elemento = doc.createElementNS(SVGNS, "circle");
		elemento.setAttributeNS(null, "cx", String.valueOf(pos[0]));
		elemento.setAttributeNS(null, "cy", String.valueOf(pos[1]));
		elemento.setAttributeNS(null, "r", String.valueOf(pos[2]));
		return elemento;
	}

	public static Element createConnectorPath(String id, int direction,
			int[] pos, Document doc) {
		return createConnectorPath(id, direction, pos, doc, CONNECTOR_BASIC);
	}

	public static Element createConnectorPath(String id, int direction,
			int[] pos, Document doc, int type) {
		int alt = pos[1];
		switch (direction) {
		case VERTICALDIR:
			alt = pos[1] + SVGFactory.VERTICAL / 2;
			break;
		default:
			alt = pos[1];
			break;
		}
		String style = connectorDashStyle;
		if(type == CONNECTOR_BASIC){
			style = connectorStyle;
		}
		Element g = createG(id, doc);
		String d = "M " + String.valueOf(pos[0]) + ", "
				+ String.valueOf(pos[1]) + "  " + String.valueOf(pos[0]) + ", "
				+ String.valueOf(alt) + " " + String.valueOf(pos[2]) + ", "
				+ String.valueOf(alt) + " " + String.valueOf(pos[2]) + ", "
				+ String.valueOf(pos[3]);
		g.appendChild(SVGFactory
				.createPathWithStyle(d, doc, connectorDashStyle));
		switch (type) {
		case CONNECTOR_ER_ONETOMANY:
			g.appendChild(createOneConnector(id, direction, pos, doc));
			g.appendChild(createManyConnector(id, direction, pos, doc));	
			break;
		case CONNECTOR_ER_ONETOONE:
			g.appendChild(createOneConnector(id, direction, pos, doc));
			g.appendChild(createOneConnector(id, direction, pos, doc));	
			break;
		default:
			break;
		}
		
		return g;
	}

	private static Element createOneConnector(String id, int direction,
			int[] pos, Document doc) {
		int[] pos1;
		int[] pos2;
		Element g = createG("one_" + id, doc);
		switch (direction) {
		case VERTICALDIR:
			pos1 = new int[] { pos[0] - 5, pos[1] + 5, pos[0] + 5, pos[1] + 5 };
			pos2 = new int[] { pos[0] - 5, pos[1] + 10, pos[0] + 5, pos[1] + 10 };
			break;
		default:
			pos1 = new int[] { pos[0] + 5, pos[1] - 5, pos[0] + 5, pos[1] + 5 };
			pos2 = new int[] { pos[0] + 10, pos[1] + 5, pos[0] + 10, pos[1] - 5 };
			break;
		}
		g.appendChild(createLine(pos1, doc));
		g.appendChild(createLine(pos2, doc));
		return g;
	}

	private static Element createManyConnector(String id, int direction,
			int[] pos, Document doc) {
		int[] pos1;
		int[] pos2;
		int[] pos3;
		Element g = createG("many_" + id, doc);
		switch (direction) {
		case VERTICALDIR:
			pos1 = new int[] { pos[2], pos[3] - 10, pos[2] + 5, pos[3] };
			pos2 = new int[] { pos[2], pos[3] - 10, pos[2] - 5, pos[3] };
			pos3 = new int[] { pos[2], pos[3] - 12, 2 };
			break;
		default:
			pos1 = new int[] { pos[2] - 10, pos[3], pos[2], pos[3] + 5 };
			pos2 = new int[] { pos[2] - 10, pos[3], pos[2], pos[3] - 5 };
			pos3 = new int[] { pos[2] - 12, pos[3], 2 };
			break;
		}
		g.appendChild(createLine(pos1, doc));
		g.appendChild(createLine(pos2, doc));
		g.appendChild(createCircle(pos3, doc));
		return g;
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
		DOMImplementation impl;
		Document svgDoc = null;
		try {
			impl = factory.newDocumentBuilder().getDOMImplementation();
			DocumentType svgDOCTYPE = impl.createDocumentType(ROOTNAME,
					PUBLICID, SYSTEMID);
			svgDoc = impl.createDocument(SVGNS, ROOTNAME, svgDOCTYPE);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return svgDoc;
	}
}
