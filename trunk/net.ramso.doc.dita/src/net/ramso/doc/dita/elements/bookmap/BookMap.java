package net.ramso.doc.dita.elements.bookmap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.DitaFactory;
import net.ramso.doc.dita.elements.IDitaTypes;
import net.ramso.doc.dita.elements.MapTypes;
import net.ramso.doc.dita.elements.PrologTypes;
import net.ramso.doc.dita.elements.TopicTypes;
import net.ramso.doc.dita.elements.map.TopicRef;

import org.jdom.Element;

/**
 * @author ramso
 */
public class BookMap extends BaseDitaElement {
	/**
	 * @param type
	 */
	public BookMap() {
		super(BookMapTypes.BOOKMAP);
		setLang(Locale.getDefault().getLanguage() + "_"
				+ Locale.getDefault().getCountry());
		setTranslate(true);
		setCollectionType("sequence");
	}

	/**
	 * @param string
	 */
	public void setCollectionType(String value) {
		setAttribute("collection-type", value);
	}

	/**
	 * @param b
	 */
	public void setTranslate(boolean value) {
		if (value) {
			setAttribute("translate", "yes");
		}
		else {
			setAttribute("translate", "no");
		}
	}

	public void setTitle(String title) {
		setAttribute("title", title);
	}

	public void addChapter(Chapter chapter) {
		addContent(chapter);
	}

	public Chapter getChapter(String id) {
		return (Chapter) getContent(BookMapTypes.CHAPTER, id);
	}

	public void addPart(Part part) {
		addContent(part);
	}

	public TopicRef getPart(String id) {
		return (TopicRef) getContent(BookMapTypes.PART, id);
	}

	public void addAppendix(Appendix appendix) {
		addContent(appendix);
	}

	public Appendix getAppendix(String id) {
		return (Appendix) getContent(BookMapTypes.APPENDIX, id);
	}

	public void setTitle(String title, String alts) {
		BookTitle bookTitle = new BookTitle();
		bookTitle.setTitle(title);
		bookTitle.setTitleAlts(alts);
		addContent(bookTitle);
	}

	public void addFrontMatter(FrontMatter front) {
		addContent(front);
	}

	public void addBackMatter(BackMatter back) {
		addContent(back);
	}

	/**
	 * @param preface
	 */
	public void addPreface(Preface preface) {
		getFrontMatter().setPreface(preface);
	}

	/**
	 * @return
	 */
	private FrontMatter getFrontMatter() {
		return (FrontMatter) getChild(BookMapTypes.FRONTMATTER.getLiteral());
	}

	/**
	 * @param parts
	 */
	public void addPart(List<Part> parts) {
		addContent(parts);
	}
}