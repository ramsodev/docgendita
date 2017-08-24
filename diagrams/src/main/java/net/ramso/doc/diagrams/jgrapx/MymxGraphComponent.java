package net.ramso.doc.diagrams.jgrapx;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxGraphHandler;
import com.mxgraph.view.mxGraph;

/**
 * Se debe elimnar el drag an drop para poder ser usado en mode headless
 * @author jjescudero
 *
 */
public class MymxGraphComponent extends mxGraphComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MymxGraphComponent(mxGraph graph) {
		super(graph);
	}

	protected mxGraphHandler createGraphHandler() {
		return new MymxGraphHandler(this);
	}
}
