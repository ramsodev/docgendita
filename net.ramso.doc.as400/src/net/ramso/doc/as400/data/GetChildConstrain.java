package net.ramso.doc.as400.data;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;

public class GetChildConstrain extends TableOperations {

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetChildConstrain() {
		super(RunExtractors.DSPFCSTFILE);
	}

	public GetConstrainRow[] read(String file, String lib, String type)
			throws CriteriaException, TableOperationException {
		Criteria criteria = new Criteria();
		criteria.add("PHCPFN", file);
		criteria.add("PHCPLN", lib);
		criteria.add("PHCSTT", type);
		return (GetConstrainRow[]) read(getRecord().getLabels(), criteria, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getRecord()
	 */
	@Override
	public IRecordOperations getRecord() {
		return new GetConstrainRow();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getArrayRecord(int)
	 */
	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new GetConstrainRow[length];
	}

}