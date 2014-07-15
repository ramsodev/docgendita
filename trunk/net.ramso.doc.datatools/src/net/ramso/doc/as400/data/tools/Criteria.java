/*
 * � 2005 TRENTI S.A.
 * Arequipa 1 3� Planta, 28043, Madrid. Spain
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of TRENTI S.A. ("Confidential Information").
 *
 * Fecha Creaci�n 27-ene-2005
 */
package net.ramso.doc.as400.data.tools;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Esta clase permite la construcci�n de criterios de selecci�n para la
 * realizaci�n de operaciones de acceso y tratamiento de informaci�n de las
 * bases de datos. Equivale a la calusula WHERE en SQL
 * 
 * @author jescudero
 * @viz.diagram Criteria.tpx
 */
public class Criteria {
	/**
	 * Los criterios que componen la clausula WHERE
	 */
	private ArrayList			criterios		= new ArrayList();
	/**
	 * <code>AND</code> operandor and
	 */
	public static final String	AND				= "AND";			//$NON-NLS-1$
	/**
	 * <code>OR</code> operador or
	 */
	public static final String	OR				= "OR";			//$NON-NLS-1$
	/**
	 * <code>EQUAL</code> operador =
	 */
	public static final String	EQUAL			= " = ?";			//$NON-NLS-1$
	/**
	 * <code>NOTEQUAL</code> operador <>
	 */
	public static final String	NOTEQUAL		= " <> ?";			//$NON-NLS-1$
	/**
	 * <code>GREATER</code> operador >
	 */
	public static final String	GREATER			= " > ?";			//$NON-NLS-1$
	/**
	 * <code>LESS</code> operador <
	 */
	public static final String	LESS			= " < ?";			//$NON-NLS-1$
	/**
	 * <code>GREATEREQUAL</code> operador >=
	 */
	public static final String	GREATEREQUAL	= " >= ?";			//$NON-NLS-1$
	/**
	 * <code>LESSEQUAL</code> operador <=
	 */
	public static final String	LESSEQUAL		= " <= ?";			//$NON-NLS-1$
	/**
	 * <code>LIKEANY</code> operador like
	 */
	public static final String	LIKEANY			= "LIKE ?";		//$NON-NLS-1$
	/**
	 * <code>LIKELEFT</code> operador like
	 */
	public static final String	LIKELEFT		= "LIKE ?";		//$NON-NLS-1$
	/**
	 * <code>LIKERIGHT</code> operador like
	 */
	public static final String	LIKERIGHT		= "LIKE ?";		//$NON-NLS-1$

	/**
	 * Constructor por defecto
	 */
	public Criteria() {
		super();
	}

	/**
	 * A�ade un criterio <b>igual a </b> a la clausula
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 * @param value
	 *            el valor de comparacion
	 * @throws CriteriaException
	 */
	public void add(String label, Object value) throws CriteriaException {
		add(label, value, EQUAL, AND);
	}

	/**
	 * A�ade un criterio a la clausula
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 * @param value
	 *            el valor de comparacion
	 * @param operator
	 *            el operador de comparacion
	 * @throws CriteriaException
	 */
	public void add(String label, Object value, String operator)
			throws CriteriaException {
		add(label, value, operator, AND);
	}

	/**
	 * A�ade un criterio <b>igual a </b> a la clausula con un uni�on entre
	 * clausulas de tipo OR
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 * @param value
	 *            el valor de comparacion
	 * @throws CriteriaException
	 */
	public void addOR(String label, Object value) throws CriteriaException {
		add(label, value, EQUAL, OR);
	}

	/**
	 * A�ade un criterio a la clausula con un enlace entre clausulas de tipo OR
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 * @param value
	 *            el valor de comparacion
	 * @param operator
	 *            el operador de comparacion
	 * @throws CriteriaException
	 */
	public void addOR(String label, Object value, String operator)
			throws CriteriaException {
		add(label, value, operator, OR);
	}

	/**
	 * A�ade un criterio a la clausula
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 * @param value
	 *            el valor de comparacion
	 * @param operator
	 *            el operador de comparacion
	 * @param andor
	 *            el concatenador
	 * @throws CriteriaException
	 */
	public void add(String label, Object value, String operator, String andor)
			throws CriteriaException {
		if (operator.equals(LIKEANY)) {
			if (value instanceof String) {
				value = "%" + value + "%"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			else {
				throw new CriteriaException(
						"La Clusula Like solo se aplica a campos alfanumericos"); //$NON-NLS-1$
			}
		}
		else if (operator.equals(LIKELEFT)) {
			if (value instanceof String) {
				value = "%" + value; //$NON-NLS-1$
			}
			else {
				throw new CriteriaException(
						"La Clausula LIKE solo se aplica a campos alfanumericos"); //$NON-NLS-1$
			}
		}
		else if (operator.equals(LIKERIGHT)) {
			if (value instanceof String) {
				value = value + "%"; //$NON-NLS-1$
			}
			else {
				throw new CriteriaException(
						"La Clusula Like solo se aplica a campos alfanumericos"); //$NON-NLS-1$
			}
		}
		Object[] criterio = new Object[4];
		criterio[0] = label;
		criterio[1] = value;
		criterio[2] = operator;
		criterio[3] = andor;
		criterios.add(criterio);
	}

	/**
	 * Crea la clusula WHERE para su uso en una sentencia SQL
	 * 
	 * @return la clausula where
	 */
	public String getClause() {
		if (criterios.size() == 0) {
			return "";
		}
		String sentence = "WHERE ("; //$NON-NLS-1$
		sentence += getFilterClause();
		sentence = sentence.trim() + ")"; //$NON-NLS-1$
		return sentence;
	}

	public String getONClause() {
		if (criterios.size() == 0) {
			return "";
		}
		String sentence = "ON ("; //$NON-NLS-1$
		sentence += getFilterClause();
		sentence = sentence.trim() + ")"; //$NON-NLS-1$
		return sentence;
	}

	public String getFilterClause() {
		String sentence = "";
		Iterator iterator = criterios.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Object[] criterio = (Object[]) iterator.next();
			if (criterio[1] != null) {
				if (i > 0) {
					sentence = (((String) criterio[3]).equals(OR)) ? sentence
							.trim() + ") " + OR + "( " : sentence.trim() + " " + AND + " "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
				i++;
				sentence = sentence + criterio[0] + criterio[2];
			}
		}
		if (i == 0) {
			return "";
		}
		return sentence;
	}

	/**
	 * Recupera los valores de comparacion de cada criterio
	 * 
	 * @return los valores de comparacion
	 */
	public Object[] getValues() {
		Object[] values = new Object[criterios.size()];
		Iterator iterator = criterios.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Object[] val = (Object[]) iterator.next();
			values[i] = val[1];
			i++;
		}
		return values;
	}

	/*
	 * (sin Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getClause();
	}
}