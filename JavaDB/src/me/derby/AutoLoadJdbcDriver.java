package me.derby;

import java.sql.*;
import java.util.*;

public class AutoLoadJdbcDriver {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			listDrivers();

			Driver driverClass = (Driver) DriverManager.getDriver("jdbc:derby:memory:myDB;create=true");
			System.out.println("jdbc:derby:memory:myDB;create=true");
			System.out.println("   " + driverClass.getClass().getName());

			listDrivers();
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

	private static void listDrivers() {
		Enumeration driverList = DriverManager.getDrivers();
		System.out.println("\nDrivers 리스트");
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("   " + driverClass.getClass().getName());
		}
		System.out.println();
	}
}