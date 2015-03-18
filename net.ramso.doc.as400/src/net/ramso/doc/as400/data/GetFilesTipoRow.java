package net.ramso.doc.as400.data;

import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

public class GetFilesTipoRow extends RecordOperations {
	private static String[]	LABELS	= { "ATFILE", "ATLIB",
			"ATFILA", "ATFATR", "ATTXT", "ATFCDT",
			"ATFCTM"		};

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetFilesTipoRow() {
		super(RunExtractors.DSPFFILE);
	}

	/**
	 * Devuelve el valor de la columna DSPFD_ATFILE en la fila representada por
	 * este objeto.
	 */
	public String getDSPFD_ATFILE() throws SQLException {
		return (String) getValue(1-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFD_ATLIB en la fila representada por
	 * este objeto.
	 */
	public String getDSPFD_ATLIB() throws SQLException {
		return (String) getValue(2-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFD_ATFILA en la fila representada por
	 * este objeto.
	 */
	public String getDSPFD_ATFILA() throws SQLException {
		return (String) getValue(3-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFD_ATFATR en la fila representada por
	 * este objeto.
	 */
	public String getDSPFD_ATFATR() throws SQLException {
		return (String) getValue(4-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFD_ATTXT en la fila representada por
	 * este objeto.
	 */
	public String getDSPFD_ATTXT() throws SQLException {
		return (String) getValue(5-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFD_ATFCDT en la fila representada por
	 * este objeto.
	 */
	public String getDSPFD_ATFCDT() throws SQLException {
		return (String) getValue(6-1);
	}

	/**
	 * Devuelve el valor de la columna DSPFD_ATFCTM en la fila representada por
	 * este objeto.
	 */
	public String getDSPFD_ATFCTM() throws SQLException {
		return (String) getValue(7-1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		return new GetFilesTipo();
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