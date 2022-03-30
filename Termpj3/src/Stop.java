import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

//import Termpj3.ButtonChange;

public class Stop {
	public static JDialog stop;
	JPanel bts, btp;
	public static JButton bt1, bt2;
	JLabel label;
	
	Font font_s;
	Font font_m;
	Font font_l;
	Font font_xl;
	
	public Stop() throws FontFormatException, IOException {
		// ��Ʈ ����
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		font_s = font.deriveFont(20f); // ���� ��Ʈ
		font_m = font.deriveFont(30f);
		font_l = font.deriveFont(40f); // ū ��Ʈ
		font_xl = font.deriveFont(60f);
		// �Ͻ����� �˾� -----------------------------------------------------------------------------------------
		stop = new JDialog();
		stop.setTitle("�Ͻ�����");
		stop.setSize(400, 250);
		stop.setLocationRelativeTo(null); // ��� ��Ÿ���� ����
		stop.setForeground(new Color(249,247,248)); // ��������� �ȵ�..! ?????????????????????????????????????????
					
		// �Ͻ����� �˾� �ӿ��� ����ϱ�, ������ ��ư�� ���� Panel
		bts = new JPanel();
		bts.setLayout(new GridLayout(2, 0));

		label = new JLabel("������ �ٽ� �����Ͻðڽ��ϱ�?");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(font_s);
		bts.add(label);

		btp = new JPanel();
		bt1 = new ButtonChange("����ϱ�"); // ����ϱ� ��ư
		bt1.setFont(font_s);

		bt2 = new ButtonChange("������"); // ������ ��ư
		bt2.setFont(font_s);
					
		btp.add(bt1);
		btp.add(bt2);
		bts.add(btp);
		stop.add(bts);
		stop.setVisible(false); // �켱 �Ͻ����� �˾��� �Ⱥ��̰� �� �� ||��ư�� ������ ���̵��� ��.
	}
}