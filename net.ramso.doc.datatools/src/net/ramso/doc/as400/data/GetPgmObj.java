package net.ramso.doc.as400.data;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;

public class GetPgmObj extends TableOperations {
	public GetPgmObj() {
		super(RunExtractors.DSPOBJDFILE);
	}

	protected void read() throws CriteriaException, TableOperationException {
		Criteria criteria = new Criteria();
		criteria.add("ODOBTP", "*PGM", Criteria.EQUAL, Criteria.OR);
		criteria.add("ODOBTP", "*MODULE", Criteria.EQUAL, Criteria.OR);
		read(getRecord().getLabels(), criteria, null);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getRecord()
	 */
	@Override
	public IRecordOperations getRecord() {
		return new GetPgmObjRow();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getArrayRecord(int)
	 */
	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new GetPgmObjRow[length];
	}
}