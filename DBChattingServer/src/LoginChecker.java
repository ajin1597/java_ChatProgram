import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.*;

public class LoginChecker {

	ArrayList<User> userInfor = new ArrayList<User>();

	StringTokenizer st;

	Connection con = null;
	Statement stmt = null;
	String URL = "jdbc:mysql://localhost:3306/chatdatabase?serverTimezone=Asia/Seoul";
	String user = "root";
	String password = "1111";
	ResultSet rs;

	LoginChecker() {

	}

	boolean lgcheck(String uid, String upass) {
		boolean flag = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			con = DriverManager.getConnection(URL, user, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from users");

			while (rs.next()) {
				String id = rs.getString("users_id");
				String pw = rs.getString("users_password");
				if (id.equals(uid) && pw.equals(upass)) {
					flag = true;
					System.out.println("로그인 성공");
					break;
				}
			}
		} catch (Exception e) {
			flag = false;
			System.out.println("로그인 실패 > " + e.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();

			} catch (Exception e) {

			}
		}
		if (flag == true)
			return true;
		else
			return false;
	}

	boolean sucheck(String uid, String upass, String uname, String unumber) {
		boolean flag = false;
		String recordString = "INSERT INTO users VALUES('" + uid + "', '" + upass + "', '" + uname + "', '" + unumber
				+ "')";
		try {
			con = DriverManager.getConnection(URL, user, password);
			stmt = con.createStatement();
			stmt.executeUpdate(recordString);
		} catch (Exception e) {

		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();

			} catch (Exception e) {

			}
		}

		return true;

	}

}