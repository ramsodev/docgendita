package net.ramso.doc.as400.data;
import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;


public class GetFilRelRow extends RecordOperations {
	private static String[] LABELS = {
		"WHREFI"
				
				, "WHRELI"
				
				, "WHTYPE"
				
				, "WHCTLN"
				
				, "WHCSTN"
	};

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetFilRelRow() {
		super(RunExtractors.DSPDBRFILE);
	}

	/**
	 * Devuelve el valor de la columna DSPDBR_WHREFI en la fila representada por este objeto.
	 */
	public Object getDSPDBR_WHREFI() throws SQLException {
		return getValue( 1);
	}

	/**
	 * Devuelve el valor de la columna DSPDBR_WHRELI en la fila representada por este objeto.
	 */
	public Object getDSPDBR_WHRELI() throws SQLException {
		return getValue( 2);
	}

	/**
	 * Devuelve el valor de la columna DSPDBR_WHTYPE en la fila representada por este objeto.
	 */
	public Object getDSPDBR_WHTYPE() throws SQLException {
		return getValue( 3);
	}

	/**
	 * Devuelve el valor de la columna DSPDBR_WHCTLN en la fila representada por este objeto.
	 */
	public Object getDSPDBR_WHCTLN() throws SQLException {
		return getValue( 4);
	}

	/**
	 * Devuelve el valor de la columna DSPDBR_WHCSTN en la fila representada por este objeto.
	 */
	public Object getDSPDBR_WHCSTN() throws SQLException {
		return getValue( 5);
	}

	/* (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		return new GetFilRel();
	}

	/* (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getLabels()
	 */
	@Override
	public String[] getLabels() {
		return LABELS;
	}

	
}