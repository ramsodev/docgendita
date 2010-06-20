package net.ramso.doc.dita.Documents;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	protected String getFileName() {
		return getElementRoot().getID();
	}

	public void save(String path) throws IOException {
		String name = getFileName();
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(this, new FileWriter(path + File.separator + name
				+ EXT));
	}
}
