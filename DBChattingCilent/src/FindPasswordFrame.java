import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class FindPasswordFrame extends JFrame {

	MyConnector connector;
	Operator mainOperator = null;
	
	String aa;
	
	JPanel panel = new JPanel(new FlowLayout()); // ���̾ƿ� ����
	JButton Find = new JButton("Find"); // Button enter ����
	JButton cancel = new JButton("cancel"); // Button enter ����
	
	JTextField typeID = new JTextField(); 
	JTextField typeName = new JTextField(); // id ������ ����
	JPasswordField typeNumber = new JPasswordField(); // password ������ ���� ������ ** < ó�� ����
	JTextField Pass = new JTextField();
	JLabel ID = new JLabel("I   D"); // �� type id	
	JLabel name = new JLabel("NAME"); // �� type id
	JLabel number = new JLabel("NUMBER"); // �� type password*/
	
	

	public FindPasswordFrame(Operator _o) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainOperator = _o;
		connector = _o.connector;
		MyActionListener1 ml1 = new MyActionListener1();
		setTitle("Find PASSWORD");
		
		ID.setPreferredSize(new Dimension(70, 30));
		typeID.setPreferredSize(new Dimension(300, 30));
		name.setPreferredSize(new Dimension(70, 30));
		typeName.setPreferredSize(new Dimension(300, 30));
		number.setPreferredSize(new Dimension(70, 30));
		typeNumber.setPreferredSize(new Dimension(300, 30));
		Pass.setPreferredSize(new Dimension(300, 30));
		Find.setPreferredSize(new Dimension(185, 30));
		cancel.setPreferredSize(new Dimension(185, 30));

		panel.add(ID); // NAME �߰�
		panel.add(typeID); // �Էµ� NAME �߰�
		panel.add(name); // NAME �߰�
		panel.add(typeName); // �Էµ� NAME �߰�
		panel.add(number); // NUMBER �߰�
		panel.add(typeNumber); // �Էµ� NUMBER �߰�
		panel.add(Pass);
		panel.add(Find);
		panel.add(cancel);

		setContentPane(panel);
		Find.addActionListener(ml1); // Find ��ư�� �̺�Ʈ ������ �߰�
		cancel.addActionListener(ml1); // cancel ��ư�� �̺�Ʈ ������ �߰�

		setResizable(false);
		setSize(400, 220);
		// �α��� â�� ȭ�� �߾ӿ� ��ġ��Ű��...
		Dimension frameSize = this.getSize(); // ������ ����� ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		Pass.setEditable(false);

		setVisible(false);
	}

	class MyActionListener1 implements ActionListener { // �̺�Ʈ�� �߻���Ų ������Ʈ(�ҽ�)
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Find")) { // Find��ư�� ������...
				aa = connector.sendFindPasswordInformation(typeID.getText(), typeName.getText(), typeNumber.getText());
				//id, name, number������ ��ġ�Ѵٸ�
				Pass.setText(aa);
				//password�ý�Ʈ�ʵ�� ��Ÿ��.
			} else if (b.getText().equals("cancel")) {
				mainOperator.lf.setVisible(true);
				dispose();
			}
		}
	}

}



