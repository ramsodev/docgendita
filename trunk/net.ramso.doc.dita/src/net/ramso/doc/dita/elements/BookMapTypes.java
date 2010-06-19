/**
 * 
 */
package net.ramso.doc.dita.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ramso
 */
public enum BookMapTypes implements IDitaTypes {
	BOOKMAP(0, "BOOKMAP", "bookmap"), BOOKTITLE(1, "BOOKTITLE", "booktitle"), FRONTMATTER(
			2, "FRONTMATTER", "frontmatter"), CHAPTER(3, "CHAPTER", "chapter"), PART(
			4, "PART", "part"), APPENDIX(5, "APPENDIX", "appendix"), BACKMATTER(
			6, "BACKMATTER", "backmatter"), MAINBOOKTITLE(7, "MAINBOOKTITLE",
			"mainbooktitle"), BOOKTITLEALT(8, "BOOKTITLEALT", "booktitlealt"), BOOKLISTS(
			9, "BOOKLISTS", "booklists"), TOC(10, "TOC", "toc"), FIGURELIST(11,
			"FIGURELIST", "figurelist"), TABLELIST(12, "TABLELIST", "tablelist"), ABBREVLIST(
			13, "ABBREVLIST", "abbrevlist"), TRADEMARKLIST(14, "TRADEMARKLIST",
			"trademarklist"), BIBLIOLIST(15, "BIBLIOLIST", "bibliolist"), INDEXLIST(
			16, "INDEXLIST", "indexlist"), BOOKLIST(17, "BOOKLIST", "booklist"), GLOSSARYLIST(
			18, "GLOSSARYLIST", "glossarylist"), PREFACE(19, "PREFACE",
			"preface"), ;
	public static final int					BOOKMAP_VALUE		= 0;
	public static final int					BOOKTITLE_VALUE		= 1;
	public static final int					FRONTMATTER_VALUE	= 2;
	public static final int					CHAPTER_VALUE		= 3;
	public static final int					PART_VALUE			= 4;
	public static final int					APPENDIX_VALUE		= 5;
	public static final int					BACKMATTER_VALUE	= 6;
	public static final int					MAINBOOKTITLE_VALUE	= 7;
	public static final int					BOOKTITLEALT_VALUE	= 8;
	public static final int					BOOKLISTS_VALUE		= 9;
	public static final int					TOC_VALUE			= 10;
	public static final int					FIGURELIST_VALUE	= 11;
	public static final int					TABLELIST_VALUE		= 12;
	public static final int					ABBREVLIST_VALUE	= 13;
	public static final int					TRADEMARKLIST_VALUE	= 14;
	public static final int					BIBLIOLIST_VALUE	= 15;
	public static final int					INDEXLIST_VALUE		= 16;
	public static final int					BOOKLIST_VALUE		= 17;
	public static final int					GLOSSARYLIST_VALUE	= 18;
	public static final int					PREFACE_VALUE		= 19;
	private static final BookMapTypes[]		VALUES_ARRAY		= new BookMapTypes[] {
			BOOKMAP, BOOKTITLE, FRONTMATTER, CHAPTER, PART, APPENDIX,
			BACKMATTER, MAINBOOKTITLE, BOOKTITLEALT, BOOKLISTS, TOC,
			FIGURELIST, TABLELIST, ABBREVLIST, TRADEMARKLIST, BIBLIOLIST,
			INDEXLIST, BOOKLIST, GLOSSARYLIST, PREFACE			};
	public static final List<BookMapTypes>	VALUES				= Collections
																		.unmodifiableList(Arrays
																				.asList(VALUES_ARRAY));

	public static BookMapTypes get(int value) {
		switch (value) {
			case BOOKMAP_VALUE:
				return BOOKMAP;
			case BOOKTITLE_VALUE:
				return BOOKTITLE;
			case FRONTMATTER_VALUE:
				return FRONTMATTER;
			case CHAPTER_VALUE:
				return CHAPTER;
			case PART_VALUE:
				return PART;
			case APPENDIX_VALUE:
				return APPENDIX;
			case BACKMATTER_VALUE:
				return BACKMATTER;
			case MAINBOOKTITLE_VALUE:
				return MAINBOOKTITLE;
			case BOOKTITLEALT_VALUE:
				return BOOKTITLEALT;
			case BOOKLISTS_VALUE:
				return BOOKLISTS;
			case TOC_VALUE:
				return TOC;
			case FIGURELIST_VALUE:
				return FIGURELIST;
			case TABLELIST_VALUE:
				return TABLELIST;
			case ABBREVLIST_VALUE:
				return ABBREVLIST;
			case TRADEMARKLIST_VALUE:
				return TRADEMARKLIST;
			case BIBLIOLIST_VALUE:
				return BIBLIOLIST;
			case INDEXLIST_VALUE:
				return INDEXLIST;
			case BOOKLIST_VALUE:
				return BOOKLIST;
			case GLOSSARYLIST_VALUE:
				return GLOSSARYLIST;
			case PREFACE_VALUE:
				return PREFACE;
		}
		return null;
	}

	public static BookMapTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BookMapTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static BookMapTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BookMapTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private final int		value;
	private final String	name;
	private final String	literal;

	private BookMapTypes(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	public String getLiteral() {
		return literal;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return literal;
	}
}
