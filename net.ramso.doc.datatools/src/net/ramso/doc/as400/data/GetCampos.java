package net.ramso.doc.as400.data;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;


public class GetCampos extends TableOperations {

	
	public GetCampos() {
		super(RunExtractors.DSPFFILE);
	}

	
	public IRecordOperations[] read(String file) throws CriteriaException,
			TableOperationException {
		Criteria criteria = new Criteria();
		criteria.add("WHFILE", file);
		return read(getRecord().getLabels(), criteria, null);
		
	}

	@Override
	public IRecordOperations getRecord() {
		return new GetCamposRow();
	}

	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new GetCamposRow[length];
	}

}