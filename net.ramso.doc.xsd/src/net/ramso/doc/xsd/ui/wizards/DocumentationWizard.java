package net.ramso.doc.xsd.ui.wizards;

import net.ramso.doc.xsd.generator.ProcessGenerator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.xsd.util.XSDResourceImpl;

public class DocumentationWizard extends Wizard {
	private DocumentationWizardPage	page;
	private ISelection				selection;
	private XSDResourceImpl		xsd;

	@SuppressWarnings("unchecked")
	public DocumentationWizard(XSDResourceImpl xsd) {
		super();
		this.xsd = xsd;
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new DocumentationWizardPage(selection);
		addPage(page);
	}

	public void performDocumentationGeneration(IProgressMonitor monitor) {
		monitor.worked(2);
		monitor.setTaskName("Generando documentación"); //$NON-NLS-1$
		IPath thePath = new Path(page.getContainerName());
		IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				thePath);
		ProcessGenerator generator = new ProcessGenerator(xsd, folder);
		generator.setTitle(page.getTitle());
		generator.setDes(page.getDescription());
		generator.setAutor(page.getAutor());
		generator.setLang(page.getLang());
		generator.process(monitor);
		monitor.worked(5);
	}

	@Override
	public boolean performFinish() {
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor) {
				try {
					performDocumentationGeneration(monitor);
				}
				catch (Exception e) {
					// System.out.println(e.getMessage());
					e.printStackTrace();
				}
				finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(false, false, operation);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
