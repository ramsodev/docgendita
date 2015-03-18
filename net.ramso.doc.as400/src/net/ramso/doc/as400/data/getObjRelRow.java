package net.ramso.doc.as400.data;

import java.sql.SQLException;

import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

public class getObjRelRow extends RecordOperations {
	private static String[]	LABELS	= { "WHFNAM", "WHLNAM", "WHOTYP", "ODOBAT" };

	public getObjRelRow() {
		super("JOIN");
	}

	/**
	 * Devuelve el valor de la columna DSPGMREF_WHFNAM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPGMREF_WHFNAM() throws SQLException {
		return getValue(1-1);
	}

	/**
	 * Devuelve el valor de la columna DSPGMREF_WHLNAM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPGMREF_WHLNAM() throws SQLException {
		return getValue(2-1);
	}

	/**
	 * Devuelve el valor de la columna DSPGMREF_WHOTYP en la fila representada
	 * por este objeto.
	 */
	public Object getDSPGMREF_WHOTYP() throws SQLException {
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
		return new getObjRel();
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