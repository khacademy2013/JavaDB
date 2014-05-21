package me.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyDB00 {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true");

			stmt = conn.createStatement();
			//stmt.execute("drop table emp");

			stmt.execute("CREATE TABLE "
					+ "EMP (id integer primary key not null, name varchar(32))");
			stmt.execute("INSERT INTO EMP VALUES (1, '한사람')");
			rs = stmt.executeQuery("SELECT * FROM EMP");
			while (rs.next()) {
				System.out.println("성명:" + rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(rs!=null) rs.close(); } catch (SQLException e) { ; }
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