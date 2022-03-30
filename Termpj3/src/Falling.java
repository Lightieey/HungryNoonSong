import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class Falling extends JFrame {
	GamePanel panel; // 게임 패널
	GameThread gThread; // 게임 Thread
	// JFrame의 크기
	static final int JFRAME_WIDTH = 600;
	static final int JFRAME_HEIGHT = 650;
	
	Font font_s;
	Font font_m;
	Font font_l;
	Font font_xl;
	
	Clip clip;	// 오디오
	String Foodtype;
	
	public static int finalscore;
	int speed;
	
	public Falling(String foodtype) throws FontFormatException, IOException {
		super("눈송이는 배고프숙");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
		setResizable(false); // 크기 변경 못 하게 설정
		setLocationRelativeTo(null); // 가운데 나타나게 설정
		Foodtype = foodtype;

		// 폰트 설정
		File font_file = new File("neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		font_s = font.deriveFont(20f); // 작은 폰트
		font_m = font.deriveFont(30f);
		font_l = font.deriveFont(40f); // 큰 폰트
		font_xl = font.deriveFont(60f);
		
		panel = new GamePanel();
		add(panel, BorderLayout.CENTER);
		
		gThread = new GameThread(); // Thread생성
		gThread.start(); // Thread 시작하여 run() 메소드 자동실행

		loadAudio("retrogame.wav");	// 오디오
		clip.start();
		
		// 키보드 이벤트 -------------------------------------------------------------------------
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_LEFT: // 왼쪽 키를 누르면 눈송이 x좌표가 -10씩 이동함.
					panel.dx -= 10;
					break;
				case KeyEvent.VK_RIGHT:
					panel.dx += 10; // 오른쪽 키를 누르면 눈송이 x좌표가 +10씩 이동함.
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

	private void loadAudio(String pathName) {	// 오디오 설정
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY); // 반복 재생
			
		} 
		catch (LineUnavailableException e) {e.printStackTrace();}
		catch (UnsupportedAudioFileException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	// 게임 패널 --------------------------------------------------------------------------------
	class GamePanel extends JPanel implements ActionListener { 
		Image backImg, nunsong, bomb;
		int width, height;
		int x, y, w, h; // 눈송이 좌표 및 이미지 절반 폭
		int dx = 0; // 눈송이 이동 방향
		int score; // 점수
		int heart; // 생명
		Random random = new Random();
		Stop s = new Stop();
		JDialog stop = s.stop;
		JButton button;
		JButton bt1 = s.bt1;
		JButton bt2 = s.bt2;

		// 음식 이미지를 저장하기 위한 ArrayList
		ArrayList<Item> foodList = new ArrayList<Item>(); // 랜덤한 음식 받는 arraylist
		ArrayList<Image> foodarr = new ArrayList<Image>(); // 음식 7가지 받는 arraylist
		ArrayList<Image> Heartarr = new ArrayList<Image>();	
		ArrayList<Image> DeadHeartarr = new ArrayList<Image>(); // 생명 리스트
		ArrayList<Item> BombList = new ArrayList<Item>();	// 폭탄 리스트

		public GamePanel() throws FontFormatException, IOException {
			setLayout(null);
			button = new JButton(new ImageIcon("Images/stopbutton1.png")); // 게임 멈춤 팝업 버튼
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			button.setContentAreaFilled(false);
			button.setBounds(520, 20, 50, 50);
			button.addActionListener(this);
			button.setFocusable(false); // 눈송이 키가 작동할 수 있도록 버튼에 포커싱을 하지 않음.
			add(button);
			
			bt1.addActionListener(this);
			bt2.addActionListener(this);
			
			width = getWidth();
			height = getHeight();

			backImg = Toolkit.getDefaultToolkit().getImage("Images/" + Foodtype + "backimg.png"); // 배경 이미지
			nunsong = Toolkit.getDefaultToolkit().getImage("Images/nunsong.png");// 눈송이 이미지
			bomb = Toolkit.getDefaultToolkit().getImage("Images/Bomb.png");
			
			for (int i = 0; i < 7; i++) { // 음식 이미지
				foodarr.add(Toolkit.getDefaultToolkit().getImage("Images/" + Foodtype + i + ".png"));
			}

			//생명부분 총 4번의 기회
			heart = 3;
			for (int i = 0; i < 3; i++) { // 하트(생명) 이미지
				Heartarr.add(Toolkit.getDefaultToolkit().getImage("Images/heart_left.png"));
				DeadHeartarr.add(Toolkit.getDefaultToolkit().getImage("Images/heart_dead.png"));
			}
			
			
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// 화면에 보여질 부분
			if (width == 0 || height == 0) { // 처음 호출시엔 느려서 보이지 않다가 이후 보임.
				width = getWidth();
				height = getHeight();
				backImg = backImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				nunsong = nunsong.getScaledInstance(70, 90, Image.SCALE_SMOOTH);
				x = width / 2;
				y = 475;
				w = 35;
				h = 25;
			}
			g.drawImage(backImg, 0, 0, this); // 배경 이미지 그리기
			for (Item f : foodList) {
				g.drawImage(f.img, f.x - f.w, f.y - f.h, this); // 음식 이미지 그리기
			}
			
			g.drawImage(nunsong, x - w, y - h, this); // 눈송이 이미지 그리기
			if (score <= 0 ) {
				score = 0;
			}
			
			g.setColor(Color.white);
			g.setFont(font_xl);
			g.drawString("" + score, 258, 58); // 점수 표시 나타내기
			g.setColor(Color.black);
			g.setFont(font_xl);
			g.drawString("" + score, 260, 60); // 점수 표시 나타내기
			
			for (int i=0; i<3;i++) {
				g.drawImage(DeadHeartarr.get(i), 20 + (i)*30, 20, 30, 30, this);
			}
			for (int i = 0; i < Heartarr.size(); i++) {
				g.drawImage(Heartarr.get(i), 20 + i*30, 20, 30, 30, this); // 생명 이미지 그리기
			}
			
			for (Item b : BombList) {
				g.drawImage(b.img, b.x - b.w, b.y - b.h, this);
			}
		
		}

		// 눈송이 이동 -------------------------
		void move() { 
			x += dx;
			// 눈송이 이미지가 화면 밖으로 나가지 않도록 설정
			if (x < w) // 왼쪽 끝
				x = w;
			if (x > width - w) // 오른쪽 끝
				x = width - w;
		}

		// 음식 생성 --------------------------
		void makeFood() { 
			if (width == 0 || height == 0)
				return;
			
			// 음식 리스트에 넣기
			int n = new Random().nextInt(20);
			if (n == 0) {
				foodList.add(new Item(foodarr.get(random.nextInt(7)), width, height)); // 음식 랜덤으로 떨어지도록 함.
			}
			for (int i = foodList.size() - 1; i >= 0; i--) {
				Item f = foodList.get(i);
				f.move();
				if (f.isDead)
					foodList.remove(i);
			}
		}
		
		// 폭탄 생성 --------------------------
		void makeBomb() throws FontFormatException, IOException {
			if (width == 0 || height == 0)
				return;
			// 폭탄 리스트에 넣기, 생명 설정
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
		
		// 충돌 여부 ------------------------------
		void checkCollision() { 
			for (Item b : BombList) {
				int left = b.x - b.w;
				int right = b.x + b.w;
				int top = b.y - b.h;
				int bottom = b.y + b.h;
				if (x > left && x < right && y > top && y < bottom) {
					b.isDead = true; // 충돌
					
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
					score -= 50; // 충돌했을 경우 점수 감소
					
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
					f.isDead = true; // 충돌
					score += 10*(random.nextInt(3)+1); // 충돌했을 경우 점수 증가
				}
			}
		}

		// 일시정지 ----------------------------------------------------------------------
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button) {
				clip.stop();
				gThread.suspend(); // ||버튼을 누르면 Thread가 suspend되고 일시정지 팝업이 뜸.
				stop.setVisible(true);
			} else if (e.getSource() == bt1) {
				stop.setVisible(false); // 계속하기 버튼을 누르면 일시정지 팝업이 사라지고 Thread가 resume됨.
				clip.start();
				gThread.resume();
				button.setFocusable(false);
			} else if (e.getSource() == bt2) {
				stop.setVisible(false);
				dispose();
			}
		}
	}

	// 게임 스레드 ----------------------------------------------------------------------------
	class GameThread extends Thread { 
		int speed = 20; // 떨어지는 속도

		@Override
		public void run() { // 음식이 떨어지는 부분
			while (true) {
				panel.repaint(); // 화면 갱신
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
		boolean isDead = false; // 생존 여부

		public Item(Image Img, int width, int height) {
			this.width = width;
			this.height = height;
			img = Img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			w = 25;
			h = 25;
			x = new Random().nextInt(width - 2 * w) + w; // 25 ~ 575
			y = -h;
		}

		void move() { // 음식이 떨어지는 효과
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
		
		
		System.out.println("유저이름 : " + s.user);
		System.out.println("최종점수  : " + finalscore);
		r.scoreadd(s.user,finalscore);
		r.scoreprint();
	}
}