package net.ramso.doc.as400.ui.wizards;

import net.ramso.doc.as400.Messages;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */
public class DocumentationWizardPageiSeries extends WizardPage {
	private Text	systemText;
	private Text	userText;
	private Text	passwordText;
	private Text	libText;
	private Text	liblText;
	private Button	createCheck;
	private Button	deleteCheck;

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
		layout.numColumns = 4;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("System");
		systemText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		systemText.setLayoutData(gd);
		systemText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("User");
		userText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		userText.setLayoutData(gd);
		userText.setTextLimit(10);
		userText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("Password");
		passwordText = new Text(container, SWT.BORDER | SWT.SINGLE
				| SWT.PASSWORD);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		passwordText.setLayoutData(gd);
		passwordText.setTextLimit(10);
		passwordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("Generation Library");
		libText = new Text(container, SWT.BORDER | SWT.SINGLE);
		libText.setToolTipText(Messages.DocumentationWizardPageiSeries_libText_toolTipText);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		libText.setLayoutData(gd);
		libText.setTextLimit(10);
		libText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		createCheck = new Button(container, SWT.CHECK);
		createCheck
				.setText(Messages.DocumentationWizardPageiSeries_btnCheckButton_text);
		deleteCheck = new Button(container, SWT.CHECK);
		deleteCheck
				.setText(Messages.DocumentationWizardPageiSeries_btnCheckButton_1_text);
		label = new Label(container, SWT.NULL);
		label.setText(Messages.DocumentationWizardPage_doc_libl);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.verticalSpan = 3;
		gd.horizontalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		label.setLayoutData(gd);
		liblText = new Text(container, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 3;
		liblText.setLayoutData(gd);
		liblText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		initialize();
		dialogChanged();
		setControl(container);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		if (getSystem().isEmpty()) {
			updateStatus("Set the host name or IP");
		}
		else if (getUser().isEmpty()) {
			updateStatus("Set the user of the host");
		}
		else if (getPassword().isEmpty()) {
			updateStatus("Set the password for the host");
		}
		else if (getLib().isEmpty()) {
			updateStatus("Set the library for temporal files");
		}
		else if (getLibl().isEmpty()) {
			updateStatus("Set the library list for analize");
		}
		else {
			updateStatus(null);
		}
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

	/**
	 * @return the systemText
	 */
	public String getSystem() {
		return systemText.getText();
	}

	/**
	 * @return the userText
	 */
	public String getUser() {
		return userText.getText();
	}

	/**
	 * @return the passwordText
	 */
	public String getPassword() {
		return passwordText.getText();
	}

	/**
	 * @return the libText
	 */
	public String getLib() {
		return libText.getText();
	}

	/**
	 * @return the liblText
	 */
	public String getLibl() {
		return liblText.getText();
	}

	/**
	 * @return the createCheck
	 */
	public boolean isCreate() {
		return createCheck.getSelection();
	}

	/**
	 * @return the deleteCheck
	 */
	public boolean isDelete() {
		return deleteCheck.getSelection();
	}
}