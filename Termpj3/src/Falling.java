import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class Falling extends JFrame {
	GamePanel panel; // ���� �г�
	GameThread gThread; // ���� Thread
	// JFrame�� ũ��
	static final int JFRAME_WIDTH = 600;
	static final int JFRAME_HEIGHT = 650;
	
	Font font_s;
	Font font_m;
	Font font_l;
	Font font_xl;
	
	Clip clip;	// �����
	String Foodtype;
	
	public static int finalscore;
	int speed;
	
	public Falling(String foodtype) throws FontFormatException, IOException {
		super("�����̴� �������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
		setResizable(false); // ũ�� ���� �� �ϰ� ����
		setLocationRelativeTo(null); // ��� ��Ÿ���� ����
		Foodtype = foodtype;

		// ��Ʈ ����
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		font_s = font.deriveFont(20f); // ���� ��Ʈ
		font_m = font.deriveFont(30f);
		font_l = font.deriveFont(40f); // ū ��Ʈ
		font_xl = font.deriveFont(60f);
		
		panel = new GamePanel();
		add(panel, BorderLayout.CENTER);
		
		gThread = new GameThread(); // Thread����
		gThread.start(); // Thread �����Ͽ� run() �޼ҵ� �ڵ�����

		loadAudio("retrogame.wav");	// �����
		clip.start();
		
		// Ű���� �̺�Ʈ -------------------------------------------------------------------------
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_LEFT: // ���� Ű�� ������ ������ x��ǥ�� -10�� �̵���.
					panel.dx -= 10;
					break;
				case KeyEvent.VK_RIGHT:
					panel.dx += 10; // ������ Ű�� ������ ������ x��ǥ�� +10�� �̵���.
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_LEFT:
					panel.dx = 0;
					break;
				case KeyEvent.VK_RIGHT:
					panel.dx = 0;
					break;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	private void loadAudio(String pathName) {	// ����� ����
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY); // �ݺ� ���
			
		} 
		catch (LineUnavailableException e) {e.printStackTrace();}
		catch (UnsupportedAudioFileException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	// ���� �г� --------------------------------------------------------------------------------
	class GamePanel extends JPanel implements ActionListener { 
		Image backImg, nunsong, bomb;
		int width, height;
		int x, y, w, h; // ������ ��ǥ �� �̹��� ���� ��
		int dx = 0; // ������ �̵� ����
		int score; // ����
		int heart; // ����
		Random random = new Random();
		Stop s = new Stop();
		JDialog stop = s.stop;
		JButton button;
		JButton bt1 = s.bt1;
		JButton bt2 = s.bt2;

		// ���� �̹����� �����ϱ� ���� ArrayList
		ArrayList<Item> foodList = new ArrayList<Item>(); // ������ ���� �޴� arraylist
		ArrayList<Image> foodarr = new ArrayList<Image>(); // ���� 7���� �޴� arraylist
		ArrayList<Image> Heartarr = new ArrayList<Image>();	
		ArrayList<Image> DeadHeartarr = new ArrayList<Image>(); // ���� ����Ʈ
		ArrayList<Item> BombList = new ArrayList<Item>();	// ��ź ����Ʈ

		public GamePanel() throws FontFormatException, IOException {
			setLayout(null);
			button = new JButton(new ImageIcon("Images/stopbutton1.png")); // ���� ���� �˾� ��ư
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			button.setContentAreaFilled(false);
			button.setBounds(520, 20, 50, 50);
			button.addActionListener(this);
			button.setFocusable(false); // ������ Ű�� �۵��� �� �ֵ��� ��ư�� ��Ŀ���� ���� ����.
			add(button);
			
			bt1.addActionListener(this);
			bt2.addActionListener(this);
			
			width = getWidth();
			height = getHeight();

			backImg = Toolkit.getDefaultToolkit().getImage("Images/" + Foodtype + "backimg.png"); // ��� �̹���
			nunsong = Toolkit.getDefaultToolkit().getImage("Images/nunsong.png");// ������ �̹���
			bomb = Toolkit.getDefaultToolkit().getImage("Images/Bomb.png");
			
			for (int i = 0; i < 7; i++) { // ���� �̹���
				foodarr.add(Toolkit.getDefaultToolkit().getImage("Images/" + Foodtype + i + ".png"));
			}

			//����κ� �� 4���� ��ȸ
			heart = 3;
			for (int i = 0; i < 3; i++) { // ��Ʈ(����) �̹���
				Heartarr.add(Toolkit.getDefaultToolkit().getImage("Images/heart_left.png"));
				DeadHeartarr.add(Toolkit.getDefaultToolkit().getImage("Images/heart_dead.png"));
			}
			
			
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// ȭ�鿡 ������ �κ�
			if (width == 0 || height == 0) { // ó�� ȣ��ÿ� ������ ������ �ʴٰ� ���� ����.
				width = getWidth();
				height = getHeight();
				backImg = backImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				nunsong = nunsong.getScaledInstance(70, 90, Image.SCALE_SMOOTH);
				x = width / 2;
				y = 475;
				w = 35;
				h = 25;
			}
			g.drawImage(backImg, 0, 0, this); // ��� �̹��� �׸���
			for (Item f : foodList) {
				g.drawImage(f.img, f.x - f.w, f.y - f.h, this); // ���� �̹��� �׸���
			}
			
			g.drawImage(nunsong, x - w, y - h, this); // ������ �̹��� �׸���
			if (score <= 0 ) {
				score = 0;
			}
			
			g.setColor(Color.white);
			g.setFont(font_xl);
			g.drawString("" + score, 258, 58); // ���� ǥ�� ��Ÿ����
			g.setColor(Color.black);
			g.setFont(font_xl);
			g.drawString("" + score, 260, 60); // ���� ǥ�� ��Ÿ����
			
			for (int i=0; i<3;i++) {
				g.drawImage(DeadHeartarr.get(i), 20 + (i)*30, 20, 30, 30, this);
			}
			for (int i = 0; i < Heartarr.size(); i++) {
				g.drawImage(Heartarr.get(i), 20 + i*30, 20, 30, 30, this); // ���� �̹��� �׸���
			}
			
			for (Item b : BombList) {
				g.drawImage(b.img, b.x - b.w, b.y - b.h, this);
			}
		
		}

		// ������ �̵� -------------------------
		void move() { 
			x += dx;
			// ������ �̹����� ȭ�� ������ ������ �ʵ��� ����
			if (x < w) // ���� ��
				x = w;
			if (x > width - w) // ������ ��
				x = width - w;
		}

		// ���� ���� --------------------------
		void makeFood() { 
			if (width == 0 || height == 0)
				return;
			
			// ���� ����Ʈ�� �ֱ�
			int n = new Random().nextInt(20);
			if (n == 0) {
				foodList.add(new Item(foodarr.get(random.nextInt(7)), width, height)); // ���� �������� ���������� ��.
			}
			for (int i = foodList.size() - 1; i >= 0; i--) {
				Item f = foodList.get(i);
				f.move();
				if (f.isDead)
					foodList.remove(i);
			}
		}
		
		// ��ź ���� --------------------------
		void makeBomb() throws FontFormatException, IOException {
			if (width == 0 || height == 0)
				return;
			// ��ź ����Ʈ�� �ֱ�, ���� ����
			int n = new Random().nextInt(70);
			if (n == 0) {
				BombList.add(new Item(bomb, width, height));
			}
			for (int i = BombList.size() - 1; i >= 0; i--) {
				Item b = BombList.get(i);
				b.move();
				if (b.isDead) {
					BombList.remove(i);
					
				}
			}
		}
		
		// �浹 ���� ------------------------------
		void checkCollision() { 
			for (Item b : BombList) {
				int left = b.x - b.w;
				int right = b.x + b.w;
				int top = b.y - b.h;
				int bottom = b.y + b.h;
				if (x > left && x < right && y > top && y < bottom) {
					b.isDead = true; // �浹
					
					if (heart == 0) {
						finalscore = score;
						System.out.println(finalscore);
						try {
							clip.stop();
							adddb();
							dispose();
							new GameOverPage(finalscore);
						} catch (FontFormatException | IOException e) {
							e.printStackTrace();
						}
						
						gThread.stop();
					}
					
					System.out.println(""+heart);
					score -= 50; // �浹���� ��� ���� ����
					
					Heartarr.remove(--heart);
					System.out.println("Bomb");
				}
			}
			
			for (Item f : foodList) {
				int left = f.x - f.w;
				int right = f.x + f.w;
				int top = f.y - f.h;
				int bottom = f.y + f.h;
				if (x > left && x < right && y > top && y < bottom) {
					f.isDead = true; // �浹
					score += 10*(random.nextInt(3)+1); // �浹���� ��� ���� ����
				}
			}
		}

		// �Ͻ����� ----------------------------------------------------------------------
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button) {
				clip.stop();
				gThread.suspend(); // ||��ư�� ������ Thread�� suspend�ǰ� �Ͻ����� �˾��� ��.
				stop.setVisible(true);
			} else if (e.getSource() == bt1) {
				stop.setVisible(false); // ����ϱ� ��ư�� ������ �Ͻ����� �˾��� ������� Thread�� resume��.
				clip.start();
				gThread.resume();
				button.setFocusable(false);
			} else if (e.getSource() == bt2) {
				stop.setVisible(false);
				dispose();
			}
		}
	}

	// ���� ������ ----------------------------------------------------------------------------
	class GameThread extends Thread { 
		int speed = 20; // �������� �ӵ�

		@Override
		public void run() { // ������ �������� �κ�
			while (true) {
				panel.repaint(); // ȭ�� ����
				panel.makeFood();
				try {
					panel.makeBomb();
				} catch (FontFormatException | IOException e1) {
					e1.printStackTrace();
				}
				panel.move();
				panel.checkCollision();
				try {
					sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class Item {
		Image img;
		int x, y, w, h;
		int width, height;
		boolean isDead = false; // ���� ����

		public Item(Image Img, int width, int height) {
			this.width = width;
			this.height = height;
			img = Img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			w = 25;
			h = 25;
			x = new Random().nextInt(width - 2 * w) + w; // 25 ~ 575
			y = -h;
		}

		void move() { // ������ �������� ȿ��
			y += 5;
			if (y > height + h) {
				isDead = true;
			}
		}
	}

	public void adddb() throws FontFormatException, IOException {
		Main m = new Main();
		Start s = new Start();
		Ranking r = new Ranking();
		
		
		System.out.println("�����̸� : " + s.user);
		System.out.println("��������  : " + finalscore);
		r.scoreadd(s.user,finalscore);
		r.scoreprint();
	}
}