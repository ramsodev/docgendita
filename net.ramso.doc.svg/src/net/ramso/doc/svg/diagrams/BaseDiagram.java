/**
 * 
 */
package net.ramso.doc.svg.diagrams;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.ramso.doc.svg.layout.IShapeTreeForTreeLayout;
import net.ramso.doc.svg.layout.ShapesNodeExtendProvider;
import net.ramso.doc.svg.shapes.IShape;
import net.ramso.doc.svg.shapes.SVGException;
import net.ramso.doc.svg.shapes.SVGFactory;
import net.ramso.doc.svg.utils.TextUtils;

import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ramso
 */
public abstract class BaseDiagram {
	
	protected String			EXT					= ".svg";
	private Document			doc					= null;
	private String				fileName			= null;
	private static double		BORDERTOTAL			= 20;
	private static int			BORDER				= 10;

	public BaseDiagram() {
		setDocument(SVGFactory.createDocument());
	}

	protected void setRootAtr(double width, double height) {
		try {
			getSvgRoot().setAttributeNS(null, "width", String.valueOf(width));
			getSvgRoot().setAttributeNS(null, "height", String.valueOf(height));
			// getSvgRoot().setAttributeNS(null, "x", String.valueOf(-10));
			// getSvgRoot().setAttributeNS(null, "y", String.valueOf(-10));
			getSvgRoot().appendChild(SVGFactory
					.createG(SVGFactory.GROUP, getDocument(), BORDER, BORDER));
		}
		catch (Exception e) {
			System.out.println("Excepcion: " + e);
		}
	}

	/**
	 * @param tree
	 */
	protected void layout(IShapeTreeForTreeLayout tree) {
		DefaultConfiguration<IShape> configuration = new DefaultConfiguration<IShape>(
				SVGFactory.VERTICAL, SVGFactory.HORIZONTAL);
		ShapesNodeExtendProvider nodeExtentProvider = new ShapesNodeExtendProvider();
		TreeLayout<IShape> treeLayout = new TreeLayout<IShape>(tree.getTree(),
				nodeExtentProvider, configuration);
		Dimension size = treeLayout.getBounds().getBounds().getSize();
		setRootAtr(size.getWidth() + BORDERTOTAL, size.getHeight()
				+ BORDERTOTAL);
		for (IShape shape : treeLayout.getNodeBounds().keySet()) {
			try {
				Rectangle2D.Double box = treeLayout.getNodeBounds().get(shape);
				shape.setX(new Double(box.getX()).intValue());
				shape.setY(new Double(box.getY()).intValue());
				shape.addShape();
			}
			catch (SVGException e) {
				e.printStackTrace();
			}
		}
		IShape root = treeLayout.getTree().getRoot();
		addConnectors(treeLayout, root);
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	public void save(String path) throws IOException, TransformerException {
		save(new FileWriter(path + File.separator + getFileName()));
	}

	public StringWriter getDocumentContent() throws IOException,
			TransformerException {
		StringWriter out = new StringWriter();
		save(out);
		return out;
	}

	/**
	 * @param out
	 * @throws IOException
	 * @throws TransformerException
	 */
	public void save(Writer out) throws IOException, TransformerException {
		TransformerFactory tsfi = TransformerFactory.newInstance();
		Transformer tsf = tsfi.newTransformer();
		tsf.setOutputProperty(OutputKeys.INDENT, "yes");
		tsf.transform(new DOMSource(getSvgRoot()), new StreamResult(out));
	}

	public void writeDocument(Element element, String file) {
		try {
			FileOutputStream fileXML = new FileOutputStream(file);
			fileXML.close();
		}
		catch (Exception e) {
			System.out.println("Error escritura fichero XML " + file
					+ " de tipo " + e);
		}
	}

	/**
	 * @return the doc
	 */
	public Document getDocument() {
		return doc;
	}

	/**
	 * @param doc
	 *            the doc to set
	 */
	public void setDocument(Document doc) {
		this.doc = doc;
	}

	public Element getSvgRoot() {
		return getDocument().getDocumentElement();
	}
	public Element getGroup() {
		return (Element) getDocument().getDocumentElement().getFirstChild();
	}
	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = TextUtils.clean(fileName);
	}

	/**
	 * Create the connectors between the shapes
	 * 
	 * @param treeLayout
	 * @param parent
	 */
	public abstract void addConnectors(TreeLayout<IShape> treeLayout,
			IShape parent);
}
