package me.derby;

import java.sql.*;

public class DerbyCreateTable {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true");

			stmt = conn.createStatement();
			int count = stmt
					.executeUpdate("CREATE TABLE Address (idx int, streetName varchar(20),"
							+ " city varchar(20))");
			System.out.println("테이블 생성 완료 : " + count);
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try { if(stmt!=null) stmt.close(); } catch (SQLException e) { ; }
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