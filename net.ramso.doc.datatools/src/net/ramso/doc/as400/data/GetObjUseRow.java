package net.ramso.doc.as400.data;

import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

public class GetObjUseRow extends RecordOperations {
	private static String[]	LABELS	= { "WHPNAM", "WHLIB", "WHTEXT" };

	public GetObjUseRow() {
		super(RunExtractors.DSPPGMREFFILE);
	}

	/**
	 * Devuelve el valor de la columna DSPPGMREF_WHPNAM en la fila representada
	 * por este objeto.
	 */
	public Object getDSPPGMREF_WHPNAM() throws SQLException {
		return getValue(1);
	}

	/**
	 * Devuelve el valor de la columna DSPPGMREF_WHLIB en la fila representada
	 * por este objeto.
	 */
	public Object getDSPPGMREF_WHLIB() throws SQLException {
		return getValue(2);
	}

	/**
	 * Devuelve el valor de la columna DSPPGMREF_WHTEXT en la fila representada
	 * por este objeto.
	 */
	public Object getDSPPGMREF_WHTEXT() throws SQLException {
		return getValue(3);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		return new GetObjUse();
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