/**
 * 
 */
package net.ramso.doc.xsd;

import org.eclipse.osgi.util.NLS;

/**
 * @author ramso
 */
public class Messages extends NLS {
	private static final String	BUNDLE_NAME	= "net.ramso.doc.xsd.messages";	//$NON-NLS-1$
	public static String		ProcessGenerator_title;
	public static String		DocumentationWizardPage_browse;
	public static String		DocumentationWizardPage_doc_autor;
	public static String		DocumentationWizardPage_doc_description;
	public static String		DocumentationWizardPage_doc_lang;
	public static String		DocumentationWizardPage_doc_title;
	public static String		DocumentationWizardPage_error_no_autor;
	public static String		DocumentationWizardPage_error_no_description;
	public static String		DocumentationWizardPage_error_no_folder;
	public static String		DocumentationWizardPage_error_no_folder_type;
	public static String		DocumentationWizardPage_error_no_title;
	public static String		DocumentationWizardPage_error_no_write;
	public static String		DocumentationWizardPage_folder;
	public static String		DocumentationWizardPage_select_folder;
	public static String		DocumentationWizardPage_wizard_description;
	public static String		DocumentationWizardPage_wizard_title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
