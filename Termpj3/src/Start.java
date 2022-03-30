import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//시작하시겠습니까 페이지 ---------------------------------------------------------------------
public class Start extends JFrame implements ActionListener {

	JPanel p1, p2, p3;
	JLabel userName, alert;
	JTextField name;
	ButtonChange yes, no;
	public static String user;
	//Falling2 f2 = new Falling2();
	
	public Start() throws FontFormatException, IOException {
		super();
		setTitle("게임시작");
		setSize(400, 250);
		setResizable(false); // 크기 변경 못 하게 설정
		setLocationRelativeTo(null); // 가운데 나타나게 설정
		setLayout(null);
		
		// 폰트
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font font_s = font.deriveFont(20f);
		
		// 패널 1
		p1 = new JPanel();
		userName = new JLabel("User Name  ");
		userName.setFont(font_s);
		name = new JTextField(20);
		name.setFont(font_s);
	
		p1.add(userName);
		p1.add(name);
		p1.setBounds(5, 40, 390, 40);
		
		// 패널 2
		p2 = new JPanel();
		alert = new JLabel("게임을 시작하시겠습니까?");
		alert.setFont(font_s);
		p2.add(alert);
		p2.setBounds(5, 80, 390, 50);
		
		// 패널 3
		p3 = new JPanel();
		yes = new ButtonChange("예");
		yes.setFont(font_s);
		yes.addActionListener(this); // 게임 화면으로 이동하도록 설정해야 함
	
		no = new ButtonChange("아니오");
		no.setFont(font_s);
		no.addActionListener(this);
	
		p3.add(yes);
		p3.add(no);
		p3.setBounds(5, 130, 390, 50);
		
		add(p1);
		add(p2);
		add(p3);
	}

	public void actionPerformed(ActionEvent e) {
		user = name.getText(); // textfield에서 입력받은 값 가져오기
		if(e.getSource() == yes ) {
			try {
				MainPage mp = new MainPage();
				Falling f2 = new Falling(mp.foodtype);
				f2.setVisible(true);
			} catch (FontFormatException | IOException e1) {
				e1.printStackTrace();
			}
			setVisible(false);
		} else if(e.getSource() == no) {
			dispose();
			setVisible(false);
				
		}
	}
}