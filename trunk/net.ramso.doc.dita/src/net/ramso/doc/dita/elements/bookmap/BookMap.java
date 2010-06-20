package net.ramso.doc.dita.elements.bookmap;

import java.util.List;
import java.util.Locale;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.map.TopicRef;

/**
 * @author ramso
 */
public class BookMap extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

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

	public void addAppendix(Appendix appendix) {
		addContent(appendix);
	}

	public void addBackMatter(BackMatter back) {
		addContent(back);
	}

	public void addChapter(Chapter chapter) {
		addContent(chapter);
	}

	public void addFrontMatter(FrontMatter front) {
		addContent(front);
	}

	/**
	 * @param parts
	 */
	public void addPart(List<Part> parts) {
		addContent(parts);
	}

	public void addPart(Part part) {
		addContent(part);
	}

	/**
	 * @param preface
	 */
	public void addPreface(Preface preface) {
		getFrontMatter().setPreface(preface);
	}

	public Appendix getAppendix(String id) {
		return (Appendix) getContent(BookMapTypes.APPENDIX, id);
	}

	public Chapter getChapter(String id) {
		return (Chapter) getContent(BookMapTypes.CHAPTER, id);
	}

	/**
	 * @return
	 */
	private FrontMatter getFrontMatter() {
		return (FrontMatter) getChild(BookMapTypes.FRONTMATTER.getLiteral());
	}

	public TopicRef getPart(String id) {
		return (TopicRef) getContent(BookMapTypes.PART, id);
	}

	/**
	 * @param string
	 */
	public void setCollectionType(String value) {
		setAttribute("collection-type", value);
	}

	public void setTitle(String title) {
		setAttribute("title", title);
	}

	public void setTitle(String title, String alts) {
		BookTitle bookTitle = new BookTitle();
		bookTitle.setTitle(title);
		bookTitle.setTitleAlts(alts);
		addContent(bookTitle);
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
}