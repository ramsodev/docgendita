/**
 * 
 */
package net.ramso.doc.svg.er;

import java.util.ArrayList;

/**
 * @author ramso
 */
public class TableData {
	public static final int ONETOONE = 0;
	public static final int ONETOMANY = 1;
	private String schema;
	private String name;
	private ArrayList<String> primaryKeys = new ArrayList<String>();
	private ArrayList<Object[]> relations = new ArrayList<Object[]>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the primaryKeys
	 */
	public ArrayList<String> getPrimaryKeys() {
		return primaryKeys;
	}

	/**
	 * @return the relations
	 */
	public ArrayList<Object[]> getRelations() {
		return relations;
	}

	/**
	 * @param primaryKey
	 */
	public void addPrimaryKey(String primaryKey) {
		primaryKeys.add(primaryKey);
	}

	public void addRelation(int type, String table) {
		relations.add(new Object[] { type, table });
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TableData) {
			if (getName().equals(((TableData) obj).getName())
					&& getSchema().equals(((TableData) obj).getSchema())) {
				return true;
			}
		}
		return false;
	}

	public void addPrimaryKey(ArrayList<String> primaryKey) {
		this.primaryKeys = primaryKey;

	}

	public void addRelations(ArrayList<Object[]> relations) {
		this.relations = relations;

	}

}
