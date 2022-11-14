import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

	JPanel panel = new JPanel(new FlowLayout()); // ���̾ƿ� ����
	JButton enter = new JButton("Login"); // Button enter ����
	JButton signup = new JButton("SignUp"); // Button enter ����
	JButton FindId = new JButton("FindId"); 
	JButton FindPass = new JButton("FindPass"); 
	
	
	JTextField typeId = new JTextField(); // id ������ ����
	JPasswordField typePassword = new JPasswordField(); // password ������ ���� ������ ** < ó�� ����
	JLabel id = new JLabel("I   D"); // �� type id
	JLabel password = new JLabel("Password"); // �� type password
	MyConnector connector;
	Operator mainOperator = null;

	public LoginFrame(Operator _o) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainOperator = _o;
		connector = _o.connector;
		MyActionListener ml = new MyActionListener();
		setTitle("CHAT LOGIN with Server");
		id.setPreferredSize(new Dimension(70, 30));
		typeId.setPreferredSize(new Dimension(300, 30));
		password.setPreferredSize(new Dimension(70, 30));
		typePassword.setPreferredSize(new Dimension(300, 30));
		enter.setPreferredSize(new Dimension(185, 30));
		signup.setPreferredSize(new Dimension(185, 30));
		FindId.setPreferredSize(new Dimension(185, 30));
		FindPass.setPreferredSize(new Dimension(185, 30));
		panel.add(id); // ID �߰�
		panel.add(typeId); // �Էµ� ID �߰�
		panel.add(password); // PASSWORD �߰�
		panel.add(typePassword); // �Էµ� PASSWORD �߰�
		panel.add(enter);
		panel.add(signup);
		panel.add(FindId);
		panel.add(FindPass);
		setContentPane(panel);
		enter.addActionListener(ml); // Login ��ư�� �̺�Ʈ ������ �߰�
		signup.addActionListener(ml); // signup ��ư�� �̺�Ʈ ������ �߰�
		FindId.addActionListener(ml); // signup ��ư�� �̺�Ʈ ������ �߰�
		FindPass.addActionListener(ml); // signup ��ư�� �̺�Ʈ ������ �߰�
		
		setResizable(false);
		setSize(400, 185);
		// �α��� â�� ȭ�� �߾ӿ� ��ġ��Ű��...
		Dimension frameSize = this.getSize(); // ������ ����� ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		setVisible(true);
	}

	class MyActionListener implements ActionListener { // �̺�Ʈ�� �߻���Ų ������Ʈ(�ҽ�)
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Login")) { // �α��ι�ư�� ������...
				// Password ������Ʈ���� ���ڿ� �о���� 1
				String pw = "";
				for (int i = 0; i < typePassword.getPassword().length; i++) {
					pw = pw + typePassword.getPassword()[i];
				}
				System.out.println(typeId.getText() + "  " + pw); // Ȯ�ο�, �����
				if (connector.sendLoginInformation(typeId.getText(), pw)) {
					mainOperator.mf.setVisible(true);
					connector.start();
					dispose();
				} else
					System.out.println("Log in Error~~~");

			} else if (b.getText().equals("SignUp")) {
				mainOperator.su.setVisible(true);
				dispose();
			}else if (b.getText().equals("FindId")) {
				mainOperator.fd.setVisible(true);
				dispose();
			}else if (b.getText().equals("FindPass")) {
				mainOperator.fp.setVisible(true);
				dispose();
			}
			
		}
	}

}