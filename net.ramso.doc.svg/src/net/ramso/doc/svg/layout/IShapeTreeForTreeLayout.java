/**
 * 
 */
package net.ramso.doc.svg.layout;

import net.ramso.doc.svg.shapes.IShape;

import org.abego.treelayout.TreeForTreeLayout;

/**
 * @author ramso
 *
 */
public interface IShapeTreeForTreeLayout {
	
	 TreeForTreeLayout<IShape> getTree();
	
	
}
