package net.ramso.doc.as400.data;

import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

/**
 * Esta clase representa una fila espec�fica de un conjunto de resultados
 * contenida en DBSelect. Generated: 10-jul-03 14:28:52
 */
public class GetPgmObjRow extends RecordOperations {
	private static String[]	LABELS	= { "ODLBNM", "ODOBNM", "ODOBTP", "ODOBAT",
			"ODOBSZ", "ODOBTX", "ODCDAT", "ODCTIM", "ODOBOW", "ODSRCF",
			"ODSRCL", "ODSRCM", "ODCMNM", "ODCMVR" };

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetPgmObjRow() {
		super(RunExtractors.DSPOBJDFILE);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODLBNM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODLBNM() throws SQLException {
		return getValue(1-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODOBNM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODOBNM() throws SQLException {
		return getValue(2-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODOBTP en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODOBTP() throws SQLException {
		return getValue(3-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODOBAT en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODOBAT() throws SQLException {
		return getValue(4-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODOBSZ en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODOBSZ() throws SQLException {
		return getValue(5-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODOBTX en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODOBTX() throws SQLException {
		return getValue(6-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODCDAT en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODCDAT() throws SQLException {
		return getValue(7-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODCTIM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODCTIM() throws SQLException {
		return getValue(8-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODOBOW en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODOBOW() throws SQLException {
		return getValue(9-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODSRCF en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODSRCF() throws SQLException {
		return getValue(10-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODSRCL en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODSRCL() throws SQLException {
		return getValue(11-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODSRCM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODSRCM() throws SQLException {
		return getValue(12-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODCMNM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODCMNM() throws SQLException {
		return getValue(13-1);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODCMVR en la fila representada
	 * por este objeto.
	 */
	public Object getDSPOBJD_ODCMVR() throws SQLException {
		return getValue(14-1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		return new GetPgmObj();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getLabels()
	 */
	@Override
	public String[] getLabels() {
		return LABELS;
	}
}