package net.ramso.doc.as400.data;

import net.ramso.doc.as400.data.tools.Criteria;
import net.ramso.doc.as400.data.tools.CriteriaException;
import net.ramso.doc.as400.data.tools.From;
import net.ramso.doc.as400.data.tools.IRecordOperations;
import net.ramso.doc.as400.data.tools.Join;
import net.ramso.doc.as400.data.tools.TableOperationException;
import net.ramso.doc.as400.data.tools.TableOperations;

public class getObjRel extends TableOperations {
	/**
	 * Constructor para una clase DBSelect.
	 */
	public getObjRel() {
		super("JOIN");
	}

	public getObjRelRow[] read(String file) throws CriteriaException,
			TableOperationException {
		Criteria on = new Criteria();
		on.add("WHFNAM = ODOBNM",null,Criteria.EQUAL);
		Join join = new Join();
		join.createJoin("DSPPGMREF", "DSPOBJD", Join.LEFT, on);
		Criteria criteria = new Criteria();
		criteria.add("WHPNAM", file);
		criteria.add("WHFNAM <> ''", null,Criteria.NOTEQUAL);
		return (getObjRelRow[]) read(getRecord().getLabels(), criteria, null, new From(join));
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getRecord()
	 */
	@Override
	public IRecordOperations getRecord() {
		return new getObjRelRow();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ramso.doc.as400.data.tools.TableOperations#getArrayRecord(int)
	 */
	@Override
	public IRecordOperations[] getArrayRecord(int length) {
		return new getObjRelRow[length];
	}
}