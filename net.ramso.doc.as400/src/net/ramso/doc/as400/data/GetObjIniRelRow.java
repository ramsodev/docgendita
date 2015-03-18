package net.ramso.doc.as400.data;

import java.sql.SQLException;

import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

public class GetObjIniRelRow extends RecordOperations {
	private static String[]	LABELS	= { "WHLIB", "WHPNAM", "ODOBTP", "ODOBAT" };
	

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetObjIniRelRow() {
		super("JOIN");
	}

	/**
	 * Devuelve el valor de la columna DSPGMREF_WHLIB en la fila representada
	 * por este objeto.
	 */
	public Object getDSPGMREF_WHLIB() throws SQLException {
		return getValue(1-1);
	}

	/**
	 * Devuelve el valor de la columna DSPGMREF_WHPNAM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPGMREF_WHPNAM() throws SQLException {
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


	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		return new GetObjIniRel();
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