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

	// 공간 제어용
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel eastPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();

	JButton exit = new JButton("종료");

	JLabel jl = new JLabel("접속인원");
	JList list;
	DefaultListModel<String> listModel;

	JTextField tf1 = new JTextField(); // 사용자가 입력할수 있도록 택스트필드 생성
	JButton jb = new JButton("전송"); // 전송버튼 생성
	JTextArea ta1 = new JTextArea(); // 사용자가 작성한 택스트를 보여줄 textarea생성
	JScrollPane js = new JScrollPane(ta1); // 텍스트 출력창에 스크롤바 생성

	public AnonymousChatRoom(Operator _o) {
	
		mainOperator = _o;
		connector = _o.connector;
		setTitle("AnonymousChatRoom");
		jb.addActionListener(new MyActionListener()); // 버튼에 이벤트 리스너 추가

		listModel = new DefaultListModel<String>();
		list = new JList(listModel);

		// 패널 추가 작업
		eastPanel.setPreferredSize(new Dimension(120, basePanel.getHeight()));
		setContentPane(basePanel);
		basePanel.add(eastPanel, BorderLayout.EAST);

		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(jl, BorderLayout.NORTH);
		eastPanel.add(list, BorderLayout.CENTER);
		// jt1.setBackground(new Color(0, 0, 255, 255)); //파란색

		basePanel.add(js, BorderLayout.CENTER);
		setContentPane(basePanel);
		basePanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout());
		southPanel.add(tf1);
		southPanel.add(jb);
		tf1.setPreferredSize(new Dimension(400, 30));

		ta1.setEditable(false);

		setSize(600, 800);

		MyActionListener em = new MyActionListener(); // 종료 메뉴 이벤트 리스너 등록
		exit.addActionListener(em); // 종료 메뉴 이벤트 리스너 등록

	}

	class MyActionListener implements ActionListener { // 전송 버튼 리스너
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			connector.sendMassage1(tf1.getText());
			tf1.setText("");
		}
	}

	void Textareachanger1(String tf1) { // 클라이언트에서 GUI textarea에 한줄씩 나타내기 위한 작업
		String oldtext = ta1.getText();
		String newtext = tf1;
		ta1.setText(oldtext + newtext + "\n");
		mainOperator.ac.ta1.setCaretPosition(mainOperator.ac.ta1.getDocument().getLength()); // 스크롤을 맨 아래로 유지
	}

	
	
	void Listchanger1(String username) { // 클라이언트에서 GUI textarea에 한줄씩 나타내기 위한 작업

		listModel.addElement(username);
		list.setModel(listModel);

	}
}

class MyActionListener implements ActionListener { // 전송 버튼 리스너
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		String exit = e.getActionCommand();
		if (exit == "종료")
			System.exit(0);
	}
}
