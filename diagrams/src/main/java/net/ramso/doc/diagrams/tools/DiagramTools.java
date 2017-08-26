package net.ramso.doc.diagrams.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.ImageIcon;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;

import net.ramso.doc.diagrams.jgrapx.mxStencilShapeExtended;
import net.ramso.doc.diagrams.jgrapx.mxSvgCanvasExtended;

public class DiagramTools {

	private static mxStylesheet stylesheet;

	public static mxStylesheet loadShapes() {
		if (stylesheet == null) {
			String[] shapes = { DiagramConstants.SHAPE_DISK, DiagramConstants.SHAPE_DISPLAY,
					DiagramConstants.SHAPE_DOCUMENT, DiagramConstants.SHAPE_PROCESS };
			int i = 0;
			try {
				URI uri = DiagramTools.class.getResource(DiagramConstants.SHAPES_PATH).toURI();
				Path myPath;
				if (uri.getScheme().equals("jar")) {
					FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
					myPath = fileSystem.getPath(DiagramConstants.SHAPES_PATH);
				} else {
					myPath = Paths.get(uri);
				}
				Stream<Path> walk = Files.walk(myPath, 1);
				for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
					Path s = it.next();
					InputStream p = DiagramTools.class
							.getResourceAsStream(DiagramConstants.SHAPES_PATH + 
									File.separator + s.getFileName());
					if (p != null && s.getFileName().toString().endsWith("shape")) {
						String nodeXml;
						try {
							nodeXml = mxUtils.readInputStream(p);
							String name = addStencilShape(nodeXml);
							shapes[i++] = name;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
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
		mxGraphics2DCanvas.putShape(name, newShape);
		mxSvgCanvasExtended.putShape(name, newShape);
		return name;
	}

}
