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
 * Este interface contiene las operaciones necesarias para trabajr con una
 * tabla.
 * 
 * @author jescudero
 * @viz.diagram ITableOperations.tpx
 */
public interface ITableOperations {

	/**
	 * Recupera las filas de la base de datos selecionada mendiante el criterio
	 * y el orden indicado en la base de datos por defecto.
	 * 
	 * @param criteria
	 *            el criterio de selecci�n, null si no desa ninguno
	 * @param order
	 *            el orden de salida, null si no desea ninguno
	 * @return las filas recuperadas
	 * @throws TableOperationException
	 */
	IRecordOperations[] read(Criteria criteria, Order order)
			throws TableOperationException;

	IRecordOperations[] read(String[] labels, Criteria criteria, Order order)
			throws TableOperationException;

	/**
	 * Recupera la clase asociada a ITableOperation de tipo IRecordOperation
	 */
	IRecordOperations getRecord();

	/**
	 * Recupera un array de la clase asociada a ITableOperation de tipo
	 * IRecordOperation
	 * 
	 * @param length
	 *            la longitud del array
	 */
	IRecordOperations[] getArrayRecord(int length);

}