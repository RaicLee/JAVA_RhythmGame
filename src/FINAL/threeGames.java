package FINAL;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class threeGames extends JFrame {

	//기본적으로 threeGames클래스는 JFrame을 상속받아 Frame으로서 역할을 한다.
	private JFrame t;//자신을 담을 변수
	private JPanel primary = new JPanel();// 모든 정보들을 담을 패널(게임 제작을 각각 따로 진행했기에 합치기 쉽게 만들기 위한 장치이다)
	private JPanel ScreenOne = new JPanel();// game image을 담을 패널, 즉 밑에 2개들을 담을 패널
	private JPanel game1 = new JPanel();// 오델로 예시가 나오는 패널
	private JPanel game2 = new JPanel();// 테트리스 예시가 나오는 패널

	private Mou ML;// game1,game2패널 위에서 하는 행동들에 대응할수있도록 해주는 마우스 리스너

	// game menu mouseExited image
	private ImageIcon preImage1, preImage2;// 마우스 올리기전 이미지를 나타낸다
	// game menu mouseEntered image
	private ImageIcon postImage1, postImage2;//마우스를 올린후 이미지를 나타낸다

	// Image Icon이미지 사이즈 변환하기 위한 변수
	private Image img, scale;
	// game name label
	private JLabel img1, img2;

	// menu-bar
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menubar.jpg")));

	// program exit button mouseEntered image
	private ImageIcon exitBEImage = new ImageIcon(Main.class.getResource("../images/exitbutton.png"));

	// program exit button mouseExited image
	private ImageIcon exitBDImage = new ImageIcon(Main.class.getResource("../images/exitbutton_disable.png"));
	private JButton exitButton = new JButton(exitBDImage);

	// get X, Y from mouse Input
	private int mouseX, mouseY;


	public threeGames() {
		t = this;
		initialize();
		// 창에 대한 initialize

		// game menu mouseExited image

		preImage1 = new ImageIcon(Main.class.getResource("../images/logo1.png"));
		img = preImage1.getImage();
		scale = img.getScaledInstance(635, 690, java.awt.Image.SCALE_SMOOTH);
		preImage1 = new ImageIcon(scale);

		preImage2 = new ImageIcon(Main.class.getResource("../images/logo2.png"));
		img = preImage2.getImage();
		scale = img.getScaledInstance(635, 690, java.awt.Image.SCALE_SMOOTH);
		preImage2 = new ImageIcon(scale);

		// game menu mouseEntered image
		postImage1 = new ImageIcon(Main.class.getResource("../images/othello.gif"));
		postImage2 = new ImageIcon(Main.class.getResource("../images/rhythm.gif"));

		// 프레임 바로위에 들어가는 primary에 대한 초기화
		primary.setPreferredSize(new Dimension(Main.screenWidth, Main.screenHeight));
		primary.setLayout(null);
		primary.setBounds(0, 30, 1280, 720);// JFrame의 위치 0,30 으로부터 가로 1280 세로 720
		primary.setBackground(Color.red);

		// game 실행Panel 
		ScreenOne.setPreferredSize(new Dimension(1280, 720));
		ScreenOne.setLayout(null);
		ScreenOne.setBounds(0, -30, 1280, 720);// primary기준으로 (0,-30)에 가로 1280 세로 720의 크기만큼을 준다
		ScreenOne.setBackground(Color.black);// 배경색 갈색으로 설정
		// primary가 30만큼 내려왔으니 원위치 시키려면
		ML = new Mou();

		// 메뉴바와 종료버튼은 직접 프레임에 가져다 넣는다.
		ExampleImagesEdit();

		// display image
		ScreenOne.add(game1);
		ScreenOne.add(game2);
		primary.add(ScreenOne);

		// 타이틀바
		exitButtonEdit();
		add(exitButton);
		menuBarEdit();
		add(menuBar);

		// 타이틀바
		this.getContentPane().add(primary);
		setVisible(true);

	}// twoGames() 생성자

	private void initialize() {
		// 프레임 설정

		// delete frame title bar
		setUndecorated(true);
		// set Size
		setSize(new Dimension(Main.screenWidth, Main.screenHeight));
		// setBackground(new Color(0, 0, 0, 0));

		// can not change frame size
		setResizable(true);

		// display frame on the center of screen
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
	}// initialize()

	// exit Button setting
	private void exitButtonEdit() {
		exitButton.setBounds(1250, 0, 30, 30);// 맨 오른쪽 끝에 놓고

		// remove border
		exitButton.setBorderPainted(false);
		// JButton이 Focus됐을 때 윤곽선 생기지 않게 함
		exitButton.setFocusPainted(false);
		// JButton 내용영역 안채움
		exitButton.setContentAreaFilled(false);
		// 버튼 색깔및 속성 없앤다
		exitButton.addMouseListener(ML);
	}// exitButtonEdit

	// setting menu-bar
	private void menuBarEdit() {
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(ML);
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			// menu bar를 움직이면 그것에 맞게 화면창이 움직이게 하는 event
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
	}// menuBarEdit

	private void ExampleImagesEdit() {
		img1 = new JLabel(preImage1);
		img2 = new JLabel(preImage2);

		// game1에 대한 패널 설정및 마우스 리스너 설정
		game1.setPreferredSize(new Dimension(635, 690));
		game1.setBounds(0, 30, 635, 690);
		game1.addMouseListener(ML);
		game1.setLayout(null);
		img1.setBounds(0, 0, 635, 690);
		game1.add(img1);

		// game2에 대한 패널 설정및 마우스 리스너 설정
		game2.setPreferredSize(new Dimension(635, 690));
		game2.setBounds(645, 30, 635, 690);
		game2.addMouseListener(ML);
		game2.setLayout(null);
		img2.setBounds(0, 0, 635, 690);
		game2.add(img2);

	}// Example Image Edit

	// 마우스 리스너 설정
	private class Mou implements MouseListener {
		public void mousePressed(MouseEvent e) {
			Object obj = e.getSource();

			if (obj == exitButton) {// click exit button
				System.exit(0);
			} else if (obj == menuBar) { // click menu bar
				mouseX = e.getX();
				mouseY = e.getY();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == game1) { // othello

				System.out.println("START Othello");
				new PanelReversi(primary, ScreenOne);
				ScreenOne.setVisible(false);

			} else if (obj == game2) { // rhythm game

				System.out.println("START Rhythm Game");
				new RhythmGame(primary, ScreenOne, t);
				ScreenOne.setVisible(false);
				
			}
			

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == game1) { // othello
				System.out.println("Play preview GAME1");
				img1.setIcon(postImage1);
				img1.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == game2) { // Rhythm game
				System.out.println("Play preview GAME2");
				img2.setIcon(postImage2);
				img2.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == exitButton) { // exit button
				exitButton.setIcon(exitBEImage);
				// change cursor
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// stop Image
			Object obj = e.getSource();
			if (obj == game1) {// Othello
				System.out.println("Stop preview GAME1");
				img1.setIcon(preImage1);
				img1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == game2) {// Rhythm game
				System.out.println("Stop preview GAME2");
				img2.setIcon(preImage2);
				img2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == exitButton) {
				exitButton.setIcon(exitBDImage);
				// change cursor
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//
			Object obj = e.getSource();
			if (obj == game1) {
				System.out.println("Stop preview GAME1");
			} else if (obj == game2) {
				System.out.println("Stop preview GAME2");

			}
		}
	}// MouseListener

}