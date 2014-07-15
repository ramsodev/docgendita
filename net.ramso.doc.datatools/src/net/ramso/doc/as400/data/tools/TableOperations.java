
package net.ramso.doc.as400.data.tools;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import net.ramso.doc.as400.utils.ConnectionManager;

/**
 * Esta clase abstracta permite la implementaci�n de clases que permitan la
 * gesti�n de las tablas y sus operaciones.
 * 
 * @author jescudero
 * @viz.diagram TableOperations.tpx
 */
public abstract class TableOperations implements ITableOperations {
	/**
	 * <code>TABLE_NAME</code> el nombre de la tabla sobre la que trabajar. Se
	 * puede incluir el nombre del esquema de base de datos si es necesario.
	 */
	protected String TABLE_NAME = ""; //$NON-NLS-1$

	/**
	 * Crea e inicializa la clase con el nombre de la tabla
	 * 
	 * @param tableName
	 *            el nombre de la tabla
	 */
	public TableOperations(String tableName) {
		TABLE_NAME = tableName;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * com.trenti.utils.database.ITableOperations#read(com.trenti.utils.database
	 * .Criteria, com.trenti.utils.database.Order, java.lang.String)
	 */
	public IRecordOperations[] read(Criteria criteria, Order order)
			throws TableOperationException {
		return read(new String[]{"*"}, criteria, order);
		
	}
	public IRecordOperations[] read(String[] labels, Criteria criteria, Order order) throws TableOperationException{
		From from = new From(TABLE_NAME);
		return read(labels, criteria, order,from);
	}
	
	public IRecordOperations[] read(String[] labels, Criteria criteria, Order order, From from)
			throws TableOperationException {
		IRecordOperations[] records = null;
		String fields = "";
		for (int i = 0; i < labels.length; i++) {
			if(i > 0){
				fields+=", ";
			}
			fields+= labels[i];
		}
		ArrayList results = new ArrayList();
		String sentence = "SELECT " + fields +" FROM " + from.toString(); //$NON-NLS-1$
		if (criteria != null) {
			sentence = sentence + " " + criteria.getClause().trim(); //$NON-NLS-1$
		}
		if (order != null) {
			sentence = sentence + " " + order.getClause().trim(); //$NON-NLS-1$
		}
		PreparedStatement ps;
		try {
			ps = ConnectionManager.getJDBCConnection().prepareStatement(
					sentence);
			if (criteria != null) {
				Object[] values = criteria.getValues();
				for (int i = 0; i < values.length; i++) {
					if (values[i] instanceof BigDecimal) {
						ps.setBigDecimal(i + 1, (BigDecimal) values[i]);
					} else if (values[i] instanceof Date) {
						ps.setDate(i + 1, (Date) values[i]);
					} else if (values[i] instanceof Time) {
						ps.setTime(i + 1, (Time) values[i]);
					} else if (values[i] instanceof Timestamp) {
						ps.setTimestamp(i + 1, (Timestamp) values[i]);
					} else {
						ps.setString(i + 1, (String) values[i]);
					}
				}
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsm = rs.getMetaData();
			while (rs.next()) {
				Object[] values = new Object[rsm.getColumnCount()];
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					switch (rsm.getColumnType(i)) {
					case Types.NUMERIC:
						values[i - 1] = rs.getBigDecimal(i);
						break;
					case Types.DECIMAL:
						values[i - 1] = rs.getBigDecimal(i);
						break;
					case Types.DATE:
						values[i - 1] = rs.getDate(i);
						break;
					case Types.TIME:
						values[i - 1] = rs.getTime(i);
						break;
					case Types.TIMESTAMP:
						values[i - 1] = rs.getTimestamp(i);
						break;
					case Types.BIGINT:
						values[i - 1] = new Long(rs.getLong(i));
						break;
					case Types.INTEGER:
						values[i - 1] = new Integer(rs.getInt(i));
						break;
					case Types.SMALLINT:
						values[i - 1] = new Short(rs.getShort(i));
						break;
					case Types.TINYINT:
						values[i - 1] = new Short(rs.getShort(i));
						break;
					default:
						values[i - 1] = rs.getString(i);
						break;
					}
				}
				IRecordOperations record = getRecord();

				record.setValues(values);
				results.add(record);
			}
			ps.close();
			records = getArrayRecord(results.size());
			Object[] objs = results.toArray();
			int i = 0;
			for (i = 0; i < objs.length; i++) {
				records[i] = (IRecordOperations) objs[i];
			}
		} catch (SQLException e) {
			throw new TableOperationException("Error de READ en " + TABLE_NAME, //$NON-NLS-1$
					e);
		}
		return records;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trenti.utils.db.ITableOperations#getRecord()
	 */
	public abstract IRecordOperations getRecord();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trenti.utils.db.ITableOperations#getArrayRecord(int)
	 */
	public abstract IRecordOperations[] getArrayRecord(int length);

}