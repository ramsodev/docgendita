package net.ramso.doc.as400.ui.wizards;

import java.util.Locale;

import net.ramso.doc.as400.Messages;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */
public class DocumentationWizardPageiSeries extends WizardPage {
	private Text		systemText;
	private Text		userText;
	private Text		passwordText;
	private Text		libText;
	private Text	liblText;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public DocumentationWizardPageiSeries() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.DocumentationWizardPage_wizard_title);
		setDescription(Messages.DocumentationWizardPage_wizard_description);
		
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("System");
		systemText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		systemText.setLayoutData(gd);
		systemText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("User");
		userText = new Text(container, SWT.BORDER | SWT.SINGLE);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.horizontalSpan = 2;
//		userText.setLayoutData(gd);
		userText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("Password");
//		gd = new GridData(GridData.FILL_VERTICAL);
//		gd.verticalAlignment = SWT.CENTER;
//		gd.verticalSpan = 3;
		label.setLayoutData(gd);
		passwordText = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.PASSWORD);
//		gd = new GridData(GridData.FILL_BOTH);
//		gd.horizontalSpan = 2;
		passwordText.setLayoutData(gd);
		passwordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("Generation Library");
		libText = new Text(container, SWT.BORDER | SWT.SINGLE);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.horizontalSpan = 2;
		libText.setLayoutData(gd);
		libText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText(Messages.DocumentationWizardPage_doc_lang);
		liblText = new Text(container, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		
//		gd = new GridData(GridData.FILL_BOTH);
//		gd.horizontalSpan = 2;
//		gd.verticalSpan = 3;
//		liblText.setLayoutData(gd);
		liblText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
//		IResource container = ResourcesPlugin.getWorkspace().getRoot()
//				.findMember(new Path(getContainerName()));
//		if (getContainerName().length() == 0) {
//			updateStatus(Messages.DocumentationWizardPage_error_no_folder);
//			return;
//		}
//		if (container == null || (container.getType() & IResource.FOLDER) == 0) {
//			updateStatus(Messages.DocumentationWizardPage_error_no_folder_type);
//			return;
//		}
//		if (!container.isAccessible()) {
//			updateStatus(Messages.DocumentationWizardPage_error_no_write);
//			return;
//		}
//		if (getTitle().length() == 0) {
//			updateStatus(Messages.DocumentationWizardPage_error_no_title);
//			return;
//		}
//		if (getDescription().length() == 0) {
//			updateStatus(Messages.DocumentationWizardPage_error_no_description);
//			return;
//		}
//		if (getAutor().length() == 0) {
//			updateStatus(Messages.DocumentationWizardPage_error_no_autor);
//			return;
//		}
		updateStatus(null);
	}

	
	

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
}