/**
 * 
 */
package net.ramso.doc.diagrams;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxSvgCanvas;
import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxCellRenderer.CanvasFactory;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;

import net.ramso.doc.diagrams.jgrapx.MymxGraphComponent;
import net.ramso.doc.diagrams.jgrapx.mxSvgCanvasExtended;
import net.ramso.doc.diagrams.tools.DiagramConstants;
import net.ramso.doc.diagrams.tools.DiagramTools;
import net.ramso.tools.TextUtils;

/**
 * @author ramso
 * 
 *         Atencion: Necesita modificar jgraphx para poder funcionar en modo
 *         headless. Es necesario eliminar el contenido de
 *         com.mxgraph.swing.handler.mxGraphHandler.installDragGestureHandler
 * 
 */
public abstract class BaseDiagram {

	private String fileName = null;

	private mxGraphComponent graphComponent;
	private mxGraph graph;
	private String layout;

	public BaseDiagram() {
		super();
		// System.setProperty("java.awt.headless", "true");
		setFileName("diagram");
		setLayout(DiagramConstants.LAYOUT_ORGANIC);
	}

	public void run() {
		addComponents();
		addConnectors();
		layout();
	}

	public void save(String path) throws IOException {
		save(path, DiagramConstants.EXPORT_PNG);
	}

	public void save(String path, String type) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		} else if (!f.isDirectory()) {
			throw new IOException(f.getAbsolutePath() + " not a directory");
		}
		if (type == DiagramConstants.EXPORT_PNG) {
			savePng(path);
		} else {
			saveSvg(path);
		}
	}

	private void saveSvg(String path) throws IOException {
		String file_name = path.trim() + File.separator + getFileName() + DiagramConstants.EXTENSION_SVG;
		mxSvgCanvasExtended canvas = (mxSvgCanvasExtended) mxCellRenderer.drawCells(getGraph(), null, 1, null,
				new CanvasFactory() {
					public mxICanvas createCanvas(int width, int height) {
						mxSvgCanvasExtended canvas = new mxSvgCanvasExtended(
								mxDomUtils.createSvgDocument(width, height));
						canvas.setEmbedded(true);
						canvas.setImageBasePath(DiagramConstants.ICONS_PATH);
						return canvas;
					}
				});
		mxUtils.writeFile(mxXmlUtils.getXml(canvas.getDocument()), file_name);
	}

	private void savePng(String path) throws IOException {
		String file_name = path.trim() + File.separator + getFileName() + DiagramConstants.EXTENSION_PNG;
		mxGraphComponent graphComponent = getGraphComponent();
		graphComponent.getCanvas().setImageBasePath("/"+DiagramConstants.ICONS_PATH);
		graphComponent.setSize(graphComponent.getPreferredSize());

		graphComponent.updateComponents();
		mxGraph graph = graphComponent.getGraph();
		BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE,
				graphComponent.isAntiAlias(), null, graphComponent.getCanvas());
		// Creates the URL-encoded XML data
		mxCodec codec = new mxCodec();
		String xml = URLEncoder.encode(mxXmlUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
		mxPngEncodeParam param = mxPngEncodeParam.getDefaultEncodeParam(image);
		param.setCompressedText(new String[] { "mxGraphModel", xml });
		// Saves as a PNG file
		FileOutputStream outputStream = new FileOutputStream(new File(file_name));
		try {
			mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream, param);
			if (image != null) {
				encoder.encode(image);
			}
		} finally {
			outputStream.close();
		}
	}

	protected mxGraphComponent getGraphComponent() {
		if (graphComponent == null) {
			graphComponent = new MymxGraphComponent(getGraph());
			graphComponent.getCanvas().setImageBasePath("/"+DiagramConstants.ICONS_PATH);
			graphComponent.setSize(graphComponent.getPreferredSize());
		}
		graphComponent.updateComponents();
		return graphComponent;
	}

	protected mxGraph getGraph() {
		if (graph == null) {
			graph = new mxGraph(DiagramTools.loadStyleshet());
			graph.setBorder(10);
		}
		return graph;
	}

	protected Object addVertex(String title, String shape) {
		getGraph().getModel().beginUpdate();
		Object parent = getGraph().getDefaultParent();
		Object vertex;
		String style = "shape=" + shape;
		int x = getGraph().getModel().getChildCount(parent) * 100;
		try {
			vertex = getGraph().insertVertex(parent, null, title, DiagramConstants.DEFAULT_POS_X + x,
					DiagramConstants.DEFAULT_POS_Y + x, DiagramConstants.DEFAULT_SIZE_WIDTH,
					DiagramConstants.DEFAULT_SIZE_HEIGHT, style);
		} finally {
			getGraph().getModel().endUpdate();
		}
		return vertex;
	}

	protected void addEdge(String title, Object source, Object target) {
		getGraph().getModel().beginUpdate();
		Object parent = getGraph().getDefaultParent();
		try {
			getGraph().insertEdge(parent, null, title, source, target);
		} finally {
			getGraph().getModel().endUpdate();
		}
	}

	protected void layout() {
		mxIGraphLayout layout = null;
		switch (getLayout()) {
		case DiagramConstants.LAYOUT_CIRCULAR:
			layout = new mxCircleLayout(getGraph());
			break;
		case DiagramConstants.LAYOUT_TREE_H:
			layout = new mxCompactTreeLayout(getGraph(), true);
			break;
		case DiagramConstants.LAYOUT_TREE_V:
			layout = new mxCompactTreeLayout(getGraph(), false);
			break;
		default:
			layout = new mxOrganicLayout(getGraph());
			((mxOrganicLayout) layout).setOptimizeEdgeCrossing(true);
			((mxOrganicLayout) layout).setOptimizeEdgeDistance(true);
			((mxOrganicLayout) layout).setOptimizeEdgeLength(true);
			((mxOrganicLayout) layout).setOptimizeNodeDistribution(true);
			((mxOrganicLayout) layout).setDisableEdgeStyle(false);
			break;
		}

		getGraph().getModel().beginUpdate();

		try {
			layout.execute(getGraph().getDefaultParent());
		} finally {
			getGraph().getModel().endUpdate();
		}
	}

	public double getWidth() {
		return getGraphComponent().getPreferredSize().getWidth();

	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = TextUtils.clean(fileName);
	}

	protected abstract void addConnectors();

	protected abstract void addComponents();

	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * @param layout
	 *            the layout to set
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

}
