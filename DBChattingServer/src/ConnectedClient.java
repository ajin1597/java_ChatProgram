import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.List;
import java.sql.*;

public class ConnectedClient extends Thread {

	Socket socket;
	Server server;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;
	String msg;
	String uName;
	ResultSet rs;

	String loginTag = "LOGIN";
	String SignUpTag = "SignUp";
	String MassageTag = "Massage";
	String MassageTag1 = "Massage1";
	String UserNameTag = "UserName";
	String UserNameTag1 = "UserName1";
	String FindIDTag = "FindID";
	String FindPassTag = "FindPass";

	Connection con = null;
	Statement stmt = null;
	String URL = "jdbc:mysql://localhost:3306/chatdatabase?serverTimezone=Asia/Seoul";
	String user = "root";
	String password = "1111";

	ConnectedClient(Socket _s, Server _server) {
		this.server = _server;
		this.socket = _s;
	}

	public void run() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		try {
			System.out.println("Server > " + this.socket.getInetAddress() + "에서의 접속이 연결되었습니다.");
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);

			// dataOutStream.writeUTF("Welcome to this server!");//

			while (true) {
				msg = dataInStream.readUTF();
				System.out.println(msg);
				StringTokenizer stk = new StringTokenizer(msg, "^^");
				String f_stk = stk.nextToken();

				if (f_stk.equals(loginTag)) { // 로그인을 위한 아이디와 암호 확인 작업
					String uid = stk.nextToken();
					String upass = stk.nextToken();
					System.out.println(uid);
					System.out.println(upass);

					if (server.lc.lgcheck(uid, upass)) {
						dataOutStream.writeUTF("LOGIN_OK");
						uName = uid;
					} else
						dataOutStream.writeUTF("LOGIN_FALE");
					try {
						con = DriverManager.getConnection(URL, user, password);
						stmt = con.createStatement();
						rs = stmt.executeQuery("select * from users");

						while (rs.next()) {
							String id = rs.getString("users_id");
							dataOutStream.writeUTF(UserNameTag + "^^" + id);
						}
					} catch (Exception e) {
						// flag = false;
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
				} else if (f_stk.equals(SignUpTag)) { // 회원가입을 위한 아이디와 암호 확인 작업

					String uid = stk.nextToken();
					String upass = stk.nextToken();
					String uname = stk.nextToken();
					String unumber = stk.nextToken();

					server.lc.sucheck(uid, upass, uname, unumber);
					dataOutStream.writeUTF("SignUp_OK");
					uName = uid;
					// dataOutStream.writeUTF("SignUp_FALE");
					for (int i = 0; i < server.clients.size(); i++) {
						outStream = server.clients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(UserNameTag + "^^" + uid);
					}

				}else if (f_stk.equals(MassageTag)) { // 클라이언트 받아온 문자열을 다른 클라이언트에게 보여주기 위한 작업
					String tf = stk.nextToken();

					for (int i = 0; i < server.clients.size(); i++) {
						outStream = server.clients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(MassageTag + "^^" + uName + " : " + tf);
					}

				}else if (f_stk.equals(MassageTag1)) { // 클라이언트 받아온 문자열을 다른 클라이언트에게 보여주기 위한 작업
					String tf1 = stk.nextToken();

					for (int i = 0; i < server.clients.size(); i++) {
						outStream = server.clients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(MassageTag1 + "^^" + tf1);
					}

				}else if (f_stk.equals(FindIDTag)) { // ID를 찾기 위해 client로 데이터를 보냄

					String uname = stk.nextToken();
					String unumber = stk.nextToken();

					try {
						con = DriverManager.getConnection(URL, user, password);
						stmt = con.createStatement();
						rs = stmt.executeQuery("select * from users where users_name = '" + uname
								+ "' and users_number= '" + unumber + "'");

						while (rs.next()) {
							String id = rs.getString("users_id");
							dataOutStream.writeUTF(id);
						}
					} catch (Exception e) {

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
				} else if (f_stk.equals(FindPassTag)) { // Password를 찾기 위해 client로 데이터를 보냄

					String uid = stk.nextToken();
					String uname = stk.nextToken();
					String unumber = stk.nextToken();

					try {
						con = DriverManager.getConnection(URL, user, password);
						stmt = con.createStatement();
						rs = stmt.executeQuery("select * from users where users_id = '" + uid + "' and users_name = '"
								+ uname + "' and users_number= '" + unumber + "'");

						while (rs.next()) {
							String pass = rs.getString("users_pass");
							dataOutStream.writeUTF(pass);
						}
					} catch (Exception e) {

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
				}

			}
		} catch (Exception e) {

		}
	}
}
