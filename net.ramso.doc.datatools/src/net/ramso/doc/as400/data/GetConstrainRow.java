package net.ramso.doc.as400.data;

import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;


public class GetConstrainRow extends RecordOperations {
	private String[]	LABELS	= { "CSRES", "PHCSTN", "PHCSTL", "PHCSTT",
			"PHCCKP", "PHCSTS", "PHNMKY", "PHKEYL", "PHCPFN", "PHCPLN",
			"PHPRKY", "PHFRKY", "PHDLTR", "PHULTR", "PHTRUN", "PHCKXP" };

	
	public GetConstrainRow() {
		super(RunExtractors.DSPFCSTFILE);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_CSRES en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_CSRES() throws SQLException {
		return getValue(1);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCSTN en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCSTN() throws SQLException {
		return getValue(2);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCSTL en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCSTL() throws SQLException {
		return getValue(3);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCSTT en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCSTT() throws SQLException {
		return getValue(4);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCCKP en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCCKP() throws SQLException {
		return getValue(5);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCSTS en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCSTS() throws SQLException {
		return getValue(6);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHNMKY en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHNMKY() throws SQLException {
		return getValue(7);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHKEYL en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHKEYL() throws SQLException {
		return getValue(8);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCPFN en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCPFN() throws SQLException {
		return getValue(9);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCPLN en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCPLN() throws SQLException {
		return getValue(10);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHPRKY en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHPRKY() throws SQLException {
		return getValue(11);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHFRKY en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHFRKY() throws SQLException {
		return getValue(12);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHDLTR en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHDLTR() throws SQLException {
		return getValue(13);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHULTR en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHULTR() throws SQLException {
		return getValue(14);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHTRUN en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHTRUN() throws SQLException {
		return getValue(15);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCKXP en la fila representada
	 * por este objeto.
	 */
	public Object getDSPFDCST_PHCKXP() throws SQLException {
		return getValue(16);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		return new GetConstrain();
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