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
		// 폰트 설정
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		font_s = font.deriveFont(20f); // 작은 폰트
		font_m = font.deriveFont(30f);
		font_l = font.deriveFont(40f); // 큰 폰트
		font_xl = font.deriveFont(60f);
		// 일시정지 팝업 -----------------------------------------------------------------------------------------
		stop = new JDialog();
		stop.setTitle("일시정지");
		stop.setSize(400, 250);
		stop.setLocationRelativeTo(null); // 가운데 나타나게 설정
		stop.setForeground(new Color(249,247,248)); // 배경적용이 안됨..! ?????????????????????????????????????????
					
		// 일시정지 팝업 속에서 계속하기, 나가기 버튼이 있을 Panel
		bts = new JPanel();
		bts.setLayout(new GridLayout(2, 0));

		label = new JLabel("게임을 다시 시작하시겠습니까?");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(font_s);
		bts.add(label);

		btp = new JPanel();
		bt1 = new ButtonChange("계속하기"); // 계속하기 버튼
		bt1.setFont(font_s);

		bt2 = new ButtonChange("나가기"); // 나가기 버튼
		bt2.setFont(font_s);
					
		btp.add(bt1);
		btp.add(bt2);
		bts.add(btp);
		stop.add(bts);
		stop.setVisible(false); // 우선 일시정지 팝업이 안보이게 한 후 ||버튼을 누르면 보이도록 함.
	}
}