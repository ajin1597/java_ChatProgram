import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class MyConnector extends Thread {
	Socket mySocket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;

	MainFrame mf;

	final String loginTag = "LOGIN";
	final String SignUpTag = "SignUp";
	final String MassageTag = "Massage";
	final String MassageTag1 = "Massage1";
	final String UserNameTag = "UserName";
	final String UserNameTag1 = "UserName1";
	final String FindIDTag = "FindID";

	MyConnector connector;
	Operator mainOperator = null;

	MyConnector(Operator _o) {

		mainOperator = _o;
		connector = _o.connector;

		try {
			mySocket = new Socket("localhost", 60000);
			System.out.println("CLIENT LOG> 채팅방에 입장하였습니다.");
			outStream = mySocket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = mySocket.getInputStream();
			dataInStream = new DataInputStream(inStream);

		} catch (Exception e) {

		}
	}

	// logintag를 붙여서 로그인 정보 전송
	boolean sendLoginInformation(String uid, String upass) {
		boolean flag = false;
		String msg = null;
		try {
			dataOutStream.writeUTF(loginTag + "^^" + uid + "^^" + upass);
			msg = dataInStream.readUTF();

		} catch (Exception e) {

		}

		if (msg.equals("LOGIN_OK")) {

			return true;

		} else {
			return false;
		}

	}

	// SingUpTag를 붙여 회원가입 정보 전송
	boolean sendSignUpInformation(String uid, String upass, String utypeNa, String utypeNb) {
		boolean flag = false;
		String msg = null;
		try {
			dataOutStream.writeUTF(SignUpTag + "^^" + uid + "^^" + upass + "^^" + utypeNa + "^^" + utypeNb);
			msg = dataInStream.readUTF();
		} catch (Exception e) {

		}

		if (msg.equals("SignUp_OK")) {
			return true;
		} else {
			return false;
		}

	}

	// FindIDTag를 붙여 name, number 정보 전송
	String sendFindidInformation(String uname, String unumber) {

		String msg = null;
		try {
			dataOutStream.writeUTF(FindIDTag + "^^" + uname + "^^" + unumber);
			msg = dataInStream.readUTF();
		} catch (Exception e) {

		}
		return msg;
		// return true;

	}

	// FindIDTag를 붙여 id, name, number 정보 전송
	String sendFindPasswordInformation(String uid, String uname, String unumber) {

		String msg = null;
		try {
			dataOutStream.writeUTF(FindIDTag + "^^" + uid + "^^" + uname + "^^" + unumber);
			msg = dataInStream.readUTF();
		} catch (Exception e) {

		}
		return msg;
		// return true;

	}

	// textfield에서 작성한 문자열을 massagetag를 붙여 전송
	void sendMassage(String tf) {
		try {
			dataOutStream.writeUTF(MassageTag + "^^" + tf);
		} catch (Exception e) {
		}

	}

	void sendMassage1(String tf1) {
		try {
			dataOutStream.writeUTF(MassageTag1 + "^^" + tf1);
		} catch (Exception e) {
		}

	}

	// 받아온 문자열을 Textarea로 나타내는 작업

	public void run() {
		try {
			inStream = this.mySocket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			while (true) {
				String msg = dataInStream.readUTF();
				System.out.println(msg);
				StringTokenizer stk = new StringTokenizer(msg, "^^");
				String f_stk = stk.nextToken();

				if (f_stk.equals(MassageTag)) {
					String tf = stk.nextToken();
					System.out.println(tf);
					mainOperator.mf.Textareachanger(tf);

				} else if (f_stk.equals(UserNameTag)) {
					String username = stk.nextToken();
					System.out.println(username);
					mainOperator.mf.Listchanger(username);
					mainOperator.ac.Listchanger1(username);

				} else if (f_stk.equals(MassageTag1)) {
					String tf1 = stk.nextToken();
					System.out.println(tf1);
					mainOperator.ac.Textareachanger1(tf1);

				}

			}
		} catch (Exception e) {

		}

	}

}
