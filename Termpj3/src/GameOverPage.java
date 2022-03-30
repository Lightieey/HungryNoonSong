import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class GameOverPage extends JPanel implements ActionListener{
	JFrame f; 
	ButtonChange end, retry;
	
	public GameOverPage(int userScore) throws FontFormatException, IOException{
		f = new JFrame("Game Over");
		setBackground(new Color(0, 0, 0, 100));
		setLayout(null);
		Main m = new Main();
		Ranking r = new Ranking();
		
		// 폰트
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font font_s = font.deriveFont(20f); // 큰 폰트
		Font font_l = font.deriveFont(35f); // 작은 폰트
		Font font_xl = font.deriveFont(60f);
		Font font_xxl = font.deriveFont(80f); // 더 큰 폰트
		
		JLabel go = new JLabel("Game Over");
		go.setFont(font_xxl);
		go.setForeground(Color.white);
		go.setBounds(115, 17, 500, 150);
		
		JLabel gameover = new JLabel("Game Over");
		gameover.setFont(font_xxl);
		gameover.setForeground(new Color(38,68,104));
		gameover.setBounds(120, 20, 500, 150);
		
		JLabel sc = new JLabel("" + userScore);
		sc.setFont(font_xl);	
		sc.setForeground(Color.white);
		sc.setBounds(235, 108, 300, 150);
		
		JLabel score = new JLabel("" + userScore);
		score.setFont(font_xl);
		score.setForeground(new Color(38,68,104));
		score.setBounds(240, 110, 300, 150);
		
		add(gameover);
		add(go);
		add(score);
		add(sc);
		
		JPanel p1 = new JPanel();
		p1.setBounds(270, 540, 250, 50);
		p1.setBackground(new Color(218,226,239));
		
		end = new ButtonChange("나가기");
		end.addActionListener(this);
		end.setFont(font_s);
		retry = new ButtonChange("다시하기");
		retry.addActionListener(this);
		retry.setFont(font_s);
		
		p1.add(end);
		p1.add(retry);
		add(p1);
		
		JPanel p2 = new JPanel();
		p2.setBounds(95,260,400,250);
		
		String username[] = {"", "", "", "", ""};
		String userscore[] = {"", "", "", "", ""};
		
		for (int i=0; i<r.namelist.size(); i++) {
			if (i == 5) break;
			username[i] = ("" + r.namelist.get(i));
			userscore[i] = ("" + r.scorelist.get(i));
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 3));
		JLabel scores[] = new JLabel[5];
		JLabel names[] = new JLabel[5];
		for (int i = 0; i < 5; i++) {
			names[i] = new JLabel(username[i]);
			names[i].setFont(font_l);
			names[i].setForeground(new Color(250,250,250,200));
			names[i].setHorizontalAlignment(SwingConstants.CENTER);
			names[i].setBounds(220, 310 + i*40, 120, 30);
		}
		for (int i = 0; i < 5; i++) {
			scores[i] = new JLabel(userscore[i]);
			scores[i].setFont(font_l);
			scores[i].setForeground(new Color(250,250,250,200));
			scores[i].setHorizontalAlignment(SwingConstants.CENTER);
			scores[i].setBounds(340, 310 + i*40, 120, 30);
		}
		
		JLabel h2lank = new JLabel("랭킹");
		h2lank.setFont(font_l);
		h2lank.setForeground(Color.white);
		h2lank.setHorizontalAlignment(SwingConstants.CENTER);
		h2lank.setBounds(100, 270, 120, 30);
		add(h2lank);
		
		JLabel hlank = new JLabel("랭킹");
		hlank.setFont(font_l);
		hlank.setHorizontalAlignment(SwingConstants.CENTER);
		hlank.setBounds(98, 268, 120, 30);
		add(hlank);
		
		JLabel h2name = new JLabel("이름");
		h2name.setFont(font_l);
		h2name.setForeground(Color.white);
		h2name.setHorizontalAlignment(SwingConstants.CENTER);
		h2name.setBounds(220, 270, 120, 30);
		add(h2name);
		
		JLabel hname = new JLabel("이름");
		hname.setFont(font_l);
		hname.setHorizontalAlignment(SwingConstants.CENTER);
		hname.setBounds(218, 268, 120, 30);
		add(hname);
		
		JLabel h2score = new JLabel("점수");
		h2score.setFont(font_l);
		h2score.setForeground(Color.white);
		h2score.setHorizontalAlignment(SwingConstants.CENTER);
		h2score.setBounds(340, 270, 120, 30);
		add(h2score);
		
		JLabel hscore = new JLabel("점수");
		hscore.setFont(font_l);
		hscore.setHorizontalAlignment(SwingConstants.CENTER);
		hscore.setBounds(338, 268, 120, 30);
		add(hscore);
		
		
		for (int i = 0; i < 5; i++) {
			JLabel lank = new JLabel(""+ (i+1));
			lank.setFont(font_l);
			lank.setForeground(new Color(250,250,250,200));
			lank.setHorizontalAlignment(SwingConstants.CENTER);
			lank.setBounds(100, 310 + i*40, 120, 30);
			add(lank);
			add(names[i]);
			add(scores[i]);
		}

	
		f.add(this);
		f.setSize(600, 650);
		f.setVisible(true);
		f.setBackground(new Color(64,114,175));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false); // 크기 변경 못 하게 설정
		f.setLocationRelativeTo(null); // 가운데 나타나게 설정
	}

	protected void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g; 
		
		g2.setColor(new Color(218,226,239));
		g2.fillRoundRect(18,18,550,578,40,40);
		
		g2.setColor(new Color(0,0,0,92));
		g2.fillRoundRect(95,260,400,250,40,40);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == end) {
			System.exit(0);
		} else if (e.getSource() == retry) {
			f.dispose();
		}	
	}
}