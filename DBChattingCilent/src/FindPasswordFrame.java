import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class FindPasswordFrame extends JFrame {

	MyConnector connector;
	Operator mainOperator = null;
	
	String aa;
	
	JPanel panel = new JPanel(new FlowLayout()); // 레이아웃 선언
	JButton Find = new JButton("Find"); // Button enter 선언
	JButton cancel = new JButton("cancel"); // Button enter 선언
	
	JTextField typeID = new JTextField(); 
	JTextField typeName = new JTextField(); // id 받은곳 선언
	JPasswordField typeNumber = new JPasswordField(); // password 받은곳 선언 받으면 ** < 처럼 나옴
	JTextField Pass = new JTextField();
	JLabel ID = new JLabel("I   D"); // 라벨 type id	
	JLabel name = new JLabel("NAME"); // 라벨 type id
	JLabel number = new JLabel("NUMBER"); // 라벨 type password*/
	
	

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

		panel.add(ID); // NAME 추가
		panel.add(typeID); // 입력된 NAME 추가
		panel.add(name); // NAME 추가
		panel.add(typeName); // 입력된 NAME 추가
		panel.add(number); // NUMBER 추가
		panel.add(typeNumber); // 입력된 NUMBER 추가
		panel.add(Pass);
		panel.add(Find);
		panel.add(cancel);

		setContentPane(panel);
		Find.addActionListener(ml1); // Find 버튼에 이벤트 리스너 추가
		cancel.addActionListener(ml1); // cancel 버튼에 이벤트 리스너 추가

		setResizable(false);
		setSize(400, 220);
		// 로그인 창을 화면 중앙에 배치시키기...
		Dimension frameSize = this.getSize(); // 프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		Pass.setEditable(false);

		setVisible(false);
	}

	class MyActionListener1 implements ActionListener { // 이벤트를 발생시킨 컴포넌트(소스)
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Find")) { // Find버튼을 누르면...
				aa = connector.sendFindPasswordInformation(typeID.getText(), typeName.getText(), typeNumber.getText());
				//id, name, number값들이 일치한다면
				Pass.setText(aa);
				//password택스트필드로 나타냄.
			} else if (b.getText().equals("cancel")) {
				mainOperator.lf.setVisible(true);
				dispose();
			}
		}
	}

}



