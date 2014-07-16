/**
 * 
 */
package net.ramso.doc.as400.data.tools;

import java.util.ArrayList;

/**
 * @author ramso
 *
 */
public class Join {
	public static final String LEFT = "LEFT OUTER JOIN";
	public static final String INNER = "INNER JOIN";
	
	public static final String RIGHT = "RIGHT OUTER JOIN";
	public static final String FULL = "FULL OUTER JOIN";
	private Criteria criteria;
	private String leftTable;
	private String rightTable;
	private String joinType;
	
	/**
	 * 
	 */
	public Join() {
	}

	public void createJoin(String leftTable, String rightTable, String joinType, Criteria criteria){
		setLeftTable(leftTable);
		setRightTable(rightTable);
		setJoinType(joinType);
		setCriteria(criteria);
	}
	
	/**
	 * @return the criteria
	 */
	public Criteria getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria the criteria to set
	 */
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the leftTable
	 */
	public String getLeftTable() {
		return leftTable;
	}

	/**
	 * @param leftTable the leftTable to set
	 */
	public void setLeftTable(String leftTable) {
		this.leftTable = leftTable;
	}

	/**
	 * @return the rightTable
	 */
	public String getRightTable() {
		return rightTable;
	}

	/**
	 * @param rightTable the rightTable to set
	 */
	public void setRightTable(String rightTable) {
		this.rightTable = rightTable;
	}

	/**
	 * @return the joinType
	 */
	public String getJoinType() {
		return joinType;
	}

	/**
	 * @param joinType the joinType to set
	 */
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String sentence = getLeftTable() + " " + getJoinType() + " " +getRightTable() +" " +  getCriteria().getONClause(); 
		return sentence;
	}
	
	
}
