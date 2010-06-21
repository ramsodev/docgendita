package net.ramso.doc.datatools.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.datatools.utils.ResourceUtils;
import net.ramso.doc.dita.Documents.BookMapDocument;
import net.ramso.doc.dita.elements.bookmap.BackMatter;
import net.ramso.doc.dita.elements.bookmap.BookLists;
import net.ramso.doc.dita.elements.bookmap.FrontMatter;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.modelbase.sql.datatypes.UserDefinedType;
import org.eclipse.datatools.modelbase.sql.routines.Procedure;
import org.eclipse.datatools.modelbase.sql.routines.UserDefinedFunction;
import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.schema.Sequence;
import org.eclipse.datatools.modelbase.sql.tables.PersistentTable;
import org.eclipse.datatools.modelbase.sql.tables.ViewTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class ProcessGenerator {
	private List<SQLObject>	objects;
	private IFolder			folder;
	private String			title;
	private String			des;
	private String			autor;
	private String			lang;

	public ProcessGenerator(List<SQLObject> objects, IFolder folder) {
		this.objects = objects;
		this.folder = folder;
		title = Messages.ProcessGenerator_title;
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
		for (SQLObject object : objects) {
			try {
				if (object instanceof Database) {
					ProcessDatabase process = new ProcessDatabase(
							(Database) object, path);
					process.process(monitor);
					map.getMap().addPreface(process.getPreface());
					map.getMap().addPart(process.getParts());
				}
				else if (object instanceof Catalog) {
					ProcessCatalog process = new ProcessCatalog(
							(Catalog) object, path, true);
					process.process(monitor);
					map.getMap().addPreface(process.getPreface());
					map.getMap().addPart(process.getParts());
				}
				else if (object instanceof Schema) {
					ProcessSchema process = new ProcessSchema((Schema) object,
							path);
					process.process(monitor);
					map.getMap().addPart(process.getPart());
				}
				else if (object instanceof PersistentTable) {
					ProcessTable process = new ProcessTable(
							(PersistentTable) object, path);
					process.process(monitor);
					map.getMap().addChapter(process.getChapter());
				}
				else if (object instanceof ViewTable) {
					ProcessView process = new ProcessView((ViewTable) object,
							path);
					process.process(monitor);
					map.getMap().addChapter(process.getChapter());
				}
				else if (object instanceof Procedure) {
					ProcessProcedure process = new ProcessProcedure(
							(Procedure) object, path);
					process.process(monitor);
					map.getMap().addChapter(process.getChapter());
				}
				else if (object instanceof UserDefinedFunction) {
					ProcessUDF process = new ProcessUDF(
							(UserDefinedFunction) object, path);
					process.process(monitor);
					map.getMap().addChapter(process.getChapter());
				}
				else if (object instanceof UserDefinedType) {
					ProcessUDT process = new ProcessUDT(
							(UserDefinedType) object, path);
					process.process(monitor);
					map.getMap().addChapter(process.getChapter());
				}
				else if (object instanceof Sequence) {
					ProcessSequence process = new ProcessSequence(
							(Sequence) object, path);
					process.process(monitor);
					map.getMap().addChapter(process.getChapter());
				}
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
