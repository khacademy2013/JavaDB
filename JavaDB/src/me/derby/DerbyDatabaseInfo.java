package me.derby;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDatabaseInfo {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			// Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true");

			// Database and driver info
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println("Server name: " + meta.getDatabaseProductName());
			System.out.println("Server version: " + meta.getDatabaseProductVersion());
			System.out.println("Driver name: " + meta.getDriverName());
			System.out.println("Driver version: " + meta.getDriverVersion());
			System.out.println("JDBC major version: " + meta.getJDBCMajorVersion());
			System.out.println("JDBC minor version: " + meta.getJDBCMinorVersion());

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try { if(conn!=null) conn.close(); } catch (SQLException e) { ; }
			// DB shutdown
			boolean shutDownError = false;
			try {
				DriverManager.getConnection("jdbc:derby:;shutdown=true");
			} catch (SQLException se) {
				shutDownError = true;
			}
			System.out.println(!shutDownError?"DB 종료 오류" : "DB 정상 종료");
		}
	}
}