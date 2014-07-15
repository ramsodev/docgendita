/*
 * � 2005 TRENTI S.A.
 * Arequipa 1 3� Planta, 28043, Madrid. Spain
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of TRENTI S.A. ("Confidential Information").
 *
 * Fecha Creaci�n 23-ene-2005
 */
package net.ramso.doc.as400.data.tools;


/**
 * Esta clase representa de forma abstracta una fila de una tabla.
 * 
 * @author jescudero
 * @viz.diagram RecordOperations.tpx
 */
public abstract class RecordOperations implements IRecordOperations {
	protected Object[] values;
	/**
	 * <code>tableName</code> el nombre de la tabla sobre la que trabajar. Se
	 * puede incluir el nombre del esquema de base de datos si es necesario.
	 */
	protected String tableName = null;

	/**
	 * La fila de la table padre en una relaci�n [1..N]
	 */
	protected IRecordOperations parent = null;

	/**
	 * Construlle y inicializa la fila
	 * 
	 * @param tablename
	 *            el nombre de la tabla
	 */
	public RecordOperations(String tablename) {
		this.tableName = tablename;
	}

	/**
	 * Retorna la clase asociada que gestiona la tablas
	 * 
	 * @return la clase que implementa el interface ITableOperation asociada a
	 *         esta
	 */
	protected abstract ITableOperations getTableOperations();

	/*
	 * (sin Javadoc)
	 * 
	 * @see com.trenti.utils.database.IRecordOperations#getLabels()
	 */
	public abstract String[] getLabels();

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public Object getValue(int idx) {
		return values[idx];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer value = new StringBuffer();
		Object[] values = getValues();
		String[] labels = getLabels();
		for (int i = 0; i < values.length; i++) {
			value.append(labels[i] + " = " + values[i].toString() + ", "); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return value.toString();
	}

}