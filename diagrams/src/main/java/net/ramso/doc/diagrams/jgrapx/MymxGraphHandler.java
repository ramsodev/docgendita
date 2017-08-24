package net.ramso.doc.diagrams.jgrapx;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxGraphHandler;

/**
 * @author jjescudero
 * Se debe elimnar el drag an drop para poder ser usado en mode headless
 */
public class MymxGraphHandler extends mxGraphHandler {
	public MymxGraphHandler(mxGraphComponent graphComponent) {
		super(graphComponent);
	}

	protected void installDragGestureHandler() {
		// My Blank implementation for the installDragGestureHandler
	}
}
