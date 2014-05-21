import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.derby.jdbc.EmbeddedDriver;

public class JavaDB03 {
	public static void main(String[] args) {
		// String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		String protocol = "jdbc:derby:";
		String dbname = "derbyDB";
		String user="user1";
		String password="user1";
		Connection conn = null;
		try {
			// 1. 드라이버로드
			// JDK폴더 밑의 db/lib 안에있는 derby.jar파일을 [Build Path]에 추가한다.
			// Embedded Mode : 데이타베이스가 자바의 한 부분으로써 실행되고 , 같은 JVM을 공유한다.
			// Class.forName(driver);
			DriverManager.registerDriver(new EmbeddedDriver());
			System.out.println("드라이버로드 성공");

			// 2. 연결 (create=true : 없으면 만들어라) 
			// jdbc:derby:dbname;user=dbuser;password=dbuserpwd
			
			// String url = protocol + dbname + ";create=true;user="+user+";password="+password+";";
			// conn = DriverManager.getConnection(url);
			String url = protocol + dbname + ";create=true;";
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("연결성공(derbyDB) : " + conn);
			// 3. 사용
			Statement stmt = conn.createStatement();
			// Creating table
			stmt.executeUpdate("drop table user_table");
			stmt.executeUpdate("create table user_table (user_id integer not null, name varchar(50), personal_info_id integer, manager_id integer)");
			stmt.executeUpdate("insert into user_table values (501, 'Sujith',101,501)");
			stmt.executeUpdate("insert into user_table values (502, 'Reshmi',102,501)");
			stmt.executeUpdate("insert into user_table values (503, '한글은?',103,502)");
			stmt.executeUpdate("insert into user_table values (504, 'Meera Menon',104,501)");
			stmt.executeUpdate("insert into user_table values (505, 'Arathi Kutty',105,501)");
			ResultSet rs = stmt.executeQuery("select * from user_table");
			while (rs.next()) {
				System.out.println("Name = " + rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. 닫기
			try { if(conn!=null)conn.close(); } catch (SQLException e) { ; }
			// 5. Database shut down
			boolean gotSQLExc = false;
			try { DriverManager.getConnection("jdbc:"+dbname+";shutdown=true"); } catch (SQLException se) { gotSQLExc = true; }
			System.out.println("Database shut down "+(!gotSQLExc? "실패!" : "성공!"));
		}

	}
}
