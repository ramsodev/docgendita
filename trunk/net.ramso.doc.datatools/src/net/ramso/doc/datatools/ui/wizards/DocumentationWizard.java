package net.ramso.doc.datatools.ui.wizards;

import java.util.List;

import net.ramso.doc.datatools.generator.ProcessGenerator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class DocumentationWizard extends Wizard {
	private DocumentationWizardPage	page;
	private ISelection				selection;
	private List<SQLObject>			sqlobjects;
	public static String IMGFOLDER = "images";

	@SuppressWarnings("unchecked")
	public DocumentationWizard(List list) {
		super();
		sqlobjects = list;
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
		ProcessGenerator generator = new ProcessGenerator(sqlobjects, folder);
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
