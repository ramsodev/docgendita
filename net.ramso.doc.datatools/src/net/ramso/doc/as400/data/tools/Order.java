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
 * Esta clase permite indicar un orden para los resultados de una operaci�n de
 * lectura de la tabla. Equivale a la clausula ORDER de SQL
 * 
 * @author jescudero
 * @viz.diagram Order.tpx
 */
public class Order {
	/**
	 * Lista con los criterios de ordenaci�n
	 */
	private ArrayList criterios = new ArrayList();

	/**
	 * <code>ASC</code> Ordenaci�n ascendente
	 */
	public static final String ASC = "ASC"; //$NON-NLS-1$

	/**
	 * <code>DESC</code> Ordenaci�n descendente
	 */
	public static final String DESC = "DESC"; //$NON-NLS-1$

	/**
	 * Constructor por defecto
	 */
	public Order() {
	}

	/**
	 * A�ade un criterio de orden de tipo ascendente
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 */
	public void add(String label) {
		add(label, ASC);
	}

	/**
	 * A�ade un criterio de orden de tipo descendente
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 */
	public void addDescent(String label) {
		add(label, DESC);
	}

	/**
	 * A�ade un criterio de ordenaci�n
	 * 
	 * @param label
	 *            la etiqueta de la columna
	 * @param type
	 *            ASC o DSC
	 */
	public void add(String label, String type) {
		String[] order = { label, type };
		criterios.add(order);
	}

	/**
	 * Recupera la clausula ORDER para ser usada en una sentencia SQL
	 * 
	 * @return la clausula ORDER
	 */
	public String getClause() {
		if (criterios.size() == 0) {
			return null;
		}
		String sentence = "ORDER BY "; //$NON-NLS-1$
		Iterator iterator = criterios.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			String[] element = (String[]) iterator.next();
			if (i > 0) {
				sentence = sentence + ","; //$NON-NLS-1$
			}
			sentence = sentence.trim() + " " + element[0] + " " + element[1]; //$NON-NLS-1$ //$NON-NLS-2$
			i++;
		}
		return sentence;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getClause();
	}
}