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

public class MainFrame extends JFrame {

	Operator mainOperator = null;
	MyConnector connector;

	// ���� �����
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel eastPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();

	// �޴� ���ۿ�
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("��üä��");
	JMenuItem AyCh = new JMenuItem("�͸�ä�ù�");
	JMenuItem exit = new JMenuItem("����");

	JLabel jl = new JLabel("�����ο�");
	JList list;
	DefaultListModel<String> listModel;

	JTextField tf = new JTextField(); // ����ڰ� �Է��Ҽ� �ֵ��� �ý�Ʈ�ʵ� ����
	JButton jb = new JButton("����"); // ���۹�ư ����
	JTextArea ta = new JTextArea(); // ����ڰ� �ۼ��� �ý�Ʈ�� ������ textarea����
	JScrollPane js = new JScrollPane(ta); // �ؽ�Ʈ ���â�� ��ũ�ѹ� ����

	public MainFrame(Operator _o) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainOperator = _o;
		connector = _o.connector;
		setTitle("CHAT V 0.1");
		jb.addActionListener(new MyActionListener()); // ��ư�� �̺�Ʈ ������ �߰�

		listModel = new DefaultListModel<String>();
		list = new JList(listModel);

		// homeMenu �߰�
		// homeMenu.add(EvCh);
		// homeMenu.addSeparator();

		homeMenu.addSeparator();
		homeMenu.add(AyCh);
		homeMenu.addSeparator();
		homeMenu.addSeparator();
		homeMenu.add(exit);
		mb.add(homeMenu);
		setJMenuBar(mb);

		// �г� �߰� �۾�
		eastPanel.setPreferredSize(new Dimension(120, basePanel.getHeight()));
		setContentPane(basePanel);
		basePanel.add(eastPanel, BorderLayout.EAST);

		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(jl, BorderLayout.NORTH);
		eastPanel.add(list, BorderLayout.CENTER);
		// jt1.setBackground(new Color(0, 0, 255, 255)); //���

		basePanel.add(js, BorderLayout.CENTER);
		setContentPane(basePanel);
		basePanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout());
		southPanel.add(tf);
		southPanel.add(jb);
		tf.setPreferredSize(new Dimension(400, 30));

		ta.setEditable(false);

		setSize(600, 800);

		MyActionListener1 em = new MyActionListener1(); // ���� �޴� �̺�Ʈ ������ ���
		exit.addActionListener(em); // ���� �޴� �̺�Ʈ ������ ���

		MyActionListener2 em2 = new MyActionListener2(); // ���� �޴� �̺�Ʈ ������ ���
		AyCh.addActionListener(em2); // ���� �޴� �̺�Ʈ ������ ���

	}

	class MyActionListener implements ActionListener { // ���� ��ư ������
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			connector.sendMassage(tf.getText());
			tf.setText("");
		}
	}

	void Textareachanger(String tf) { // Ŭ���̾�Ʈ���� GUI textarea�� ���پ� ��Ÿ���� ���� �۾�
		String oldtext = ta.getText();
		String newtext = tf;
		ta.setText(oldtext + newtext + "\n");
		mainOperator.mf.ta.setCaretPosition(mainOperator.mf.ta.getDocument().getLength()); // ��ũ���� �� �Ʒ��� ����
	}

	void Listchanger(String username) {

		listModel.addElement(username);
		list.setModel(listModel);

	}

	class MyActionListener2 implements ActionListener { // �͸�ä�ù� �̵� �̺�Ʈ ������
		public void actionPerformed(ActionEvent e) {
			String AyCh = e.getActionCommand();

			mainOperator.ac.setVisible(true);

		}
	}
}

class MyActionListener1 implements ActionListener { // �޴� ���� �̺�Ʈ ������
	public void actionPerformed(ActionEvent e) {
		String exit = e.getActionCommand();
		if (exit == "����")
			System.exit(0);
	}
}
