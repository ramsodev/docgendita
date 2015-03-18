/**
 * 
 */
package net.ramso.doc.svg.layout;

import net.ramso.doc.svg.shapes.IShape;

import org.abego.treelayout.NodeExtentProvider;

/**
 * @author ramso
 *
 */
public class ShapesNodeExtendProvider implements NodeExtentProvider<IShape> {

	/* (non-Javadoc)
	 * @see org.abego.treelayout.NodeExtentProvider#getWidth(java.lang.Object)
	 */
	@Override
	public double getWidth(IShape treeNode) {
		return treeNode.getWidth();
	}

	/* (non-Javadoc)
	 * @see org.abego.treelayout.NodeExtentProvider#getHeight(java.lang.Object)
	 */
	@Override
	public double getHeight(IShape treeNode) {
		return treeNode.getHeight();
	}
}
