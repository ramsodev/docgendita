package net.ramso.doc.as400.data;

import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.From;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.Join;
import net.ramso.doc.as400.data.tools.Order;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;

public class GetKeyLf extends TableOperations {
	public GetKeyLf() {
		super("JOIN");
	}

	public GetKeyLfRow[] read(String file) throws CriteriaException,
			TableOperationException {
		Criteria on = new Criteria();
		on.add("APFILE", "ODOBNM");
		Join join = new Join();
		join.createJoin("DSPFDACC", "DSPOBJD", Join.LEFT, on);
		Criteria criteria = new Criteria();
		criteria.add("ODOBNM", file);
		Order order = new Order();
		order.add("APFILE", Order.ASC);
		order.add("APEKEYN", Order.ASC);
		return (GetKeyLfRow[]) read(getRecord().getLabels(), criteria, order, new From(join));
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getRecord()
	 */
	@Override
	public IRecordOperations getRecord() {
		return new GetKeyLfRow();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getArrayRecord(int)
	 */
	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new GetKeyLfRow[length];
	}
}