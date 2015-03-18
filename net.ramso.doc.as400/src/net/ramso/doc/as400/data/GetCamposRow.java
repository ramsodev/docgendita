package net.ramso.doc.as400.data;

import java.math.BigDecimal;
import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

/**
 * Esta clase representa una fila espec�fica de un conjunto de resultados
 * contenida en DBSelect. Generated: 12-jul-03 18:33:54
 */

public class GetCamposRow extends RecordOperations {
	public static String[] LABELS = { "WHFLDI", "WHFLDD", "WHFLDP", "WHFTXT",
			"WHFLDB", "WHCHD1", "WHCHD2", "WHCHD3", "WHFLDT", "WHNAME",
			"WHTEXT" };

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetCamposRow() {
		super(RunExtractors.DSPFFDFILE);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFLDI en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHFLDI() throws SQLException {
		return (String) getValue(1-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFLDD en la fila representada por
	 * este objeto.
	 */
	public BigDecimal getDSPFFD_WHFLDD() throws SQLException {
		return (BigDecimal) getValue(2-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFLDP en la fila representada por
	 * este objeto.
	 */
	public BigDecimal getDSPFFD_WHFLDP() throws SQLException {
		return (BigDecimal) getValue(3-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFTXT en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHFTXT() throws SQLException {
		return (String) getValue(4-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFLDB en la fila representada por
	 * este objeto.
	 */
	public BigDecimal getDSPFFD_WHFLDB() throws SQLException {
		return (BigDecimal) getValue(5-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHCHD1 en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHCHD1() throws SQLException {
		return (String) getValue(6-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHCHD2 en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHCHD2() throws SQLException {
		return (String) getValue(7-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHCHD3 en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHCHD3() throws SQLException {
		return (String) getValue(8-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFLDT en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHFLDT() throws SQLException {
		return (String) getValue(9-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFLDT en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHNAME() throws SQLException {
		return (String) getValue(10-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFFD_WHFLDT en la fila representada por
	 * este objeto.
	 */
	public String getDSPFFD_WHTEXT() throws SQLException {
		return (String) getValue(11-1);
	}

	@Override
	protected ITableOperations getTableOperations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getLabels() {
		return LABELS;
	}

}