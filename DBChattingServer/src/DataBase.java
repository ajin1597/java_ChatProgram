import java.io.*;
import java.sql.*;
import java.util.*;

public class DataBase {

	public DataBase() {

		Connection con = null;
		Statement stmt = null;
		String URL = "jdbc:mysql://localhost:3306/chatdatabase?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "1111";

		// File userFile = new File("Users.txt"); // TextFile¿–±‚
		String readData = null;
		StringTokenizer st;

		String u_insertString = "INSERT INTO users VALUES ";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		String createUser = "CREATE TABLE users " + "(users_id varchar(20) not null primary key, "
				+ "users_password varchar(20) not null," + "users_name varchar(20) not null,"
				+ "users_number varchar(20) not null)";

		try {
			con = DriverManager.getConnection(URL, user, password);
			stmt = con.createStatement();
			stmt.executeUpdate(createUser);

		} catch (Exception e) {
			System.out.println("1");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}

	}

}