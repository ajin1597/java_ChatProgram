import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends JFrame {

	JPanel panel = new JPanel(new FlowLayout()); // ���̾ƿ� ����
	JButton enter = new JButton("ȸ������"); // Button enter ����
	JButton cancel = new JButton("cancel"); // Button enter ����
	JTextField typeId = new JTextField(); // id ������ ����
	JTextField typeNa = new JTextField(); // name
	JTextField typeNb = new JTextField(); // ��ȭ��ȣ
	JPasswordField typePassword = new JPasswordField(); // password ������ ���� ������ ** < ó�� ����

	JLabel id = new JLabel("I   D"); // �� type id
	JLabel na = new JLabel("NAME"); // �� type id
	JLabel nb = new JLabel("NUMBER"); // �� type id
	JLabel password = new JLabel("Password"); // �� type password
	MyConnector connector;
	Operator mainOperator = null;

	public SignUp(Operator _o) {
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
		cancel.setPreferredSize(new Dimension(185, 30));
		na.setPreferredSize(new Dimension(70, 30));
		typeNa.setPreferredSize(new Dimension(300, 30));
		nb.setPreferredSize(new Dimension(70, 30));
		typeNb.setPreferredSize(new Dimension(300, 30));

		panel.add(id); // ID �߰�
		panel.add(typeId); // �Էµ� ID �߰�
		panel.add(password); // PASSWORD �߰�
		panel.add(typePassword); // �Էµ� PASSWORD �߰�
		panel.add(na);
		panel.add(typeNa);
		panel.add(nb);
		panel.add(typeNb);
		panel.add(enter);
		panel.add(cancel);

		setContentPane(panel);
		enter.addActionListener(ml); // Login ��ư�� �̺�Ʈ ������ �߰�
		cancel.addActionListener(ml); // cancel ��ư�� �̺�Ʈ ������ �߰�

		setResizable(false);
		setSize(400, 220);
		// �α��� â�� ȭ�� �߾ӿ� ��ġ��Ű��...
		Dimension frameSize = this.getSize(); // ������ ����� ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		setVisible(false);

	}

	class MyActionListener implements ActionListener { // �̺�Ʈ�� �߻���Ų ������Ʈ(�ҽ�)
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("ȸ������")) { // ȸ�������� ������...
				// Password ������Ʈ���� ���ڿ� �о���� 1
				String pw = "";
				for (int i = 0; i < typePassword.getPassword().length; i++) {
					pw = pw + typePassword.getPassword()[i];
				}

				if (connector.sendSignUpInformation(typeId.getText(), pw, typeNa.getText(), typeNb.getText())) {
					dispose();
					mainOperator.lf.setVisible(true);
				} else
					System.out.println("Log in Error~~~");

			} else if (b.getText().equals("cancel")) {
				mainOperator.lf.setVisible(true);
				dispose();
			}
		}
	}

}