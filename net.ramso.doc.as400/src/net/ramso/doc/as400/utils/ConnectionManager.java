package net.ramso.doc.as400.utils;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Exception;
import com.ibm.as400.access.AS400JDBCDataSource;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.ErrorCompletingRequestException;

public class ConnectionManager {
	private static AS400 con = null;
	private static Connection condb = null;
	public static String CHGLIBCOMMAND = "CHGLIBL LIBL(";
	public static String CRTLIBCMD = "CRTLIB LIB(";
	public static String DLTLIBCMD = "DLTLIB LIB(";
	public static String LIBL = "";

	public static void createConnection(String host, String user, String pass,
			String libs) throws AS400SecurityException,
			ErrorCompletingRequestException, IOException, InterruptedException,
			PropertyVetoException {
		if (con == null) {
			con = new AS400(host, user, pass);
			addLibs(libs);
		}
	}

	private static void addLibs(String libs) throws AS400SecurityException,
			ErrorCompletingRequestException, IOException, InterruptedException,
			PropertyVetoException {
		LIBL += " " + libs;
		String cmd = CHGLIBCOMMAND + LIBL + ")";
		runCmd(cmd);
	}

	public static void crtLib(String lib) throws AS400SecurityException,
			ErrorCompletingRequestException, IOException, InterruptedException,
			PropertyVetoException {
		String cmd = CRTLIBCMD + lib + ")";
		runCmd(cmd);
	}

	public static void dltLib(String lib) throws AS400SecurityException,
			ErrorCompletingRequestException, IOException, InterruptedException,
			PropertyVetoException {
		String cmd = DLTLIBCMD + lib + ")";
		runCmd(cmd);
	}

	public static boolean runCmd(String cmd) throws AS400SecurityException,
			ErrorCompletingRequestException, IOException, InterruptedException,
			PropertyVetoException {
		CommandCall command = new CommandCall(getConnection());
		boolean status = true;
		status = command.run(cmd);
		if (status != true) {
			System.out.println("Command failed!");
			AS400Message[] messagelist = command.getMessageList();
			throw new AS400Exception(messagelist);
		}

		return status;
	}

	public static AS400 getConnection() {
		return con;
	}

	public static Connection getJDBCConnection() throws SQLException {
		if (condb == null) {
			AS400JDBCDataSource d = new AS400JDBCDataSource(getConnection());
			d.setLibraries(RunExtractors.LIB);
			condb = d.getConnection();
		}
		return condb;
	}

	public static void disconnect() throws SQLException {
		getJDBCConnection().close();
		condb = null;
		getConnection().disconnectAllServices();
		con = null;
	}
}
