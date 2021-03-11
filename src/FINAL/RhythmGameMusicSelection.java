package FINAL;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RhythmGameMusicSelection extends JPanel {

	private Image screenImage; // 배경이미지 생성을 위한 변수
	private Graphics screenGraphic; // 이미지의 그래픽을 받기 위한 변수
	// 스크린에 그릴때 필요한 것들 선언
	private JPanel primary;
	private JPanel THEFirstPanel;
	// upcall할때 사용할수 있도록 전 패널에서 받아온 것을 넣는 용이다./
	private JPanel MusicSelect = new BackGround();
	// .곡을 고르는 화면을 담는 Music Select 화면
	// background라는 내부 클래스로 정의 된다.

	private Image musicSelectionImage = new ImageIcon(Main.class.getResource("../images/headphone.jpg")).getImage();
	// 배경화면

	//////////////////////////////////////////
	private ImageIcon RightBasicImage = new ImageIcon(Main.class.getResource("../images/left.png"));
	private ImageIcon RightEnteredImage = new ImageIcon(Main.class.getResource("../images/left_Entered.png"));
	private ImageIcon LeftBasicImage = new ImageIcon(Main.class.getResource("../images/right.png"));
	private ImageIcon LeftEnteredImage = new ImageIcon(Main.class.getResource("../images/right_Entered.png"));
	private ImageIcon EasyBasicImage = new ImageIcon(Main.class.getResource("../images/Easy.png"));
	private ImageIcon EasyEnteredImage = new ImageIcon(Main.class.getResource("../images/Easy_selected.png"));
	private ImageIcon HardBasicImage = new ImageIcon(Main.class.getResource("../images/HARD.png"));
	private ImageIcon HardEnteredImage = new ImageIcon(Main.class.getResource("../images/HARD_selected.png"));
	private ImageIcon BackBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));
	private ImageIcon BackEnteredImage = new ImageIcon(Main.class.getResource("../images/backButtonEntered.png"));
	// 버튼에 대한 이미지들이다, 호버링할때 쉽게 이미지를 바꿀수 있도록 하게 해준다.

	// 버튼들을 선언한 부분이다. 각각 기본 이미지를 가지도록 설정하였다.
	private JButton Right = new JButton(RightBasicImage);
	private JButton Left = new JButton(LeftBasicImage);
	private JButton MButton;
	private JButton Easy = new JButton(EasyBasicImage);
	private JButton Hard = new JButton(HardBasicImage);
	private JButton Back = new JButton(BackBasicImage);
	// 버튼 선언이다.

	private boolean isMusicSelectionScreen;
	private boolean GameScreen;
	// 게임화면인지 참 거짓을 판별하는 불값이다
	ArrayList<Track> trackList = new ArrayList<Track>();
	// 트랙을 담은 리스트 선언
	private Music selectedMusic;// 지금 선택된 노래를 담기위한 Music 객체이다.
	private Image titleImage; // 초기 곡 선택화면에서 곡 화면 이름
	private Image selectedImage; // 초기 곡 선택화면에서 곡 사진
	private int nowSelected = 0;// 현재 선택된 트랙 번호

	public static Game game;
	private MusicSelectListener MSL;
	// game과 내부 마우스 리스너 선언
	// game은 프로그램 실행중에 항상 존재하게 된다.
	// keyListener를 통하여 손쉽게 game의 method에 접근할수 있도록 static으로 선언하였다.

	RhythmGameMusicSelection(JPanel Primary, JPanel One, JFrame th) {
		// trackList에 곡 내용들을 추가하는 부분이다.
		trackList.add(new Track("mirrormirror.png", "night.png", "deemoBG.jpg", "mirror_night.mp3",
				"shortVer mirrornight.mp3", "MIRROR NIGHT"));
		trackList.add(new Track("rock.png", "Queen.png", "rockyou.jpg", "cropped rockyou.mp3", "shortVer rockyou.mp3",
				"WE WILL ROCK YOU"));
		trackList.add(new Track("stargazerr.png", "STARGAZER .jpg", "stargazer.png", "StarGazer.mp3",
				"shortVer stargazer.mp3", "STARGAZER"));
		// 곡 정보, 앨범 표지, 백그라운드, 짧은 하이라이트 곡, 전체 곡, 곡 제목
		selectTrack(0);
		// 첫번째곡 시작
		MSL = new MusicSelectListener();

		MusicSelect.addKeyListener(new keyListenerLeft());
		MusicSelect.addKeyListener(new keyListenerRight());
		// 동시 키입력에서 문제가 생겨서 마우스 리스너를 2개를 따로 선언하고 안쪽에서
		// 부르게 되는 judge함수에 대해서도 2개로 나누어 쾌적한 동시키입력이 되도록 하였다.
		MusicSelect.setFocusable(true);
		// MusicSelect.requestFocusInWindow();

		GameScreen = false;
		primary = Primary;
		THEFirstPanel = One;
		isMusicSelectionScreen = true;
		// upcall필요한것 및 불값 초기화
		MusicSelect.setBounds(0, -30, 1280, 720);
		MusicSelect.setLayout(null);
		// musicSelect패널을 위로 30만큼 끌어올린후 시작한다.

		ImageIcon image = new ImageIcon(Main.class.getResource("../images/back.png"));
		Image pre = image.getImage();
		Image scale = pre.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		MButton = new JButton(new ImageIcon(scale));
		// 게임 실행중이 아닐때의 백버튼 선언

		Right.setBounds(-10, 350, 100, 100);
		Left.setBounds(1200, 350, 100, 100);
		MButton.setBounds(0, 30, 50, 50);
		Easy.setBounds(0, 570, 180, 160);
		Hard.setBounds(270, 570, 180, 160);
		Back.setBounds(0, 30, 50, 50);
		// 버튼 위치들 선언

		buttonInit(Right);
		buttonInit(Left);
		buttonInit(Easy);
		buttonInit(Hard);
		buttonInit(Back);
		buttonInit(MButton);
		// rhythmGame 클래스 참조
		Right.addMouseListener(MSL);
		MButton.addMouseListener(MSL);
		Left.addMouseListener(MSL);
		Hard.addMouseListener(MSL);
		Easy.addMouseListener(MSL);
		Back.addMouseListener(MSL);
		// 마우스 리스너 추가
		MusicSelect.add(Right);
		MusicSelect.add(Left);
		MusicSelect.add(MButton);
		MusicSelect.add(Hard);
		MusicSelect.add(Easy);
		MusicSelect.add(Back);
		// 추가
		Back.setVisible(false);
		// 인게임 내의 back 버튼이다--> 불가시화
		primary.add(MusicSelect);
		// primary에 더한다

	}

	private void buttonInit(JButton k) {
		k.setBorderPainted(false);
		k.setFocusPainted(false);
		k.setContentAreaFilled(false);
	}
	// rhythmgame클래스 참조

	class BackGround extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			screenImage = createImage(Main.screenWidth, Main.screenHeight);
			screenGraphic = screenImage.getGraphics();
			// 이미지 생성하고
			// 그래픽을 받은후에
			screenDraw((Graphics2D) screenGraphic);
			// 붓을 넘겨주고
			g.drawImage(screenImage, 0, 0, null);
			// 그 총 그래픽을 그린다
		}// 가장 처음

		// 그림 그리는 곳
		public void screenDraw(Graphics2D g) {

			g.drawImage(musicSelectionImage, 0, 0, null);// 배경화면 출력
			if (isMusicSelectionScreen) {
				// 노래 선택화면일때
				g.drawImage(selectedImage, 0, 150, null);
				g.drawImage(titleImage, 450, 150, null);
				// 곡 정보와,곡 앨범이미지 만을 그리고
			}
			if (GameScreen) {
				// 게임 화면일때는 게임으로 붓을 넘겨준다
				game.screenDraw(g);
			}
			paintComponents(g);
			try {
				Thread.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 너무 빨리 바뀌는 느낌이 들어서 조금 늦추었다.
			// 슬립
			repaint();
			// 계속 다시 그려주도록 한다
		}
	}

	public void selectTrack(int nowSelected) {
		if (selectedMusic != null)
			selectedMusic.close();

		titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage()))
				.getImage();
		selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage()))
				.getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);// 무한 반복
		selectedMusic.start();
		// 선택된 노래가 있으면 끄고
		// 이미지들을 trackList들로 받아오고
		// 노래도 해당하는 노래로 시작한다
	}

	public void selectRight() {
		if (nowSelected == trackList.size() - 1) {
			nowSelected = 0;
		} else {
			nowSelected++;
		}
		selectTrack(nowSelected);
		// 사이즈 넘으면 초기화 시키고
		// 아니면 증가시킨후
		// 노래 선택후 시작
	}

	public void selectLeft() {
		if (nowSelected == 0) {
			nowSelected = trackList.size() - 1;
		} else {
			nowSelected--;
		}
		selectTrack(nowSelected);// 시작
		// selectRight참조
	}

	public void gameStart(int nowSelected, String difficult) {
		if (selectedMusic != null)
			selectedMusic.close();

		isMusicSelectionScreen = false;
		Right.setVisible(false);
		Left.setVisible(false);
		MButton.setVisible(false);
		Easy.setVisible(false);
		Hard.setVisible(false);
		Back.setVisible(true);
		// 게임 시작할때
		// 뮤직 고르는 것에 해당하는 모든것들 불가시화

		musicSelectionImage = new ImageIcon(
				Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage())).getImage();
		// 해당 배경화면으로 바꾸고
		game = new Game(trackList.get(nowSelected).getTitleName(), difficult,
				trackList.get(nowSelected).getGameMusic());
		// 해당 게임으로 선언한후
		game.start();
		// 게임 시작

		MusicSelect.setFocusable(true);
		MusicSelect.requestFocusInWindow();
		// JPanel에서 키리스너가 잘 안들어서 혹시몰라 다시한번 사용하였다/
	}

	public void ReturnToMusicSelection() {

		Right.setVisible(true);
		Left.setVisible(true);
		MButton.setVisible(true);
		Easy.setVisible(true);
		Hard.setVisible(true);
		Back.setVisible(false);
		musicSelectionImage = new ImageIcon(Main.class.getResource("../images/headphone.jpg")).getImage();
		game.interrupt();
		game.close();
		// 게임에서 나올때 행동
		// 모든 것들 gameStart의 역순

		selectTrack(nowSelected);
		// 지금 선택된거 노래 틀고
		isMusicSelectionScreen = true;
		// 노래선택 화면 참으로 만든다
	}

	private void RETURNTOMAIN() {

		MusicSelect.setVisible(false);
		THEFirstPanel.setVisible(true);
		selectedMusic.close();
		// 메인으로 돌아갈때
		// 처음 2개 게임화면 있는것만 보이게 한다
	}

	// 마우스 이벤트에 따라 아이콘 바꾸고
	// 클릭시에 해당하는 행동들을 실행시킨다.
	private class MusicSelectListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == MButton) {// 되돌아가기 버튼을 눌렀을 때 메인으로 되돌아가기
				RETURNTOMAIN();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {// Hover Effect으로, 마우스 커서를 버튼 위에 올렸을 때, 손가락 모양으로 변하도록 설정
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == Right) {
				Right.setIcon(RightEnteredImage);
				Right.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == Left) {
				Left.setIcon(LeftEnteredImage);
				Left.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == Easy) {
				Easy.setIcon(EasyEnteredImage);
				Easy.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == Hard) {
				Hard.setIcon(HardEnteredImage);
				Hard.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == Back) {
				Back.setIcon(BackEnteredImage);
				Back.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {// Hover Effect으로, 마우스 커서가 버튼의 범위를 벗어났을 때, 원래 커서 모양으로 변하도록 설정
			Object obj = e.getSource();
			// TODO Auto-generated method stub
			if (obj == Right) {
				Right.setIcon(RightBasicImage);
				Right.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == Left) {
				Left.setIcon(LeftBasicImage);
				Left.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == Easy) {
				Easy.setIcon(EasyBasicImage);
				Easy.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == Hard) {
				Hard.setIcon(HardBasicImage);
				Hard.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == Back) {
				Back.setIcon(BackBasicImage);
				Back.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {// 마우스로 눌렀을 때
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == MButton) {// 되돌아가기
				RETURNTOMAIN();
			} else if (obj == Right) {// 오른쪽으로 넘기기
				selectRight();
			} else if (obj == Left) {// 왼쪽으로 넘기기
				selectLeft();
			} else if (obj == Easy) {// 쉬운 난이도 선택
				System.out.println("EASY");
				GameScreen = true;
				gameStart(nowSelected, "EASY");
			} else if (obj == Hard) {// 어려운 난이도 선택
				GameScreen = true;
				gameStart(nowSelected, "HARD");
			} else if (obj == Back) {// 되돌아가기
				GameScreen = false;
				isMusicSelectionScreen = false;
				ReturnToMusicSelection();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
