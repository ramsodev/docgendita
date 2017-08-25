package net.ramso.doc.diagrams.jgrapx;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class mxGraphExtended extends mxGraph {

    @Override
    public void drawCell(mxICanvas canvas, Object cell) {
        
        mxCellState state = this.getView().getState(cell);
        state.getStyle().put("cellid", ((mxCell)cell).getId());

        super.drawCell(canvas, cell);
    }
}