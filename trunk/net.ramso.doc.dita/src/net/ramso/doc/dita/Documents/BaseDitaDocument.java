package net.ramso.doc.dita.Documents;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.IDitaTypes;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class BaseDitaDocument extends Document {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected String			EXT					= ".dita";

	public BaseDitaDocument(IDitaTypes type, String publicId, String systemId) {
		super(DitaFactory.createElement(type), DitaFactory.createDocType(type,
				publicId, systemId));
	}

	public BaseDitaElement getElementRoot() {
		return (BaseDitaElement) getRootElement();
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return getElementRoot().getID() + EXT;
	}

	public void save(String path) throws IOException {
		save(new FileWriter(path + File.separator + getFileName()));
	}

	public StringWriter getDocumentContent() throws IOException {
		StringWriter out = new StringWriter();
		save(out);
		return out;
	}

	/**
	 * @param out
	 * @throws IOException
	 */
	public void save(Writer out) throws IOException {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(this, out);
	}
}
