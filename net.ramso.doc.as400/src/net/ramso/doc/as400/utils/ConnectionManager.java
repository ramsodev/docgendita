package net.ramso.doc.as400.utils;

import java.sql.Connection;
import java.sql.SQLException;

import net.ramso.doc.as400.commands.RunExtractors;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400JDBCDataSource;
import com.ibm.as400.access.AS400JDBCDriver;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.vaccess.SQLConnection;

public class ConnectionManager {
	private static AS400		con = null;
	private static Connection	condb = null;
	public static String		CHGLIBCOMMAND	= "CHGLIBL LIBL(";
	public static String		CRTLIBCMD		= "CRTLIB LIB(";
	public static String		DLTLIBCMD		= "DLTLIB LIB(";
	public static String		LIBL			= "";

	public static void createConnection(String host, String user, String pass,
			String libs) {
		if (con == null) {
			con = new AS400(host, user, pass);
			addLibs(libs);
		}
	}

	private static void addLibs(String libs) {
		LIBL += " " + libs;
		String cmd = CHGLIBCOMMAND + LIBL + ")";
		runCmd(cmd);
	}

	public static void crtLib(String lib) {
		String cmd = CRTLIBCMD + lib + ")";
		runCmd(cmd);
	}

	public static void dltLib(String lib) {
		String cmd = DLTLIBCMD + lib + ")";
		runCmd(cmd);
	}

	public static boolean runCmd(String cmd) {
		CommandCall command = new CommandCall(getConnection());
		boolean status = true;
		try {
			status = command.run(cmd);
			if (status != true) {
				System.out.println("Command failed!");
			}
			AS400Message[] messagelist = command.getMessageList();
			for (int i = 0; i < messagelist.length; ++i) {
				System.out.println(messagelist[i].getText());
			}
		}
		catch (Exception e) {
			System.out.println("Command " + command.getCommand()
					+ " issued an exception!");
			e.printStackTrace();
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
		condb=null;
		getConnection().disconnectAllServices();
		con=null;
	}
}
