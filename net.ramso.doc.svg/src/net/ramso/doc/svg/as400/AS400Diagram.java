/**
 * 
 */
package net.ramso.doc.svg.as400;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import net.ramso.doc.svg.diagrams.BaseDiagram;
import net.ramso.doc.svg.shapes.IShape;
import net.ramso.doc.svg.shapes.SVGFactory;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.w3c.dom.Element;

/**
 * @author ramso
 */
public class AS400Diagram extends BaseDiagram {
	ArrayList<AS400Object>	objs	= new ArrayList<AS400Object>();

	public AS400Diagram(ArrayList<AS400Object> objs) {
		super();
		setObjs(objs);
	}

	public void run() {
		setFileName(getObjs().get(0).getName());
		AS400TreeForTreeLayout as400Tree = new AS400TreeForTreeLayout(
				getObjs(), getDocument());
		layout(as400Tree);
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

	
	

	/* (non-Javadoc)
	 * @see net.ramso.doc.svg.diagrams.BaseDiagram#addConnectors(org.abego.treelayout.TreeLayout)
	 */
	@Override
	public void addConnectors(TreeLayout<IShape> treeLayout, IShape parent) {
		TreeForTreeLayout<IShape> tree = treeLayout.getTree();
		if(!tree.isLeaf(parent)){
			Rectangle2D.Double parentPosition = treeLayout.getNodeBounds().get(parent);
			int[] pos = new int[4];
			pos[0] = new Double(parentPosition.getCenterX()).intValue();
			pos[1] = new Double(parentPosition.getCenterY()).intValue() + parent.getHeight()/2;
			for(IShape shape: tree.getChildren(parent)){
				Rectangle2D.Double childPosition = treeLayout.getNodeBounds().get(shape);
				pos[2] = new Double(childPosition.getCenterX()).intValue();
				pos[3] = new Double(childPosition.getCenterY()).intValue() - shape.getHeight()/2;
				Element con = SVGFactory.createConnectorPath("",SVGFactory.VERTICALDIR, pos, getDocument());
				getGroup().appendChild(con);
				addConnectors(treeLayout, shape);
			}
		}
		
	}
}
