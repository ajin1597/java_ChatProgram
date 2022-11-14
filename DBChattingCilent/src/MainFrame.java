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

	// 공간 제어용
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel eastPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();

	// 메뉴 제작용
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("전체채팅");
	JMenuItem AyCh = new JMenuItem("익명채팅방");
	JMenuItem exit = new JMenuItem("종료");

	JLabel jl = new JLabel("접속인원");
	JList list;
	DefaultListModel<String> listModel;

	JTextField tf = new JTextField(); // 사용자가 입력할수 있도록 택스트필드 생성
	JButton jb = new JButton("전송"); // 전송버튼 생성
	JTextArea ta = new JTextArea(); // 사용자가 작성한 택스트를 보여줄 textarea생성
	JScrollPane js = new JScrollPane(ta); // 텍스트 출력창에 스크롤바 생성

	public MainFrame(Operator _o) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainOperator = _o;
		connector = _o.connector;
		setTitle("CHAT V 0.1");
		jb.addActionListener(new MyActionListener()); // 버튼에 이벤트 리스너 추가

		listModel = new DefaultListModel<String>();
		list = new JList(listModel);

		// homeMenu 추가
		// homeMenu.add(EvCh);
		// homeMenu.addSeparator();

		homeMenu.addSeparator();
		homeMenu.add(AyCh);
		homeMenu.addSeparator();
		homeMenu.addSeparator();
		homeMenu.add(exit);
		mb.add(homeMenu);
		setJMenuBar(mb);

		// 패널 추가 작업
		eastPanel.setPreferredSize(new Dimension(120, basePanel.getHeight()));
		setContentPane(basePanel);
		basePanel.add(eastPanel, BorderLayout.EAST);

		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(jl, BorderLayout.NORTH);
		eastPanel.add(list, BorderLayout.CENTER);
		// jt1.setBackground(new Color(0, 0, 255, 255)); //블루

		basePanel.add(js, BorderLayout.CENTER);
		setContentPane(basePanel);
		basePanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout());
		southPanel.add(tf);
		southPanel.add(jb);
		tf.setPreferredSize(new Dimension(400, 30));

		ta.setEditable(false);

		setSize(600, 800);

		MyActionListener1 em = new MyActionListener1(); // 종료 메뉴 이벤트 리스너 등록
		exit.addActionListener(em); // 종료 메뉴 이벤트 리스너 등록

		MyActionListener2 em2 = new MyActionListener2(); // 종료 메뉴 이벤트 리스너 등록
		AyCh.addActionListener(em2); // 종료 메뉴 이벤트 리스너 등록

	}

	class MyActionListener implements ActionListener { // 전송 버튼 리스너
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			connector.sendMassage(tf.getText());
			tf.setText("");
		}
	}

	void Textareachanger(String tf) { // 클라이언트에서 GUI textarea에 한줄씩 나타내기 위한 작업
		String oldtext = ta.getText();
		String newtext = tf;
		ta.setText(oldtext + newtext + "\n");
		mainOperator.mf.ta.setCaretPosition(mainOperator.mf.ta.getDocument().getLength()); // 스크롤을 맨 아래로 유지
	}

	void Listchanger(String username) {

		listModel.addElement(username);
		list.setModel(listModel);

	}

	class MyActionListener2 implements ActionListener { // 익명채팅방 이동 이벤트 리스너
		public void actionPerformed(ActionEvent e) {
			String AyCh = e.getActionCommand();

			mainOperator.ac.setVisible(true);

		}
	}
}

class MyActionListener1 implements ActionListener { // 메뉴 종료 이벤트 리스너
	public void actionPerformed(ActionEvent e) {
		String exit = e.getActionCommand();
		if (exit == "종료")
			System.exit(0);
	}
}
