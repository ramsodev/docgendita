package net.ramso.doc.diagrams.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;

import net.ramso.doc.diagrams.jgrapx.mxStencilShapeExtended;
import net.ramso.doc.diagrams.jgrapx.mxSvgCanvasExtended;

public class DiagramTools {

	private static mxStylesheet stylesheet;
	private static int repet = 0;

	public static mxStylesheet loadStyleshet() {
		if (stylesheet == null) {
			DiagramTools dt = new DiagramTools();
			String[] shapes = dt.loadShapes();

			stylesheet = new mxStylesheet();
			// configure "figures" aka "vertex"
			{
				Map<String, Object> style = stylesheet.getDefaultVertexStyle();
				style.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
				style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
				style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
				style.put(mxConstants.STYLE_AUTOSIZE, "1");
			}
			// configure "lines" aka "edges"
			{
				Map<String, Object> style = stylesheet.getDefaultEdgeStyle();
				style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
				style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
				style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL);
			}
			Map<String, Map<String, Object>> styles = stylesheet.getStyles();
			for (String sh : shapes) {
				if (sh != null) {
					Map<String, Object> style = new HashMap<String, Object>();
					style.put(mxConstants.STYLE_SHAPE, sh);
					styles.put(sh, style);
				}
			}
			stylesheet.setStyles(styles);
		}
		return stylesheet;
	}

	private String[] loadShapes() {
		int i = 0;
		String[] shapes = { DiagramConstants.SHAPE_DISK, DiagramConstants.SHAPE_DISPLAY,
				DiagramConstants.SHAPE_DOCUMENT, DiagramConstants.SHAPE_PROCESS };
		try {
			List<String> fs = getResourceFiles(DiagramConstants.SHAPES_PATH);

			for (String s : fs) {
				InputStream p = getResourceAsStream(DiagramConstants.SHAPES_PATH  + s);
				if (p != null && s.endsWith("shape")) {
					String nodeXml;
					try {
						nodeXml = mxUtils.readInputStream(p);
						String name = addStencilShape(nodeXml);
						shapes[i++] = name;
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						p.close();
					}
				} else if (p != null) {
					p.close();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shapes;
	}

	private String addStencilShape(String nodeXml) throws ParserConfigurationException, SAXException, IOException {
		String name = null;
		int lessthanIndex = nodeXml.indexOf("<");
		nodeXml = nodeXml.substring(lessthanIndex);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(nodeXml)));		
		mxStencilShapeExtended newShape = new mxStencilShapeExtended(doc);
		name = newShape.getName();
		mxGraphics2DCanvas.putShape(name, newShape);
		mxSvgCanvasExtended.putShape(name, newShape);
		return name;
	}

	private List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();
		try (InputStream in = getResourceAsStream(path);

				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}
		if (filenames.isEmpty()) {
			filenames.add("display.shape");
			filenames.add("document.shape");
			filenames.add("magdisk.shape");
			filenames.add("predefdproc.shape");
		}
		return filenames;
	}

	private InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);

		return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
