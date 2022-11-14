import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.*;

public class Server {

	ServerSocket ss = null;
	ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();
	LoginChecker lc = null;
	DataBase db = null;
	
	Connection con = null;
	Statement stmt = null;
	String URL = "jdbc:mysql://localhost:3306/chatdatabase?serverTimezone=Asia/Seoul";
	String user = "root";
	String password = "1111";
	
	
	
	public static void main(String[] args) {
		Server server = new Server();
		server.lc = new LoginChecker();
		server.db = new DataBase();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			server.ss = new ServerSocket(60000);
			System.out.println("Server > Server Socket is Created...");
			while (true) {
				Socket socket = server.ss.accept();
				ConnectedClient c = new ConnectedClient(socket, server);
				server.clients.add(c);
				c.start();
			}
		} catch (Exception e) {
			System.out.println("Server > 예외발생.");
		}
	}
}
