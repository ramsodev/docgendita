package net.ramso.doc.as400.handlers;

import net.ramso.doc.as400.ui.wizards.DocumentationWizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class GendocHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public GendocHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Wizard wizard = new DocumentationWizard();
		WizardDialog dialog = new WizardDialog(window
				.getShell(), wizard);
		dialog.create();
		dialog.open();
		return null;
	}
}
