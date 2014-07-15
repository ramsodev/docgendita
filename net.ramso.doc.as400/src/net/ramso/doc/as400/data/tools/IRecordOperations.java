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

/**
 * Este interface define las operaciones minimas necesarias para el tratamiento
 * de una fila de una tabla.
 * 
 * @author jescudero
 * @viz.diagram IRecordOperations.tpx
 */
public interface IRecordOperations {
	/**
	 * Recupera los valores de todas las columnas de la tabla.
	 * 
	 * @return los valores
	 */
	Object[] getValues();

	/**
	 * Recupera las etiquetas con los nombre de las columnas de la fila.
	 * 
	 * @return los nombres de columna
	 */
	String[] getLabels();

	/**
	 * Carga los valores de las columnas de esta fila
	 * 
	 * @param values
	 *            los valores
	 */
	void setValues(Object[] values);

	
	

	
}