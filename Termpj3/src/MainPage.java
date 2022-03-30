import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class MainPage extends JFrame implements ActionListener{
	JPanel p1, p2, p3;
	JLabel title, imglabel, label;
	JRadioButton[] option;
	ImageIcon pre;
	ButtonChange type2;
	Start s = new Start();
	Color backcolor, fontcolor;
	static final int JFRAME_WIDTH = 600;
	static final int JFRAME_HEIGHT = 650;
	public static String foodtype;
	
	public MainPage() throws FontFormatException, IOException {
		// �� -----------------------------------------------------------------
		fontcolor = new Color(249,247,248);
		backcolor = new Color(64,114,175);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
		setResizable(false); // ũ�� ���� �� �ϰ� ����
		setLocationRelativeTo(null); // ��� ��Ÿ���� ����
		
		// ��Ʈ -----------------------------------------------------------------
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font font_s = font.deriveFont(20f); // ���� ��Ʈ
		Font font_l = font.deriveFont(40f); // ū ��Ʈ
				
		setTitle("�����̴� �������");
		setLayout(null);
		getContentPane().setBackground(backcolor);
		
		// ���� Ÿ��Ʋ -----------------------------------------------------------------
		title = new JLabel("�����̴� �������");
		title.setFont(font_l);
		title.setForeground(fontcolor); // �۾� �� ����
		title.setBounds(120, 80, 600, 50);
		add(title);
		
		// ���� ���� ���� -----------------------------------------------------------------
		p1 = new JPanel();
		p1.setLayout(new GridLayout(4, 0));
		
		option = new JRadioButton[4];
		ButtonGroup options = new ButtonGroup();
		
		option[0] = new JRadioButton(" �н�ƮǪ��", true);
		option[1] = new JRadioButton(" �Ͻ�");
		option[2] = new JRadioButton(" ����");
		option[3] = new JRadioButton(" ����Ʈ");
		
		
		for (int i = 0; i < 4; i ++) {
			option[i].addActionListener(this);
			option[i].setFont(font_s); // ��Ʈ ����
			option[i].setForeground(fontcolor); // �۾� ��
			option[i].setBackground(backcolor); // option ����
			options.add(option[i]);
		}
		
		for (int i = 0; i < 4; i ++) {
			p1.add(option[i]);
		}
		
		p1.setBounds(30, 200, 150, 200);
		add(p1);
		
		// ���� ������ -----------------------------------------------------------------
		p2 = new JPanel();
		pre = new ImageIcon("Images/fastfoodpre.gif");
		imglabel = new JLabel(pre);
		p2.setBounds(220, 200, 300, 300);
		p2.setBackground(backcolor);
		p2.add(imglabel);
		add(p2);
		
		// ���� �ɼ� ��ư -----------------------------------------------------------------
		p3 = new JPanel();
		p3.setBackground(backcolor);
		p3.setBounds(330, 530, 250, 50);
		
		type2 = new ButtonChange("���ӽ���");
		type2.addActionListener(this);
		type2.setFont(font_s);
		
		p3.add(type2);
		add(p3);
	}
	
	
	// �ɼ� ���� ������ �̹��� �ٸ��� ����
	@Override
	public void actionPerformed(ActionEvent e) {
		if (option[0].isSelected()==true) {
			imglabel.setIcon(new ImageIcon("Images/fastfoodpre.gif"));
			foodtype = "fastfood";
		} else if (option[1].isSelected()==true) {
			imglabel.setIcon(new ImageIcon("Images/japanesepre.gif"));
			foodtype = "sushi";
		} else if (e.getSource() == option[2]) {
			imglabel.setIcon(new ImageIcon("Images/fruitpre.gif"));
			foodtype = "fruit";
		} else if (e.getSource() == option[3]) {
			imglabel.setIcon(new ImageIcon("Images/dessertpre.gif"));
			foodtype = "dessert";
		}
		
		if (e.getSource() == type2) {
			s.setVisible(true);
		}
	}
}