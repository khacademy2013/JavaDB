import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.drda.NetworkServerControl;


public class JavaDB05 {

	public static void main(String[] args) {
		String driver = "org.apache.derby.jdbc.ClientDriver";
		String protocol = "jdbc:derby://localhost:1527/";
		String dbname = "derbyDB2";
		String user="user1";
		String password="user1";
		NetworkServerControl server = null;
		Connection conn = null;
		try {
			// 0. 서버시작
			server = new NetworkServerControl();
			PrintWriter pw = new PrintWriter("server.log");
			server.start(pw);
			//System.out.println(server.getRuntimeInfo());
			//System.out.println(server.getSysinfo());

			// 1. 드라이버로드
			// JDK폴더 밑의 db/lib 안에있는 derby.jar파일을 [Build Path]에 추가한다.
			// Network Server 방식 : 어플리케이션과 DB가 다른 JVM 또는 프로세스에 존재하도록 구성하는 방식으로 
			//                       각 인스턴스가 하나의 JVM 내에서 동작하고, 일반적인 JDBC와 똑같은 방식으로 
			//                       접근이 가능하다.
			Class.forName(driver);
			System.out.println("드라이버로드 성공");

			// 2. 연결 (create=true : 없으면 만들어라) 
			// jdbc:derby:
			String url = protocol + dbname +";create=true;"; // 메모리에 생성
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("연결성공(jdbc:derby://localhost:1527/) : " + conn);
			// 3. 사용
			Statement stmt = conn.createStatement();
			// Creating table
			// stmt.executeUpdate("drop table user_table");
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 4. 닫기
			try { if(conn!=null)conn.close(); } catch (SQLException e) { ; }
			// 5. Database shut down
			boolean gotSQLExc = false;
			try { DriverManager.getConnection("jdbc:"+dbname+";shutdown=true"); } catch (SQLException se) { gotSQLExc = true; }
			System.out.println("Database shut down "+(!gotSQLExc? "실패!" : "성공!"));
			// 6. 서버종
			try {
				server.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
