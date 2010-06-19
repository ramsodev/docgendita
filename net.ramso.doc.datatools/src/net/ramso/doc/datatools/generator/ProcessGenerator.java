package net.ramso.doc.datatools.generator;

import java.io.IOException;
import java.util.List;

import net.ramso.doc.dita.Documents.BookMapDocument;
import net.ramso.doc.dita.elements.bookmap.BackMatter;
import net.ramso.doc.dita.elements.bookmap.BookLists;
import net.ramso.doc.dita.elements.bookmap.Chapter;
import net.ramso.doc.dita.elements.bookmap.FrontMatter;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.tables.PersistentTable;
import org.eclipse.datatools.modelbase.sql.tables.ViewTable;

public class ProcessGenerator {
	private List<SQLObject>	objects;
	private IFolder			folder;
	private String title;
	private String des;
	private String autor;
	private String lang;

	public ProcessGenerator(List<SQLObject> objects, IFolder folder) {
		this.objects = objects;
		this.folder = folder;
		this.title= "Documentación de base de datos";
		this.des="";
		this.autor="";
		this.lang="";
	}

	public void process(IProgressMonitor monitor) {
		String path = folder.getLocation().toOSString();
		BookMapDocument map = new BookMapDocument();
		map.getMap().setTitle(getTitle(), getDes());
		map.getMap().setID("DBDoc");
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
				}else if (object instanceof ViewTable) {
					ProcessView process = new ProcessView(
							(ViewTable) object, path);
					process.process(monitor);
					map.getMap().addChapter(process.getChapter());
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		BackMatter backMatter =new BackMatter();
		bookLists = new BookLists();
		bookLists.setFigureList(true);
		bookLists.setTableList(true);
		backMatter.addBookLists(bookLists);
		map.getMap().addBackMatter(backMatter);
		try {
			map.save(path);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the des
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @param des the des to set
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * @return the autor
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * @param autor the autor to set
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
}
