/**
 * 
 */
package net.ramso.doc.diagrams.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mxgraph.util.mxConstants;

import net.ramso.doc.diagrams.BaseDiagram;
import net.ramso.doc.diagrams.tools.DiagramConstants;

/**
 * @author ramso
 */
public class ERDiagram extends BaseDiagram {
	ArrayList<TableData> objs = new ArrayList<TableData>();
	private int w;
	private HashMap<String, Object> vertex = new HashMap<String, Object>();
	private HashMap<String, Object> edges = new HashMap<String, Object>();

	public ERDiagram(ArrayList<TableData> objs) {
		super();
		setObjs(objs);
		setFileName(getObjs().get(0).getName());
		w = 100;
	}

	/**
	 * @return the objs
	 */
	public ArrayList<TableData> getObjs() {
		return objs;
	}

	/**
	 * @param objs
	 *            the objs to set
	 */
	public void setObjs(ArrayList<TableData> objs) {
		this.objs = objs;
	}

	@Override
	protected void addConnectors() {
		getGraph().getModel().beginUpdate();
		Object parent = getGraph().getDefaultParent();
		try {
			for (Entry<String, Object> edge : edges.entrySet()) {
				Object source = vertex.get(edge.getKey());
				ArrayList<Object[]> value = (ArrayList<Object[]>) edge.getValue();
				for (Object[] relation : value) {
					String text = "1 .. n";
					String style = mxConstants.STYLE_EDGE + "=" + mxConstants.EDGESTYLE_ENTITY_RELATION + ";"
							+ mxConstants.STYLE_STARTARROW + "=" + mxConstants.NONE + ";" + mxConstants.STYLE_ENDARROW
							+ "=" + mxConstants.ARROW_OVAL;
					if (((int) relation[0]) == TableData.ONETOONE) {
						text = "1 .. 1";
						style += mxConstants.STYLE_EDGE + "=" + mxConstants.EDGESTYLE_ENTITY_RELATION + ";"
								+ mxConstants.STYLE_STARTARROW + "=" + mxConstants.NONE + ";"
								+ mxConstants.STYLE_ENDARROW + "=" + mxConstants.NONE;
					}
					Object target = vertex.get((String) relation[1]);
					getGraph().insertEdge(parent, null, text, source, target, style);
				}
			}
		} finally {
			getGraph().getModel().endUpdate();
		}

	}

	@Override
	protected void addComponents() {
		getGraph().getModel().beginUpdate();
		Object parent = getGraph().getDefaultParent();
		try {
			for (TableData data : objs) {
				Object v = insert(parent, data);
				vertex.put(data.getName(), v);
				edges.put(data.getName(), data.getRelations());
			}
		} finally {
			getGraph().getModel().endUpdate();
		}
	}

	public Object insert(Object parent, TableData data) {
		int h = 20 + (data.getPrimaryKeys().size() * 20);
		double x = (getGraph().getModel().getChildCount(parent) * 100) + DiagramConstants.DEFAULT_POS_X;
		double y = DiagramConstants.DEFAULT_POS_Y + x;
		int i = 0;
		Object[] cells = new Object[data.getPrimaryKeys().size() + 3];
		h = data.getPrimaryKeys().size() * 20;
		// cells[0] = getGraph().insertVertex(parent, null, null, x, y, w, h);
		cells[i++] = getGraph().insertVertex(parent, null, data.getSchema().trim() + "." + data.getName().trim(), x, y,
				w, 20, mxConstants.STYLE_FILLCOLOR + "=lightgray;" + mxConstants.STYLE_VERTICAL_ALIGN + "="
						+ mxConstants.ALIGN_MIDDLE);// BAckground gris
		y += 20;
		// Añadir una caja para pk
		int pi = i;
//		cells[i++] = getGraph().insertVertex(parent, null, null, x, y, w, h);

		for (String pk : data.getPrimaryKeys()) {
			cells[i++] = getGraph().insertVertex(parent, null, pk.trim(), x + 10, y, w - 10, 20,
					mxConstants.STYLE_ALIGN + "=" + mxConstants.ALIGN_LEFT + ";" + mxConstants.STYLE_STROKEWIDTH + "=0"
							+ ";" + mxConstants.STYLE_FONTSTYLE + "=" + mxConstants.FONT_UNDERLINE + ";"
							+ mxConstants.STYLE_FILLCOLOR + "=none;");
			y += 20;
		}
		// TODO: Añadir campos
//		 cells[i++] = getGraph().insertVertex(parent, null, null, x, y, w,
//		 20,mxConstants.STYLE_FILLCOLOR + "=lightgray");
		Object g = getGraph().groupCells(null, 0, cells);
		return g;
	}

}
