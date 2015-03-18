package net.ramso.doc.xsd.handlers;

import net.ramso.doc.xsd.ui.wizards.DocumentationWizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xsd.util.XSDResourceImpl;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class DocumentationHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public DocumentationHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelection(event);
		;
		Object element = selection.getFirstElement();
		if (element instanceof IFile) {
			XSDResourceImpl xsdSchemaResource = getXSD(((IFile) element)
					.getFullPath().toString());
			try {
				if (xsdSchemaResource != null) {
					Wizard wizard = new DocumentationWizard(xsdSchemaResource);
					WizardDialog dialog = new WizardDialog(HandlerUtil
							.getActiveWorkbenchWindow(event).getShell(), wizard);
					dialog.create();
					dialog.open();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static XSDResourceImpl getXSD(String path) {
		try {
			ResourceSet resourceSet = new ResourceSetImpl();
			URI fileURI = URI.createPlatformResourceURI(path, true);
			XSDResourceImpl xsd = (XSDResourceImpl) resourceSet.getResource(
					fileURI, true);
			return xsd;
		}
		catch (Exception e) {
			return null;
		}
	}
}
