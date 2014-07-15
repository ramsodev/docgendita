package net.ramso.doc.as400.data;

import java.math.BigDecimal;
import java.sql.SQLException;

import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

/**
 * Esta clase representa una fila espec�fica de un conjunto de resultados
 * contenida en DBSelect. Generated: 13-jul-03 14:51:11
 */
public class GetKeyLfRow extends RecordOperations {
	private static String[]	LABELS	= { "APFATR", "APKEYF", "APKSEQ", "APKEYN",
			"APBOF", "APFILE", "APNKYF", "ODOBTX" };

	public GetKeyLfRow() {
		super("JOIN");
	}

	/**
	 * Devuelve el valor de la columna DSPFDACC_APFATR en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDACC_APFATR() throws SQLException {
		return (String) getValue(1);
	}

	/**
	 * Devuelve el valor de la columna DSPFDACC_APKEYF en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDACC_APKEYF() throws SQLException {
		return (String) getValue(2);
	}

	/**
	 * Devuelve el valor de la columna DSPFDACC_APKSEQ en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDACC_APKSEQ() throws SQLException {
		return (String) getValue(3);
	}

	/**
	 * Devuelve el valor de la columna DSPFDACC_APKEYN en la fila representada
	 * por este objeto.
	 */
	public BigDecimal getDSPFDACC_APKEYN() throws SQLException {
		return (BigDecimal) getValue(4);
	}

	/**
	 * Devuelve el valor de la columna DSPFDACC_APBOF en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDACC_APBOF() throws SQLException {
		return (String) getValue(5);
	}

	/**
	 * Devuelve el valor de la columna DSPFDACC_APFILE en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDACC_APFILE() throws SQLException {
		return (String) getValue(6);
	}

	/**
	 * Devuelve el valor de la columna DSPFDACC_APNKYF en la fila representada
	 * por este objeto.
	 */
	public BigDecimal getDSPFDACC_APNKYF() throws SQLException {
		return (BigDecimal) getValue(7);
	}

	/**
	 * Devuelve el valor de la columna DSPOBJD_ODOBTX en la fila representada
	 * por este objeto.
	 */
	public String getDSPOBJD_ODOBTX() throws SQLException {
		return (String) getValue(8);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		
		return new GetKeyLf();
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