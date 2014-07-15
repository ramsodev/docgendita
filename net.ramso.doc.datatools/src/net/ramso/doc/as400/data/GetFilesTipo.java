package net.ramso.doc.as400.data;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;

public class GetFilesTipo extends TableOperations {
	
	public GetFilesTipo() {
		super(RunExtractors.DSPFFILE);
	}

	
	public GetFilesTipoRow[] read(String file) throws CriteriaException,
			TableOperationException {
		Criteria criteria = new Criteria();
		criteria.add("WHFILE", file);
		return (GetFilesTipoRow[]) read(getRecord().getLabels(), criteria, null);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getRecord()
	 */
	@Override
	public IRecordOperations getRecord() {
		return new GetFilesTipoRow();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getArrayRecord(int)
	 */
	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new GetFilesTipoRow[length];
	}
}