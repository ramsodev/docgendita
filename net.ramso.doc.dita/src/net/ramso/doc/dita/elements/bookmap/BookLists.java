/**
 * 
 */
package net.ramso.doc.dita.elements.bookmap;

import net.ramso.doc.dita.elements.BaseDitaElement;
import net.ramso.doc.dita.elements.BookMapTypes;
import net.ramso.doc.dita.elements.DitaFactory;

/**
 * @author ramso
 */
public class BookLists extends BaseDitaElement {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param type
	 */
	public BookLists() {
		super(BookMapTypes.BOOKLISTS);
	}

	/**
	 * @return
	 */
	private boolean isAbbrevList() {
		return getChild(BookMapTypes.ABBREVLIST.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isBiblioList() {
		return getChild(BookMapTypes.BIBLIOLIST.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isBookList() {
		return getChild(BookMapTypes.BOOKLIST.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isFigureList() {
		return getChild(BookMapTypes.FIGURELIST.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isGlossayList() {
		return getChild(BookMapTypes.GLOSSARYLIST.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isIndexList() {
		return getChild(BookMapTypes.INDEXLIST.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isTableList() {
		return getChild(BookMapTypes.TABLELIST.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isToc() {
		return getChild(BookMapTypes.TOC.getLiteral()) != null;
	}

	/**
	 * @return
	 */
	private boolean isTrademarkList() {
		return getChild(BookMapTypes.TRADEMARKLIST.getLiteral()) != null;
	}

	public void setAbbrevList(boolean set) {
		if (set) {
			if (!isAbbrevList()) {
				addContent(DitaFactory.createElement(BookMapTypes.ABBREVLIST));
			}
		}
		else {
			if (isAbbrevList()) {
				removeChild(BookMapTypes.ABBREVLIST.getLiteral());
			}
		}
	}

	public void setBiblioList(boolean set) {
		if (set) {
			if (!isBiblioList()) {
				addContent(DitaFactory.createElement(BookMapTypes.BIBLIOLIST));
			}
		}
		else {
			if (isBiblioList()) {
				removeChild(BookMapTypes.BIBLIOLIST.getLiteral());
			}
		}
	}

	public void setBookList(boolean set) {
		if (set) {
			if (!isBookList()) {
				addContent(DitaFactory.createElement(BookMapTypes.BOOKLIST));
			}
		}
		else {
			if (isBookList()) {
				removeChild(BookMapTypes.BOOKLIST.getLiteral());
			}
		}
	}

	public void setFigureList(boolean set) {
		if (set) {
			if (!isFigureList()) {
				addContent(DitaFactory.createElement(BookMapTypes.FIGURELIST));
			}
		}
		else {
			if (isFigureList()) {
				removeChild(BookMapTypes.FIGURELIST.getLiteral());
			}
		}
	}

	public void setGlossaryList(boolean set) {
		if (set) {
			if (!isGlossayList()) {
				addContent(DitaFactory.createElement(BookMapTypes.GLOSSARYLIST));
			}
		}
		else {
			if (isGlossayList()) {
				removeChild(BookMapTypes.GLOSSARYLIST.getLiteral());
			}
		}
	}

	public void setIndexList(boolean set) {
		if (set) {
			if (!isIndexList()) {
				addContent(DitaFactory.createElement(BookMapTypes.INDEXLIST));
			}
		}
		else {
			if (isIndexList()) {
				removeChild(BookMapTypes.INDEXLIST.getLiteral());
			}
		}
	}

	public void setTableList(boolean set) {
		if (set) {
			if (!isToc()) {
				addContent(DitaFactory.createElement(BookMapTypes.TABLELIST));
			}
		}
		else {
			if (isTableList()) {
				removeChild(BookMapTypes.TABLELIST.getLiteral());
			}
		}
	}

	public void setToc(boolean set) {
		if (set) {
			if (!isToc()) {
				addContent(DitaFactory.createElement(BookMapTypes.TOC));
			}
		}
		else {
			if (isToc()) {
				removeChild(BookMapTypes.TOC.getLiteral());
			}
		}
	}

	public void setTrademarkList(boolean set) {
		if (set) {
			if (!isTrademarkList()) {
				addContent(DitaFactory
						.createElement(BookMapTypes.TRADEMARKLIST));
			}
		}
		else {
			if (isTrademarkList()) {
				removeChild(BookMapTypes.TRADEMARKLIST.getLiteral());
			}
		}
	}
}
