import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.table.*;
import javax.swing.tree.*;

public class AnonymousChatRoom extends JFrame {

	Operator mainOperator = null;
	MyConnector connector;

	// ���� �����
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel eastPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();

	JButton exit = new JButton("����");

	JLabel jl = new JLabel("�����ο�");
	JList list;
	DefaultListModel<String> listModel;

	JTextField tf1 = new JTextField(); // ����ڰ� �Է��Ҽ� �ֵ��� �ý�Ʈ�ʵ� ����
	JButton jb = new JButton("����"); // ���۹�ư ����
	JTextArea ta1 = new JTextArea(); // ����ڰ� �ۼ��� �ý�Ʈ�� ������ textarea����
	JScrollPane js = new JScrollPane(ta1); // �ؽ�Ʈ ���â�� ��ũ�ѹ� ����

	public AnonymousChatRoom(Operator _o) {
	
		mainOperator = _o;
		connector = _o.connector;
		setTitle("AnonymousChatRoom");
		jb.addActionListener(new MyActionListener()); // ��ư�� �̺�Ʈ ������ �߰�

		listModel = new DefaultListModel<String>();
		list = new JList(listModel);

		// �г� �߰� �۾�
		eastPanel.setPreferredSize(new Dimension(120, basePanel.getHeight()));
		setContentPane(basePanel);
		basePanel.add(eastPanel, BorderLayout.EAST);

		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(jl, BorderLayout.NORTH);
		eastPanel.add(list, BorderLayout.CENTER);
		// jt1.setBackground(new Color(0, 0, 255, 255)); //�Ķ���

		basePanel.add(js, BorderLayout.CENTER);
		setContentPane(basePanel);
		basePanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout());
		southPanel.add(tf1);
		southPanel.add(jb);
		tf1.setPreferredSize(new Dimension(400, 30));

		ta1.setEditable(false);

		setSize(600, 800);

		MyActionListener em = new MyActionListener(); // ���� �޴� �̺�Ʈ ������ ���
		exit.addActionListener(em); // ���� �޴� �̺�Ʈ ������ ���

	}

	class MyActionListener implements ActionListener { // ���� ��ư ������
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			connector.sendMassage1(tf1.getText());
			tf1.setText("");
		}
	}

	void Textareachanger1(String tf1) { // Ŭ���̾�Ʈ���� GUI textarea�� ���پ� ��Ÿ���� ���� �۾�
		String oldtext = ta1.getText();
		String newtext = tf1;
		ta1.setText(oldtext + newtext + "\n");
		mainOperator.ac.ta1.setCaretPosition(mainOperator.ac.ta1.getDocument().getLength()); // ��ũ���� �� �Ʒ��� ����
	}

	
	
	void Listchanger1(String username) { // Ŭ���̾�Ʈ���� GUI textarea�� ���پ� ��Ÿ���� ���� �۾�

		listModel.addElement(username);
		list.setModel(listModel);

	}
}

class MyActionListener implements ActionListener { // ���� ��ư ������
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		String exit = e.getActionCommand();
		if (exit == "����")
			System.exit(0);
	}
}
