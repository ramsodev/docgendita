package net.ramso.doc.as400.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.ramso.doc.as400.Messages;
import net.ramso.doc.dita.Documents.BookMapDocument;
import net.ramso.doc.dita.elements.bookmap.BackMatter;
import net.ramso.doc.dita.elements.bookmap.BookLists;
import net.ramso.doc.dita.elements.bookmap.FrontMatter;
import net.ramso.doc.dita.utils.ResourceUtils;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class ProcessGenerator {
	private String[]	libs;
	private IFolder		folder;
	private String		title;
	private String		des;
	private String		autor;
	private String		lang;

	public ProcessGenerator(String[] libs, IFolder folder) {
		this.libs = libs;
		this.folder = folder;
		title = "Documentación de Aplicaciones Legacy de OS/400";
		des = ""; //$NON-NLS-1$
		autor = ""; //$NON-NLS-1$
		lang = ""; //$NON-NLS-1$
	}

	/**
	 * @return the autor
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * @return the des
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	public void process(IProgressMonitor monitor) {
		String path = folder.getFullPath().toOSString();
		BookMapDocument map = new BookMapDocument();
		map.getMap().setTitle(getTitle(), getDes());
		map.getMap().setID("DBDoc"); //$NON-NLS-1$
		map.getMap().setLang(lang);
		FrontMatter fromMatter = new FrontMatter();
		BookLists bookLists = new BookLists();
		bookLists.setToc(true);
		fromMatter.addBookLists(bookLists);
		map.getMap().addFrontMatter(fromMatter);
		for (String object : libs) {
			try {
				ProcessLib process = new ProcessLib(object, path);
				process.process(monitor);
				map.getMap().addPart(process.getPart());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		BackMatter backMatter = new BackMatter();
		bookLists = new BookLists();
		bookLists.setFigureList(true);
		bookLists.setTableList(true);
		backMatter.addBookLists(bookLists);
		map.getMap().addBackMatter(backMatter);
		try {
			path += File.separator + map.getFileName();
			ResourceUtils.getInstance().saveDitaFileAsResource(
					map.getDocumentContent(), path);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			MessageBox box = new MessageBox(Display.getCurrent()
					.getActiveShell(), SWT.OK | SWT.ICON_INFORMATION);
			box.setMessage("Generation of documentation is finished"); //$NON-NLS-1
			box.open();
		}
	}

	/**
	 * @param autor
	 *            the autor to set
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * @param des
	 *            the des to set
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
