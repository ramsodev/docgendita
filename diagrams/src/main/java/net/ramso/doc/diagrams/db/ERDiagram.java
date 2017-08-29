/**
 * 
 */
package net.ramso.doc.diagrams.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mxgraph.model.mxCell;
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
		setLayout(DiagramConstants.LAYOUT_TREE_V);
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
					switch ((int) relation[0]) {
					case TableData.ONETOONE:
						text = "1 .. 1";
						style = mxConstants.STYLE_EDGE + "=" + mxConstants.EDGESTYLE_ENTITY_RELATION + ";"
								+ mxConstants.STYLE_STARTARROW + "=" + mxConstants.NONE + ";"
								+ mxConstants.STYLE_ENDARROW + "=" + mxConstants.NONE;
						break;

					case TableData.ONETOMANY:
						text = "1 .. n";
						style = mxConstants.STYLE_EDGE + "=" + mxConstants.EDGESTYLE_ENTITY_RELATION + ";"
								+ mxConstants.STYLE_STARTARROW + "=" + mxConstants.NONE + ";"
								+ mxConstants.STYLE_ENDARROW + "=" + mxConstants.ARROW_OVAL;
						break;
					default:
						text = "";
						style = mxConstants.STYLE_EDGE + "=" + mxConstants.EDGESTYLE_ENTITY_RELATION + ";"
								+ mxConstants.STYLE_STARTARROW + "=" + mxConstants.ARROW_OPEN + ";"
								+ mxConstants.STYLE_ENDARROW + "=" + mxConstants.ARROW_OPEN;
						break;
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
		String color = "lightgray";
		String icon = "primary_key.png";
		switch (data.getType()) {
		case TableData.TABLE:
			color = "lightgray";
			break;
		case TableData.VIEW:
			color = "yellow";
			break;
		default:
			h = w;
			Object cell = getGraph().insertVertex(parent, null, data.getSchema().trim() + "." + data.getName().trim(),
					x, y, w, h, "shape=" + mxConstants.SHAPE_RHOMBUS);
			return cell;
		}

		Object[] cells = new Object[(data.getPrimaryKeys().size() * 2) + 2];
		h = data.getPrimaryKeys().size() * 20;
		// cells[i++] = getGraph().insertVertex(parent, null, null, x, y, w+10,
		// h+10,"ROUNDED");
		cells[i++] = getGraph().insertVertex(parent, null, data.getSchema().trim() + "." + data.getName().trim(), x, y,
				w, 20, mxConstants.STYLE_FILLCOLOR + "=" + color + ";" + mxConstants.STYLE_VERTICAL_ALIGN + "="
						+ mxConstants.ALIGN_MIDDLE);
		y += 20;
		// Añadir una caja para pk
		int pi = i;
		// cells[i++] = getGraph().insertVertex(parent, null, null, x, y, w, h);
		for (Object[] pk : data.getPrimaryKeys()) {
			String name = ((String) pk[0]).trim();
			switch ((int) pk[1]) {
			case TableData.PK:
				icon = "primary_key.png";
				break;
			case TableData.UNIQUE:
				icon = "unique_key.png";
				break;
			case TableData.ORDER:
				icon = "Secuencia.png";
				break;
			case TableData.ON:
				icon = "PKRelationTable.png";
				break;
			case TableData.FILTER:
				icon = "filter.png";
				break;
			default:
				icon = "Column.png";
				break;
			}
			cells[i++] = getGraph().insertVertex(parent, null, name, x + 10, y, w - 10, 20,
					mxConstants.STYLE_ALIGN + "=" + mxConstants.ALIGN_LEFT + ";" + mxConstants.STYLE_STROKEWIDTH + "=0"
							+ ";" + mxConstants.STYLE_FONTSTYLE + "=" + mxConstants.FONT_UNDERLINE + ";"
							+ mxConstants.STYLE_FILLCOLOR + "=none;");

			cells[i++] = getGraph().insertVertex(parent, null, null, x + 1, y, 10, 20, mxConstants.STYLE_SHAPE + "="
					+ mxConstants.SHAPE_IMAGE + ";" + mxConstants.STYLE_IMAGE + "=" + icon);
			y += 20;
		}
		// TODO: Añadir campos
		// cells[i++] = getGraph().insertVertex(parent, null, null, x, y, w,
		// 20,mxConstants.STYLE_FILLCOLOR + "=lightgray");
		mxCell g = (mxCell) getGraph().groupCells(null, 0, cells);
		return g;
	}

}
