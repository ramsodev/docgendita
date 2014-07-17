package net.ramso.doc.as400.data;

import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.ITableOperations;
import net.ramso.doc.as400.data.tools.RecordOperations;

public class GetConstrainRow extends RecordOperations {
	private String[] LABELS = { "CSFILE", "CSLIB", "PHCSTN", "PHCSTT",
			"PHCPFN", "PHCPLN", "PHPRKY", "PHFRKY" };

	public GetConstrainRow() {
		super(RunExtractors.DSPFCSTFILE);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_CSRES en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDCST_CSFILE() throws SQLException {
		return (String) getValue(0);
	}

	public String getDSPFDCST_CSLIB() throws SQLException {
		return (String) getValue(1);
	}

	/**
	 * Nombre de la relación
	 */
	public String getDSPFDCST_PHCSTN() throws SQLException {
		return (String) getValue(2);
	}

	/**
	 * Devuelve el valor el tipo de constrain: P - PromayKey R _ Relación
	 */
	public String getDSPFDCST_PHCSTT() throws SQLException {
		return (String) getValue(3);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCPFN en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDCST_PHCPFN() throws SQLException {
		return (String) getValue(4);
	}

	/**
	 * Devuelve el valor de la columna DSPFDCST_PHCPLN en la fila representada
	 * por este objeto.
	 */
	public String getDSPFDCST_PHCPLN() throws SQLException {
		return (String) getValue(5);
	}
	
	/**
	 * Claves padre
	 */
	public String getDSPFDCST_PHPRKY() throws SQLException {
		return (String) getValue(6);
	}
	
	/**
	 * Claves Hijo
	 */
	public String getDSPFDCST_PHFRKY() throws SQLException {
		return (String) getValue(7);
	}

	/**
	 * /* (non-Javadoc)
	 * 
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getTableOperations()
	 */
	@Override
	protected ITableOperations getTableOperations() {
		return new GetConstrain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ramso.doc.as400.data.tools.RecordOperations#getLabels()
	 */
	@Override
	public String[] getLabels() {
		return LABELS;
	}
}