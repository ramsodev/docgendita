package net.ramso.doc.as400.generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.ramso.doc.as400.Messages;
import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.utils.ConnectionManager;
import net.ramso.doc.as400.utils.ConnectionTools;
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
	private ArrayList<String> libs;
	private IFolder folder;
	private String title;
	private String des;
	private String autor;
	private String lang;
	private String system;
	private String user;
	private String password;
	private String lib;
	private boolean create = false;
	private boolean delete = false;

	public ProcessGenerator(String[] libs, IFolder folder) {
		ArrayList<String> temp = new ArrayList<String>();
		for (String lib : libs) {
			if (!lib.trim().isEmpty()) {
				temp.add(lib.trim().toUpperCase());
			}
		}
		this.libs = temp;
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
		String libl = "";
		for (String lib : libs) {
			libl += lib.trim() + " ";
		}
		try {
			ConnectionManager.createConnection(system, user, password, libl);
			RunExtractors.run(lib, create, delete);
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox box = new MessageBox(Display.getCurrent()
					.getActiveShell(), SWT.OK | SWT.ICON_ERROR);
			box.setMessage("Error al acceder al host\n" + e.getLocalizedMessage()); //$NON-NLS-1
			box.open();
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		try {
			// String path = folder.getFullPath().toOSString();
			String path = "";
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
					ProcessLib process = new ProcessLib(object.trim(), path);
					process.process(monitor);
					map.getMap().addPart(process.getPart());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			BackMatter backMatter = new BackMatter();
			bookLists = new BookLists();
			bookLists.setFigureList(true);
			bookLists.setTableList(true);
			backMatter.addBookLists(bookLists);
			map.getMap().addBackMatter(backMatter);

			path += File.separator + map.getFileName();
			ResourceUtils.getInstance().saveDitaFileAsResource(
					map.getDocumentContent(), path);
		} catch (Exception e) {
			MessageBox box = new MessageBox(Display.getCurrent()
					.getActiveShell(), SWT.OK | SWT.ICON_ERROR);
			box.setMessage("Error al generar documentación\n" + e.getLocalizedMessage()); //$NON-NLS-1
			box.open();
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	/**
	 * @param system
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param password
	 */
	public void setLib(String lib) {
		this.lib = lib;
	}

	/**
	 * @param create
	 */
	public void setCreate(boolean create) {
		this.create = create;
	}

	/**
	 * @param delete
	 */
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
