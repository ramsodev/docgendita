package net.ramso.doc.datatools.ui.popup;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class DocumentationActionProvider extends CommonActionProvider
	{
		private static final DocumentationAction action = new DocumentationAction();
		protected ISelectionProvider selectionProvider;
		protected CommonViewer viewer;
		protected ActionContributionItem ITEM;
	
		public void init(ICommonActionExtensionSite aSite)
		{
			super.init(aSite);
			this.selectionProvider = aSite.getViewSite().getSelectionProvider();
			this.viewer = (CommonViewer) aSite.getStructuredViewer();
			initActionContributionItem();
		}
	
		public void fillContextMenu(IMenuManager menu)
		{
			action.setCommonViewer(this.viewer);
			action.selectionChanged(new SelectionChangedEvent(this.selectionProvider, this.getContext().getSelection()));
			
			menu.insertAfter("slot3",action);
			
		}
	
		protected void initActionContributionItem()
		{
			ITEM = new ActionContributionItem(action);
		}
	
}

