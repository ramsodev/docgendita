package net.ramso.doc.as400.data;

import net.ramso.doc.as400.commands.RunExtractors;
import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.Order;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;

public class GetObjUse extends TableOperations {
	public GetObjUse() {
		super(RunExtractors.DSPPGMREFFILE);
	}

	public GetObjUseRow[] read(String file) throws CriteriaException,
			TableOperationException {
		Criteria criteria = new Criteria();
		criteria.add("WHFNAM", file);
//		criteria.add("WHFNAM", lib);
		Order order = new Order();
		order.add("WHPNAM", Order.ASC);
		return (GetObjUseRow[]) read(getRecord().getLabels(), criteria, order);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getRecord()
	 */
	@Override
	public IRecordOperations getRecord() {
		return new GetObjUseRow();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getArrayRecord(int)
	 */
	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new GetObjUseRow[length];
	}
}