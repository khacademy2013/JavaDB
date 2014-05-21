import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.derby.drda.NetworkServerControl;


public class ChunjaJavaDB2 {

	public static void main(String[] args) {
		String driver = "org.apache.derby.jdbc.ClientDriver";
		String protocol = "jdbc:derby://localhost:1527/";
		String dbname = "Chunja";
		String user="user1";
		String password="user1";
		NetworkServerControl server = null;
		Connection conn = null;
		try {
			server = new NetworkServerControl();
			PrintWriter pw = new PrintWriter("server.log");
			server.start(pw);

			Class.forName(driver);
			System.out.println("드라이버로드 성공");

			String url = protocol + dbname +";create=true;"; 
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("연결성공(jdbc:derby://localhost:1527/) : " + conn);
			Statement stmt = conn.createStatement();
			// Creating table
			// Creating table
			stmt.executeUpdate("drop table ChunJa2");
			stmt.executeUpdate("create table ChunJa2 (idx integer not null, hanja char(4), hangul char(4), discription1 char(50), discription2 char(100))");
			Scanner sc = new Scanner(new File("chunja2.txt"));
			while (sc.hasNextLine()) {
				String t = sc.nextLine().trim();
				PreparedStatement pstmt = conn.prepareStatement("insert into ChunJa2 values(?,?,?,?,?)");
				if (t.length() > 0) {
					StringTokenizer st = new StringTokenizer(t, "|");
					pstmt.setInt(1, Integer.parseInt(st.nextToken()));
					pstmt.setString(2, st.nextToken().trim());
					pstmt.setString(3, st.nextToken().trim());
					pstmt.setString(4, st.nextToken().trim());
					pstmt.setString(5, st.nextToken().trim());
					pstmt.executeUpdate();
				}
				pstmt.close();
			}
			sc.close();
			System.out.println("저장완료");
			ResultSet rs = stmt.executeQuery("select count(*) from ChunJa2");
			rs.next();
			System.out.println(rs.getInt(1) + "개 존재함");

			rs = stmt.executeQuery("select * from ChunJa2");
			if (rs.next()) {
				do {
					System.out.println(rs.getInt(1) + ". "
							+ rs.getString("hanja").trim() + "("
							+ rs.getString("hangul").trim() + ")\n"
							+ rs.getString("discription1").trim() + "\n"
							+ rs.getString("discription2").trim() + "\n");

				} while (rs.next());
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
