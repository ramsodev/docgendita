package net.ramso.doc.as400.data;

import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.From;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.Join;
import net.ramso.doc.as400.data.tools.Order;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;

public class GetObjIniRel extends TableOperations {
	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetObjIniRel() {
		super("JOIN");
	}

	public void read() throws CriteriaException, TableOperationException {
		Criteria on = new Criteria();
		on.add("WHPNAM", "ODOBNM");
		Join join = new Join();
		join.createJoin("DSPPGMREF", "DSPOBJD", Join.LEFT, on);
		Criteria criteria = new Criteria();
		criteria.add("ODOBTP", "*MODULE", Criteria.NOTEQUAL);
		Order order = new Order();
		order.add("WHLIB", Order.ASC);
		order.add("WHPNAM", Order.ASC);
		read(getRecord().getLabels(), criteria, order, new From(join));
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getRecord()
	 */
	@Override
	public IRecordOperations getRecord() {
		return new GetObjIniRelRow();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getArrayRecord(int)
	 */
	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new GetObjIniRelRow[length];
	}
}