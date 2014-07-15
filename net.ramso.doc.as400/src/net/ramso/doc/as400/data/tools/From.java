/**
 * 
 */
package net.ramso.doc.as400.data.tools;

/**
 * @author ramso
 */
public class From {
	private String	table	= "";
	private Join	join;

	/**
	 * 
	 */
	public From(String table) {
		this.table = table;
	}

	/**
	 * 
	 */
	public From(Join join) {
		this.join = join;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (join != null) {
			return join.toString();
		}
		return table;
	}
}
