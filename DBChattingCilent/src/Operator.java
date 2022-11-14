public class Operator {

	MyConnector connector = null;
	LoginFrame lf = null;
	MainFrame mf = null;
	SignUp su = null;
	FindIdFrame fd = null;
	FindPasswordFrame fp = null;
	AnonymousChatRoom ac = null;
	
	public static void main(String[] args) {
		Operator operator = new Operator();
		operator.connector = new MyConnector(operator);
		operator.mf = new MainFrame(operator);
		operator.lf = new LoginFrame(operator);
		operator.su = new SignUp(operator);
		operator.fd = new FindIdFrame(operator);
		operator.fp = new FindPasswordFrame(operator);
		operator.ac = new AnonymousChatRoom(operator);
	}

}
