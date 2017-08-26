package net.ramso.doc.diagrams.tools;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.shape.mxStencilShape;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;

import net.ramso.doc.diagrams.jgrapx.mxStencilShapeExtended;
import net.ramso.doc.diagrams.jgrapx.mxSvgCanvasExtended;

public class DiagramTools {

	private static mxStylesheet stylesheet;

	public static mxStylesheet loadShapes() {
		if (stylesheet == null) {
			URL f = DiagramTools.class.getResource("/net/ramso/doc/diagrams/shapes/");
			String filename = f.getPath();
			File dir = new File(filename);
			String[] shapes = { DiagramConstants.SHAPE_DISK, DiagramConstants.SHAPE_DISPLAY,
					DiagramConstants.SHAPE_DOCUMENT, DiagramConstants.SHAPE_PROCESS };
			int i = 0;
			if (dir.isDirectory()) {
				File[] ss = dir.listFiles();
				for (File s : ss) {
					if (s.getAbsolutePath().endsWith("shape")) {
						String nodeXml;
						try {
							nodeXml = mxUtils.readFile(s.getAbsolutePath());
							String name = addStencilShape(nodeXml);
							shapes[i++] = name;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
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
				Map<String, Object> style = new HashMap<String, Object>();
				style.put(mxConstants.STYLE_SHAPE, sh);
				styles.put(sh, style);
			}
			stylesheet.setStyles(styles);
		}
		return stylesheet;
	}

	public static String addStencilShape(String nodeXml) {
		int lessthanIndex = nodeXml.indexOf("<");
		nodeXml = nodeXml.substring(lessthanIndex);
		mxStencilShapeExtended newShape = new mxStencilShapeExtended(nodeXml);
		String name = newShape.getName();
		ImageIcon icon = null;
		mxGraphics2DCanvas.putShape(name, newShape);
		mxSvgCanvasExtended.putShape(name, newShape);
		return name;
	}
}
