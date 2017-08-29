/**
 * 
 */
package net.ramso.doc.diagrams.db;

import java.util.ArrayList;

/**
 * @author ramso
 */
public class TableData {
	public static final int ONETOONE = 0;
	public static final int ONETOMANY = 1;
	public static final int TABLE = 0;
	public static final int VIEW = 1;
	public static final int JOIN = 2;
	public static final int PK = 0;
	public static final int UNIQUE = 1;
	public static final int ORDER = 2;
	public static final int ON = 3;
	public static final int RELATION = 2;
	public static final int FILTER = 4;
	private String schema;
	private String name;
	private ArrayList<Object[]> primaryKeys = new ArrayList<Object[]>();
	private ArrayList<Object[]> relations = new ArrayList<Object[]>();
	private int type = TABLE;

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
	public ArrayList<Object[]> getPrimaryKeys() {
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
		addPrimaryKey(primaryKey,PK);
	}
	public void addPrimaryKey(String primaryKey, int type) {
		primaryKeys.add( new Object[]{primaryKey,type});
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
			if (getName().equals(((TableData) obj).getName()) && getSchema().equals(((TableData) obj).getSchema())) {
				return true;
			}
		}
		return false;
	}

	public void addPrimaryKey(ArrayList<Object[]> primaryKey) {
		this.primaryKeys = primaryKey;

	}

	public void addRelations(ArrayList<Object[]> relations) {
		this.relations = relations;

	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
