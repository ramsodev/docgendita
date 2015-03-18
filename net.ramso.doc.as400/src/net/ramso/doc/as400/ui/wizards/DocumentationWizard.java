package net.ramso.doc.as400.ui.wizards;

import net.ramso.doc.as400.generator.ProcessGenerator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class DocumentationWizard extends Wizard {
	private DocumentationWizardPage	page;
	private ISelection				selection;
	private DocumentationWizardPageiSeries	page1;
	public static String IMGFOLDER = "images";

	@SuppressWarnings("unchecked")
	public DocumentationWizard() {
		super();
		
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
		page1 = new DocumentationWizardPageiSeries();
		addPage(page1);
	}

	public void performDocumentationGeneration(IProgressMonitor monitor) {
		monitor.worked(2);
		monitor.setTaskName("Generando documentación"); //$NON-NLS-1$
		IPath thePath = new Path(page.getContainerName());
		IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				thePath);
		IFolder imgf = folder.getFolder(IMGFOLDER);
		if(!imgf.exists()){
			try {
				imgf.create(false, true, monitor);
			}
			catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String[] libs = page1.getLibl().toUpperCase().split(",");
		ProcessGenerator generator = new ProcessGenerator(libs, folder);
		generator.setTitle(page.getTitle());
		generator.setDes(page.getDescription());
		generator.setAutor(page.getAutor());
		generator.setLang(page.getLang());
		generator.setSystem(page1.getSystem());
		generator.setUser(page1.getUser());
		generator.setPassword(page1.getPassword());
		generator.setLib(page1.getLib().toUpperCase());
		generator.setCreate(page1.isCreate());
		generator.setDelete(page1.isDelete());
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
