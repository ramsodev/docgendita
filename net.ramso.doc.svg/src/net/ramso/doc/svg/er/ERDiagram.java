/**
 * 
 */
package net.ramso.doc.svg.er;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import net.ramso.doc.svg.diagrams.BaseDiagram;
import net.ramso.doc.svg.shapes.IShape;
import net.ramso.doc.svg.shapes.SVGFactory;
import net.ramso.doc.svg.utils.TextUtils;

import org.abego.treelayout.TreeLayout;
import org.w3c.dom.Element;

/**
 * @author ramso
 */
public class ERDiagram extends BaseDiagram {
	ArrayList<TableData>		objs	= new ArrayList<TableData>();
	private ERTreeForTreeLayout	erTree;
	private static final int	TOP		= 0;
	private static final int	CENTER	= 1;
	private static final int	DOWN	= 3;

	public ERDiagram(ArrayList<TableData> objs) {
		super();
		setObjs(objs);
	}

	public void run() {
		setFileName(getObjs().get(0).getName());
		erTree = new ERTreeForTreeLayout(getObjs(), getDocument());
		layout(erTree);
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

	/*
	 * (non-Javadoc)
	 * @see
	 * net.ramso.doc.svg.diagrams.BaseDiagram#addConnectors(org.abego.treelayout
	 * .TreeLayout)
	 */
	@Override
	public void addConnectors(TreeLayout<IShape> treeLayout, IShape parent) {
		ArrayList<IShape[]> connectors = erTree.getConnectors();
		for (IShape[] iShapes : connectors) {
			Rectangle2D.Double parentPosition = treeLayout.getNodeBounds().get(
					iShapes[0]);
			Rectangle2D.Double childPosition = treeLayout.getNodeBounds().get(
					iShapes[1]);
			int[] pos = new int[4];
			String id = iShapes[0].getId() + "_" +iShapes[1].getId();
			if (parentPosition.getCenterY() > childPosition.getCenterY()) {
				pos[1] = new Double(parentPosition.getMinY()).intValue();
				pos[3] = new Double(childPosition.getMaxY()).intValue();
				calcX(id,TOP, parentPosition, childPosition, pos);
			}
			else if (parentPosition.getCenterY() < childPosition.getCenterY()) {
				pos[1] = new Double(parentPosition.getMaxY()).intValue();
				pos[3] = new Double(childPosition.getMinY()).intValue();
				calcX(id,DOWN, parentPosition, childPosition, pos);
			}
			else {
				pos[1] = new Double(parentPosition.getCenterY()).intValue();
				pos[3] = new Double(childPosition.getCenterY()).intValue();
				calcX(id,CENTER, parentPosition, childPosition, pos);
			}
			// addConnectors(treeLayout, null);
		}
	}

	/**
	 * @param top2
	 * @param parentPosition
	 * @param childPosition
	 * @param pos
	 */
	private void calcX(String id, int level,
			java.awt.geom.Rectangle2D.Double parentPosition,
			java.awt.geom.Rectangle2D.Double childPosition, int[] pos) {
		Element con = null;
		switch (level) {
			case CENTER:
				if (parentPosition.getCenterX() > childPosition.getCenterX()) {
					pos[0] = new Double(parentPosition.getMinX()).intValue();
					pos[2] = new Double(childPosition.getMaxX()).intValue();
				}
				else {
					pos[0] = new Double(parentPosition.getMaxX()).intValue();
					pos[2] = new Double(childPosition.getMinX()).intValue();
				}
				con = SVGFactory.createConnectorPath(id,
						SVGFactory.HOROZONTALDIR, pos, getDocument(), SVGFactory.CONNECTOR_ER_ONETOMANY);
				break;
			default:
				pos[0] = new Double(parentPosition.getCenterX()).intValue();
				pos[2] = new Double(childPosition.getCenterX()).intValue();
				con = SVGFactory.createConnectorPath(id,
						SVGFactory.VERTICALDIR, pos, getDocument(), SVGFactory.CONNECTOR_ER_ONETOMANY);
				break;
		}
		getGroup().appendChild(con);
	}

	public int getWidth() {
		
		return 501;
	}
}
