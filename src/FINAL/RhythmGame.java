package FINAL;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class RhythmGame {

	private JFrame th;
	//frame을 upcall사용할때 사용할수 있도록 가능케 하는 th 변수 선언
	private JPanel primary;
	private JPanel THEFirstScreen;
	//primary는 합치기 쉽게하기 위한 도구엿던 빨간 색깔을 지닌 패널을 upcall할때 필요한 것이고
	//THEFirstScreen은 초반에 시작할때 게임패널2개 있는것을 upcall형식으로 부를때 필요한 것이다.
	
	
	
	
	private ImageIcon introBackground = new ImageIcon(Main.class.getResource("../images/crowd.jpg"));
	// 어떤 배경화면을 사용할지를 정한다.
	
	private JPanel MusicPanel = new JPanel();
	private JLabel BackGround = new JLabel(introBackground);
	private JLabel LOGO= new JLabel(new ImageIcon(Main.class.getResource("../images/logo.png")));
	//MUSICPANEL은 JPanel이고
	//BACKGROUND는 JLABEL로써 배경화면을 담는다.
	//LOGO는 로고 이미지를 담는 용이다.
	 
	
	
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/start.png"));
	private ImageIcon startButtonEnteredImage = new ImageIcon(
			Main.class.getResource("../images/start_Entered.png"));
	private ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));
	private ImageIcon backButtonEnteredImage = new ImageIcon(
			Main.class.getResource("../images/backButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/end.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/end_Entered.png"));
	//STARTBUTTON,BACKBUTTON,EXITBUTTON에 대한 이미지를 정의하였다
	//각각 사용자가 마우스를 ENTER했을때와 EXIT했을때에 사용될 이미지들이다.
	//즉, 호버링할때 이미지를 쉽게 바꿀수 있도록 이미지 아이콘을 받아놓는다.
	
	private JButton Back = new JButton(backButtonBasicImage);
	private JButton Start = new JButton(startButtonBasicImage);
	private JButton End = new JButton(exitButtonBasicImage);
	//BACK,START,END 버튼들을 정의한다.각각 기본 이미지를 가지도록 한다
	private RML MouseL;
	//버튼을 눌렀을때 행동을 실행할 리스너를 선언한다.
	Music introMusic = new Music("ShineLikeaStar.mp3", true);
	//시작 노래를 정의한다. 무한 반복으로 설정한다.
	public RhythmGame(JPanel p,JPanel MainScreen,JFrame t) {
		th=t;
		primary=p;
		THEFirstScreen=MainScreen;
		//UPCALL대상들을 생성자로 입력받은 것들과 연결시키고
		
		
		MusicPanel.setPreferredSize(new Dimension(Main.screenWidth, Main.screenHeight));
		MusicPanel.setLayout(null);
		MusicPanel.setBounds(0, 0, 1280, 690);
		//MUSICPANEL크기와 LAYOUT과 위치 선언
		
		BackGround.setSize(new Dimension(1280, 690));
		BackGround.setBounds(0, 0, 1280, 690);
		BackGround.setLayout(null);
		//BACKGROUND의 크기와 LAYOUT과 위치 선언
		
		MouseL = new RML();
		//마우스 리스너 생성함.
		
		
		
		//버튼 위치와 세세한 내용들 설정한다
		Back.setBounds(0, 0, 50, 50);
		buttonInit(Back);
		Start.setBounds(100, 500, 400, 100);
		buttonInit(Start);
		End.setBounds(800, 500, 400, 100);
		buttonInit(End);
		////////////////////////////////////////
		
		LOGO.setBounds(1050,0,200,200);
		//로고 위치 선언
		
		BackGround.add(Back);
		BackGround.add(Start);
		BackGround.add(End);
		BackGround.add(LOGO);
		//BACKGROUND에 버튼 더하고
		
		Back.addMouseListener(MouseL);
		Start.addMouseListener(MouseL);
		End.addMouseListener(MouseL);
		//버튼 3개에는 마우스 리스너를 물리고
		
		MusicPanel.add(BackGround);
		//뮤직패널위에 BACKGROUND를 붙이고
		primary.add(MusicPanel);
		// MusicPanel위에 모든 MusicMain에 해당하는것들을 선언후 붙인다.
		//다루기 쉽게 하기 위하여.
		
		
		//모든 작업이 끝나면 노래를 시작한다
		introMusic.start();

	}

	private void buttonInit(JButton k) {
		k.setBorderPainted(false);
		k.setFocusPainted(false);
		k.setContentAreaFilled(false);
	}
	//버튼 BORDER와 FOCUS와 AREAFILLED를 모두 FALSE값을 준다

	private void Back() {
		introMusic.close();
		THEFirstScreen.setVisible(true);
		MusicPanel.setVisible(false);
	}
	//뒤로가기
	//노래를 끄고
	//뒤로가기 눌렀을때로 UPCALL형식으로 THEFirstScreen을 보이게 하고
	//뮤직 패널은 안보이게 한다

	private void Start() {
		introMusic.close();
		MusicPanel.setVisible(false);
		new RhythmGameMusicSelection(primary,THEFirstScreen,th);
	}
	//시작하기
	//노래 끝내고
	//뮤직패널 안보이게
	//리듬게임뮤직설렉션을 시작한다.
	
	
	private void END() {
		System.exit(0);
	}
	//끝내기
	//시스템종료

	private class RML implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == End) {
				End.setIcon(exitButtonEnteredImage);
				End.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == Back) {
				Back.setIcon(backButtonEnteredImage);
				Back.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (obj == Start) {
				Start.setIcon(startButtonEnteredImage);
				Start.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == End) {
				End.setIcon(exitButtonBasicImage);
				End.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == Back) {
				Back.setIcon(backButtonBasicImage);
				Back.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (obj == Start) {
				Start.setIcon(startButtonBasicImage);
				Start.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			if (obj == Back) {
				Back();
			} else if (obj == Start) {
				Start();
			} else if (obj == End) {
				END();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	//사용자가 버튼을 누르거나 마우스를 버튼에 올리고 내렸을때 하는 모든 행동들에 대해서
	//어떤식으로 대응할지 정한 곳이다
	//버튼 이미지와 커서가 마우스를 올리고 내리고에 따라 달라진다.

}
