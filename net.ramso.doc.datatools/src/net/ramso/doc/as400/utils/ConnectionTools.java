/**
 * 
 */
package net.ramso.doc.as400.utils;

import java.sql.Connection;
import java.sql.ResultSet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IManagedConnection;
import org.eclipse.datatools.connectivity.ProfileManager;

/**
 * @author ramso
 */
public class ConnectionTools {
	private static Connection	connection = null;
	private static String profile = "";

	public static IConnectionProfile[] getProfiles(){
		return ProfileManager.getInstance().getProfiles();	
	}
	
	
	public static IConnectionProfile getConnection() {
		IConnectionProfile profileConnector = ProfileManager.getInstance()
				.getProfileByName(profile);
		IStatus status = profileConnector.connect();
		if (status.getCode() == IStatus.OK) {
			// success
		}
		else {
			// failure :(
			if (status.getException() != null) {
				status.getException().printStackTrace();
			}
		}
		return profileConnector;
	}

	public static ResultSet execSQL(String sql) {
		java.sql.Connection conn = getJavaConnectionForProfile(getConnection());
		if (conn != null) {
			try {
				java.sql.Statement stmt = conn.createStatement();
				return stmt.executeQuery(sql);
			}
			catch (java.sql.SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return null;
	}

	public static java.sql.Connection getJavaConnectionForProfile(
			IConnectionProfile profile) {
		if (connection == null) {
			IManagedConnection managedConnection = ((IConnectionProfile) profile)
					.getManagedConnection("java.sql.Connection");
			if (managedConnection != null) {
				connection = (java.sql.Connection) managedConnection
						.getConnection().getRawConnection();
			}
		}
		return connection;
	}
}
