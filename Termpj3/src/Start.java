import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//�����Ͻðڽ��ϱ� ������ ---------------------------------------------------------------------
public class Start extends JFrame implements ActionListener {

	JPanel p1, p2, p3;
	JLabel userName, alert;
	JTextField name;
	ButtonChange yes, no;
	public static String user;
	//Falling2 f2 = new Falling2();
	
	public Start() throws FontFormatException, IOException {
		super();
		setTitle("���ӽ���");
		setSize(400, 250);
		setResizable(false); // ũ�� ���� �� �ϰ� ����
		setLocationRelativeTo(null); // ��� ��Ÿ���� ����
		setLayout(null);
		
		// ��Ʈ
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font font_s = font.deriveFont(20f);
		
		// �г� 1
		p1 = new JPanel();
		userName = new JLabel("User Name  ");
		userName.setFont(font_s);
		name = new JTextField(20);
		name.setFont(font_s);
	
		p1.add(userName);
		p1.add(name);
		p1.setBounds(5, 40, 390, 40);
		
		// �г� 2
		p2 = new JPanel();
		alert = new JLabel("������ �����Ͻðڽ��ϱ�?");
		alert.setFont(font_s);
		p2.add(alert);
		p2.setBounds(5, 80, 390, 50);
		
		// �г� 3
		p3 = new JPanel();
		yes = new ButtonChange("��");
		yes.setFont(font_s);
		yes.addActionListener(this); // ���� ȭ������ �̵��ϵ��� �����ؾ� ��
	
		no = new ButtonChange("�ƴϿ�");
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
		user = name.getText(); // textfield���� �Է¹��� �� ��������
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