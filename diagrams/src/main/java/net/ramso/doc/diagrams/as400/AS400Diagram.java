/**
 * 
 */
package net.ramso.doc.diagrams.as400;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mxgraph.util.mxConstants;

import net.ramso.doc.diagrams.BaseDiagram;
import net.ramso.doc.diagrams.tools.DiagramConstants;
import net.ramso.tools.TextUtils;

/**
 * @author ramso
 */
public class AS400Diagram extends BaseDiagram {
	ArrayList<AS400Object> objs = new ArrayList<AS400Object>();
	private HashMap<String, Object> vertex;
	private HashMap<String, String> edges;

	public AS400Diagram(ArrayList<AS400Object> objs) {
		super();

		vertex = new HashMap<String, Object>();
		edges = new HashMap<String, String>();
		setObjs(objs);
		setFileName(getObjs().get(0).getName());
	}

	protected void addComponents() {
		for (AS400Object obj : getObjs()) {
			if (!vertex.containsKey(obj.getName())) {
				Object shape;
				String title = TextUtils.createMultilineString(
						new String[] { obj.getName(), obj.getLib(), obj.getAttribute() + "/" + obj.getType() });
				if ((obj.getType().compareTo("*PGM") == 0) | (obj.getType().compareTo("*SRVPGM") == 0)) {
					shape = addVertex(title, DiagramConstants.SHAPE_PROCESS);

				} else if ((obj.getAttribute().compareTo("PF-DTA") == 0) | (obj.getAttribute().compareTo("LF") == 0)) {
					shape = addVertex(title, DiagramConstants.SHAPE_DISK);
				} else if (obj.getAttribute().compareTo("DSPF") == 0) {
					shape = addVertex(title, DiagramConstants.SHAPE_DISPLAY);
				} else if (obj.getAttribute().compareTo("PRTF") == 0) {
					shape = addVertex(title, DiagramConstants.SHAPE_DOCUMENT);
				} else if (obj.getType().compareTo("*FILE") == 0) {
					shape = addVertex(title, mxConstants.SHAPE_CYLINDER);
				} else {
					shape = addVertex(title, mxConstants.SHAPE_RECTANGLE);
				}
				vertex.put(obj.getName(), shape);
			}
			edges.put(obj.getName(), obj.getParent());
		}
	}

	/**
	 * @return the objs
	 */
	public ArrayList<AS400Object> getObjs() {
		return objs;
	}

	/**
	 * @param objs
	 *            the objs to set
	 */
	public void setObjs(ArrayList<AS400Object> objs) {
		this.objs = objs;
	}

	protected void addConnectors() {
		for (Entry<String, String> edge : edges.entrySet()) {
			if (!edge.getKey().equalsIgnoreCase(edge.getValue())) {
				addEdge(null, vertex.get(edge.getValue()), vertex.get(edge.getKey()));
			}
		}
	}

}
