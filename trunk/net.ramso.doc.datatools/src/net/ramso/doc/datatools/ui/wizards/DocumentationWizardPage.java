package net.ramso.doc.datatools.ui.wizards;

import java.util.Arrays;
import java.util.Locale;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
public class DocumentationWizardPage extends WizardPage {
	private Text		containerText;
	private ISelection	selection;
	private Text		titleText;
	private Text		desText;
	private Text		autorText;
	private Combo		langCombo;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public DocumentationWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Generar documentación de base de datos");
		setDescription("Este Asistente crea la documentación de "
				+ "una base de datos en formato dita");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Destino:");
		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		Button button = new Button(container, SWT.PUSH);
		button.setText("Seleccionar");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&Titulo:");
		titleText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		titleText.setLayoutData(gd);
		titleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&Descripción:");
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.verticalAlignment=SWT.CENTER;
		gd.verticalSpan=3;
		label.setLayoutData(gd);
		desText = new Text(container, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.verticalSpan=3;
		desText.setLayoutData(gd);
		desText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&Autor:");
		autorText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		autorText.setLayoutData(gd);
		autorText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&Idioma:");
		langCombo = new Combo(container, SWT.SIMPLE | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.verticalSpan = 2;
		langCombo.setLayoutData(gd);
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		autorText.setText(System.getProperties().getProperty("user.name"));
		String lang = Locale.getDefault().getLanguage() + "_"
				+ Locale.getDefault().getCountry();
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			langCombo.add(locale.getLanguage() + "_" + locale.getCountry());
		}
		langCombo.select(getLang(lang, langCombo.getItems()));
	}

	/**
	 * @param lang
	 * @param items
	 * @return
	 */
	private int getLang(String lang, String[] items) {
		int i = 0;
		for (String item : items) {
			if (item.equalsIgnoreCase(lang)) {
				return i;
			}
			i++;
		}
		return 0;
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Seleccion el destino de la docuemntación");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		if (getContainerName().length() == 0) {
			updateStatus("Debe especificar un destino de la documentación");
			return;
		}
		if (container == null || (container.getType() & IResource.FOLDER) == 0) {
			updateStatus("El destino de la documentación debe existir y ser una Carpeta");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("El destino debe de permir la escritura");
			return;
		}
		if(getTitle().length() == 0){
			updateStatus("Debe especificar un titulo de la documentación");
			return;
		}
		if(getDescription().length() == 0){
			updateStatus("Debe especificar una pequeña descripción de la documentación");
			return;
		}
		if(getAutor().length() == 0){
			updateStatus("Debe especificar un autor de la documentación");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getTitle() {
		return titleText.getText();
	}

	public String getDescription() {
		return desText.getText();
	}

	public String getAutor() {
		return autorText.getText();
	}

	public String getLang() {
		return langCombo.getText();
	}
}