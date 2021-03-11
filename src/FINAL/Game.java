package FINAL;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	// 게임 클래스는 THREAD를 상속받아서 사용된다.

	///////////////////////////////////////////////////////
	private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("../images/noteRouteLine.png")).getImage();
	private Image noteRouteLineComboImage = new ImageIcon(Main.class.getResource("../images/comboOver10.png"))
			.getImage();
	private Image noteRouteLineComboOver50Image = new ImageIcon(Main.class.getResource("../images/comboOver50.png"))
			.getImage();
	// 키를 누를때 뒤에 콤보수에 따라서 색깔 바뀌도록 하는 이미지들이다./////////

	// 판정선 부분:사용자에게 어떤 타이밍에 어떤 키를 눌러야할지 기준이 되는 부분이다.
	private Image judgementLineImage = new ImageIcon(Main.class.getResource("../images/judgementLine.png")).getImage();
	////////////////////////

	// 게임 시 밑에서 게임 정보(곡 제목, 점수, 단계)를 알려주는 부분 을 담는 부분이다
	private Image gameInfoImage = new ImageIcon(Main.class.getResource("../images/GameInfoImage.png")).getImage();
	//////////////// 게임정보는 모두 String으로 그려진다

	/////////////////////////////////////////////////
	// 키를 누를때 뒤쪽에 나타나는 이미지에 해당하는 부분이다.
	// 기본값으로는 파란색, 10콤보 넘어가면 맨 위에 정의된 이미지들로 바뀌게 된다.
	// S키 부분
	private Image noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// D키 부분
	private Image noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// F키 부분
	private Image noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// 스페이스바 부분
	private Image noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// J키 부분
	private Image noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// K키 부분
	private Image noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// L키 부분
	private Image noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	///////////////////////////////////////////////////

	// 사용자에게 보여지는 이미지 부분들
	private Image FLARE = new ImageIcon(Main.class.getResource("../images/flare.png")).getImage();
	private Image ENDBACKGROUND = new ImageIcon(Main.class.getResource("../images/selectionBackground.jpg")).getImage();
	private Image LOGO = new ImageIcon(Main.class.getResource("../images/logo.png")).getImage();
	// 플레어, 끝날때 화면,로고등을 선언하였다/

	// 게임정보///////////////////
	private String titleName;// 제목을 써주는 변수
	private String difficulty;// 난이도
	private String musicTitle;// 노래를 시작시켜주기 위한 변수
	////////////////////////////

	// 게임음악\\\\\\\\\\\\\\\\\
	private Music gameMusic;// 게임음악
	private Music scoreMusic;// 점수곡
	// 게임 음악과 점수곡을 따로 선언하였다.

	public static int Life; // 사용자의 게임수명을 알려주기 위한 변수
	public static int Wrong = -3; // MISS가 났을 때 life는 -3만큼 씩 줄어드는 형식
	public static int Gamescore = 0;// 점수 합산을 위한 변수
	// 어느 클래스에서나 접근할수있도록 public static 형 사용
	// 노트의 landing에 따라good, perfect, late, early, great, miss으로 나눌 수 있으며, 각 단꼐별로 점수가
	// 다르다

	// 사용자가 입력한 키값에 따른 판정에 의해 더해지는 점수들을 정의한 것이다
	private final int GOOD = 100;
	private final int PERFECT = 500;
	private final int LATE = 50;
	private final int EARLY = 50;
	private final int GREAT = 200;
	private final int SpecialNotes = 1000;
	/////////////////////////////////////////////////

	private int flag = 0;
	// 점수곡이 한번만 나올수 있도록 하는 안전장치
	private int LifeFlag = 0;
	// 라이프가 0보다 떨어지면 그에 따른 행동을 trigger할수 있도록 한 lifeflag이다.
	private boolean MusicFlag;
	// 초반에 어쩔때마다 점수곡이 나오는 오류가 있어서 부득이하게 MusicFlag를 통하여
	// 조절하게 하였다.

	private String GameScoreString; // 점수
	private String returnedValueOfNoteJudgement; // 노트의 landing을 판정하기 위한 변수
	// 점수를 담는 String부분과 노트를 판정했을때 return값을 받는 String부분이다.

	public static String JUDGEMENT; // 노트의 landing을 판정하기 위한 변수
	public static int combo;// combo 판단을 위한 변수
	public static String COMBO;
	public static int Maxcombo;// maxCombo를 저장하기위한 변수
	public static String MAXCOMBO;

	Beats[] BEATS = null;// 비트 넣을것 마련
	ArrayList<Note> noteList = new ArrayList<Note>();// 실행중인 노트 관리를 위한 리스트 선언

	// Game 클래스 생성자, 초기화
	public Game(String titleName, String difficulty, String musicTitle) {
		LifeFlag = 0;
		flag = 0;
		MusicFlag = false;
		Gamescore = 0;
		JUDGEMENT = new String("");
		combo = 0;
		Maxcombo = 0;
		if (difficulty.equals("HARD")) // 어려운 난이도일 때 수명은 60부터 시작
		{
			Life = 60;
		} else { // 쉬운 난이도일 때 수명은 100부터 시작
			Life = 100;
		}

		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);
		scoreMusic = new Music("ROBOTICS.mp3", true);
	}
	// 전체 초기화및 노래 어떤 노래를 틀지 반복여부등 초기화
	// flag값들을 0으로 초기화
	// musicFlag는 false로 초기화
	// 어려움에 따라 라이프값을 다르게 주고
	// 들어온 노래의 이름을 받아 반복없도록 gameMusic을 선언해주고
	// scoreMusic에 항상 같은 노래를 나올수 있도록 선언해준다.

	public void screenDraw(Graphics2D g) {
		if ((!gameMusic.isAlive() && (MusicFlag == true)) || (LifeFlag != 0)) {
			// 게임이 끝날 조건이다. 음악이 죽고 MusicFlag가 참이거나 LifeFlag 가 발동되었을때
			g.drawImage(ENDBACKGROUND, 0, 0, null);
			g.drawImage(LOGO, 700, 150, null);
			// 뒷 배경을 점수배경으로 그리고 로고 추가
			g.setColor(Color.black);
			g.setFont(new Font("Elephant", Font.BOLD, 50));
			g.drawString(titleName, 150, 400);
			MAXCOMBO = String.format("MAXCOMBO: %3d", Maxcombo);
			g.drawString(MAXCOMBO, 150, 500);
			g.drawString("Score:   " + GameScoreString, 150, 600);
			// 나머지 정보들 그려주고
			this.interrupt();
			// game을 중지시킨후
			if (flag == 0 && !gameMusic.isInterrupted()) {
				for (int i = 0; i < noteList.size(); i++) {
					Note note = noteList.get(i);
					note.KILLTHREAD();

				} // 내려오는 노드 죽이고
				System.out.println("점수곡 시작");
				scoreMusic.start();// 엔딩송 시작
				flag++;

			}
			// flag를 사용하여 noteList안에 있는 모든 노트들을 죽이고
			// 점수곡 실행시키는 것을 조절한다
		} else {// 게임이 정상 진행중일때
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			if (difficulty.equals("EASY")) { // 난이도가 easy일 때
				// 노트의 루트이미지를 그린다.
				g.drawImage(noteRouteSImage, 228, 30, null);
				g.drawImage(noteRouteDImage, 332, 30, null);
				g.drawImage(noteRouteKImage, 848, 30, null);
				g.drawImage(noteRouteLImage, 952, 30, null);

				g.drawImage(noteRouteLineImage, 328, 30, null);
				g.drawImage(noteRouteLineImage, 432, 30, null);
				g.drawImage(noteRouteLineImage, 844, 30, null);
				g.drawImage(noteRouteLineImage, 948, 30, null);
				// 쉬운경우 S,D,K,L 키 에 해당하는 것만 그린다
			} else if (difficulty.equals("HARD")) {// 난이도가 hard일 때
				// 노트의 루트이미지를 그린다.
				g.drawImage(noteRouteSImage, 228, 30, null);
				g.drawImage(noteRouteDImage, 332, 30, null);
				g.drawImage(noteRouteFImage, 436, 30, null);
				g.drawImage(noteRouteSpace1Image, 540, 30, null);
				g.drawImage(noteRouteSpace2Image, 640, 30, null);
				g.drawImage(noteRouteJImage, 744, 30, null);
				g.drawImage(noteRouteKImage, 848, 30, null);
				g.drawImage(noteRouteLImage, 952, 30, null);

				g.drawImage(noteRouteLineImage, 224, 30, null);
				g.drawImage(noteRouteLineImage, 328, 30, null);
				g.drawImage(noteRouteLineImage, 432, 30, null);
				g.drawImage(noteRouteLineImage, 536, 30, null);
				g.drawImage(noteRouteLineImage, 740, 30, null);
				g.drawImage(noteRouteLineImage, 844, 30, null);
				g.drawImage(noteRouteLineImage, 948, 30, null);
				g.drawImage(noteRouteLineImage, 1052, 30, null);
				// 어려운 경우 S,D,F,SpaceBar,J,K,L에 해당하는 모든 이미지들을 그린다.
			}
			// *******
			g.drawImage(gameInfoImage, 0, 660, null);
			g.drawImage(judgementLineImage, 0, 580, null);
			// 게임 화면 밑에 부분이다. 디자인과 판정선 부분이다.
			g.setColor(Color.black);
			// 판정선과 게임 정보
			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);// 노트 리스트로부터 받아오고 새로운 note에 대입시킨다.
				if (!note.isProceeding()) {// 노트가 진행중이 아닐경우
					noteList.remove(i);// 리스트에서 없애고
					i--;// 하나 줄인다.
				} // 없애고
				else {
					note.screenDraw(g);// 붓을 note로 넘긴다
				} // 나머지 경우 그린다.

			}
			// 노트 그리기

			// 밑에는 게임과련 정보들에 대한 부분이다.
			// 점수에 따라 색깔이 다르다
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.blue);
			g.drawString(titleName, 20, 702); // 곡 제목의 글씨체, 글씨 색깔 설정
			g.setColor(Color.WHITE);
			if (difficulty.equals("EASY")) { // 쉬운 난이도일 때 글씨 색깔 설정
				g.setColor(Color.yellow);
				g.drawString(difficulty, 1190, 702);
			} else {
				g.setColor(Color.WHITE); // 어려운 난이도일 때 글씨 색깔 설정
				g.drawString(difficulty, 1190, 702);
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.orange);
			if (difficulty.equals("EASY")) {// 난이도가 easy일 때 key setting
				g.drawString("S", 270, 609);
				g.drawString("D", 374, 609);
				g.drawString("K", 889, 609);
				g.drawString("L", 993, 609);

			} else if (difficulty.equals("HARD")) {// 난이도가 hard일 때 key setting
				g.drawString("S", 270, 609);
				g.drawString("D", 374, 609);
				g.drawString("F", 478, 609);
				g.drawString("Space Bar", 580, 609);
				g.drawString("J", 784, 609);
				g.drawString("K", 889, 609);
				g.drawString("L", 993, 609);
			}

			// 게임 점수와 게임점수 합산 방법에 대한 안내 부분

			g.setColor(Color.LIGHT_GRAY);// ************
			g.setFont(new Font("Elephant", Font.BOLD, 30)); // 색깔 및 글씨체 설정
			GameScoreString = String.format("%06d", Gamescore);
			g.drawString(GameScoreString, 565, 700);
			g.drawImage(FLARE, 440, 350, null);
			g.setFont(new Font("Arial", Font.BOLD, 15)); // 화면 우측에 합산 방법 안내에 대한 글씨체 설정
			g.setColor(Color.green);// 색깔 설정

			// 방법 안내에 대한 내용 및 위치 설정
			g.drawString("MISS = -100", 1100, 60);
			g.drawString("LATE = +50", 1100, 90);
			g.drawString("EARLY = +50", 1100, 120);
			g.drawString("GOOD = +100", 1100, 150);
			g.drawString("GREAT = +200", 1100, 180);
			g.drawString("PERFECT = +500", 1100, 210);
			g.drawString("SPECIAL NOTES = +1000", 1100, 240);
			g.drawString("COMBO=+5", 1100, 270);
			// ******

			g.setFont(new Font("Elephant", Font.BOLD, 30));
			g.setColor(Color.white);
			// judgement String이 special을 포함하고있을때 들어가게 된다.
			if (JUDGEMENT.contains("SPECIAL")) {// 만약 perfect또한 가지고 있다면
				if (JUDGEMENT.contains("PERFECT")) {
					g.setFont(new Font("Elephant", Font.BOLD, 30));
					g.setColor(Color.ORANGE);
					g.drawString(JUDGEMENT, 450, 350);
					g.setFont(new Font("Elephant", Font.BOLD, 30));
					g.setColor(Color.white);
					// 특수한 색깔및 위치로출력해준다.

				} else {
					g.setFont(new Font("Elephant", Font.BOLD, 30));
					g.setColor(Color.cyan);
					g.drawString(JUDGEMENT, 500, 350);
					g.setFont(new Font("Elephant", Font.BOLD, 30));
					g.setColor(Color.white);
					// 다른 특수한 색깔과 위치에 출력한다
				}
			} else if (JUDGEMENT.equals("PERFECT")) {
				// perfect를 가지고 있으면
				g.setFont(new Font("Elephant", Font.BOLD, 30));
				g.setColor(Color.red);
				g.drawString(JUDGEMENT, 550, 450);
				g.setFont(new Font("Elephant", Font.BOLD, 30));
				g.setColor(Color.white);
				// 조금 특수한 색깔로 출력해준다.

			} else {
				g.drawString(JUDGEMENT, 580, 450);
				// 그냥 출력한다.
			}
			//// 판정관련 부분이다/ 사용자가 입력한 키값에 대한 판정을 보여준다

			COMBO = String.format("COMBO: %3d", combo);
			g.setFont(new Font("SansSerif", Font.BOLD, 30));
			g.setColor(Color.magenta);
			g.drawString(COMBO, 1090, 300);
			// 콤보에 관한 것이다

			// 라이프 관련 부분이다. life값을 비교하여 얼마나 그려줄지 정한다
			g.setColor(Color.red);
			g.drawString("LIFE :" + Life, 0, 560);
			if (Life > 80) {
				g.fillRect(0, 250, 50, 50);
				g.fillRect(0, 305, 50, 50);
				g.fillRect(0, 360, 50, 50);
				g.fillRect(0, 415, 50, 50);
				g.fillRect(0, 470, 50, 50);

			} else if (Life > 60) {
				g.fillRect(0, 305, 50, 50);
				g.fillRect(0, 360, 50, 50);
				g.fillRect(0, 415, 50, 50);
				g.fillRect(0, 470, 50, 50);

			} else if (Life > 40) {
				g.fillRect(0, 360, 50, 50);
				g.fillRect(0, 415, 50, 50);
				g.fillRect(0, 470, 50, 50);

			} else if (Life > 20) {
				g.fillRect(0, 415, 50, 50);
				g.fillRect(0, 470, 50, 50);

			} else if (Life >= 0) {
				g.fillRect(0, 470, 50, 50);

			} else {
				// 라이프가 0보다 작을때에는 노래를 끝내고 점수판을 보여주어야한다.
				if (LifeFlag == 0) {
					gameMusic.close();// 게임음악 끝내고
					this.interrupt();// game에 대해 멈추고
					for (int i = 0; i < noteList.size(); i++) {
						Note note = noteList.get(i);
						note.KILLTHREAD();

					} // 모든 노트리스트안에 있는것들 죽인다
					g.drawImage(ENDBACKGROUND, 0, 0, null);
					// 점수화면 이미지 그린다
					LifeFlag++;// 라이프 플래그 증가--> 점수화면
				}
			}

		} // During the game play
	}// screenDraw

	// S,D,F,SpaceBar,J,K,L을 누를 때 소리가 나도록 설정
	// 이부분부터 약 ~460 줄까지는 사용자가 키를 눌렀을때 어떤 행동을 할것인지 정하는 함수들이다.
	// 이 함수들은 keyListener클래스에 의해서 trigger되고 누를 때는 P(KEY)형식 땔때는 R(KEY)형식으로
	// 정의하였다. 누를때 그 키값에 대한 JUDGE를 실행하게 되고 콤보수에 따라 이미지 또한 바꿔주고
	// 키를 누르는 음악을 실행시킨다.
	// 땔때는 본래 이미지로 다시 바꾼다.
	public void PS() {// Press S
		judgeLeft("S");// S키에 대하여
		noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		if (combo > 10) {
			noteRouteSImage = noteRouteLineComboImage;
		}
		if (combo > 20) {
			noteRouteSImage = noteRouteLineComboOver50Image;
		}
		new Music("drumSmall1.mp3", false).start();
	}

	public void RS() {// Release S
		noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	}

	public void PD() {// Press D
		judgeLeft("D");// D키에 대하여
		noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		if (combo > 10) {
			noteRouteDImage = noteRouteLineComboImage;
		}
		if (combo > 20) {
			noteRouteDImage = noteRouteLineComboOver50Image;
		}
		new Music("drumSmall1.mp3", false).start();
	}

	public void RD() {// Release D
		noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	}

	public void PF() {// Press F
		judgeLeft("F");// F키에 대하여
		noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		if (combo > 10) {
			noteRouteFImage = noteRouteLineComboImage;
		}
		if (combo > 20) {
			noteRouteFImage = noteRouteLineComboOver50Image;
		}
		new Music("drumSmall1.mp3", false).start();
	}

	public void RF() {// Release F
		noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	}

	public void PSP() {// Press SpaceBar
		judgeLeft("Space");// SpaceBar에 대하여
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		if (combo > 10) {
			noteRouteSpace1Image = noteRouteLineComboImage;
			noteRouteSpace2Image = noteRouteLineComboImage;
		}
		if (combo > 20) {
			noteRouteSpace1Image = noteRouteLineComboOver50Image;
			noteRouteSpace2Image = noteRouteLineComboOver50Image;
		}
		new Music("drumBig1.mp3", false).start();
	}

	public void RSP() {// Release SpaceBar
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	}

	public void PJ() {// Press J
		judgeRight("J");// J키 에 대하여
		noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		if (combo > 10) {
			noteRouteJImage = noteRouteLineComboImage;
		}
		if (combo > 20) {
			noteRouteJImage = noteRouteLineComboOver50Image;
		}
		new Music("drumSmall1.mp3", false).start();
	}

	public void RJ() {// Release J
		noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	}

	public void PK() {// Press K
		judgeRight("K");// K키에 대하여
		noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		if (combo > 10) {
			noteRouteKImage = noteRouteLineComboImage;
		}
		if (combo > 20) {
			noteRouteKImage = noteRouteLineComboOver50Image;
		}
		new Music("drumSmall1.mp3", false).start();
	}

	public void RK() {// Release K
		noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	}

	public void PL() {// Press L
		judgeRight("L");// L키에 대하여
		noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		if (combo > 10) {
			noteRouteLImage = noteRouteLineComboImage;
		}
		if (combo > 20) {
			noteRouteLImage = noteRouteLineComboOver50Image;
		}
		new Music("drumSmall1.mp3", false).start();
	}

	public void RL() {// Release L
		noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	}
	// 약 312~320줄 사이에 설명 완료하였습니다.

	@Override
	public void run() {
		START();
	}
	// game을 실행시키면 run함수를 호출하게되고 그 안에서
	// 악보를 받아오고 그에따라 노트를 떨어트려주는 작업을 실행하게 된다.

	// close에 대한 주석 끝
	public void close() {

		gameMusic.close();
		// 게임 음악을 먼저 끝내고
		this.interrupt();
		// 이 게임도 끝내면서
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			note.KILLTHREAD();

		}
		// 현재 노트 리스트안에 있는 모든 노트이 대해
		// 노트에 대한 쓰레드 다 죽이고
		if (scoreMusic != null) {
			if (scoreMusic.isAlive()) {
				scoreMusic.close();
			}
		}
		// 스코어 노래가 살아있으면
		// 스코어 노래또한 끈다.

	}

	// ************
	// START함수는 쉽게말해서 곡이름과 난이도에 따라 어떤 악보를 고를지 정하는 작업이라 생각하면된다
	// starttime과 term을 통하여 새로운 노트가 어떤 시간에 나올지 정한다.
	public void START() {
		if (titleName.equals("STARGAZER")) {// Stargazer 곡
			if (difficulty.equals("EASY")) {
				int startTime = 100;
				int term = 150;// 비트 약 150
				System.out.println("START");
				BEATS = new Beats[] { new Beats(startTime + term, "D", true), new Beats(startTime + term, "K"),
						new Beats(startTime + term * 5, "D"), new Beats(startTime + term * 5, "K"),
						new Beats(startTime + term * 9, "S"), new Beats(startTime + term * 9, "L", true),
						new Beats(startTime + term * 13, "D", true), new Beats(startTime + term * 13, "K"),
						new Beats(startTime + term * 17, "D"), new Beats(startTime + term * 17, "K"),
						new Beats(startTime + term * 21, "S"), new Beats(startTime + term * 21, "L"),
						new Beats(startTime + term * 25, "D"), new Beats(startTime + term * 25, "K"),
						new Beats(startTime + term * 29, "D"), new Beats(startTime + term * 29, "K"),
						new Beats(startTime + term * 33, "S", true), new Beats(startTime + term * 33, "L"),
						new Beats(startTime + term * 37, "D"), new Beats(startTime + term * 37, "K"),
						new Beats(startTime + term * 41, "D", true), new Beats(startTime + term * 41, "K"),
						new Beats(startTime + term * 45, "S"), new Beats(startTime + term * 45, "L", true),
						new Beats(startTime + term * 49, "D"), new Beats(startTime + term * 49, "K", true),
						new Beats(startTime + term * 53, "D"), new Beats(startTime + term * 53, "K"),
						new Beats(startTime + term * 57, "S"), new Beats(startTime + term * 57, "L"),
						new Beats(startTime + term * 61, "D"), new Beats(startTime + term * 61, "K"),
						new Beats(startTime + term * 65, "D"), new Beats(startTime + term * 65, "K"),
						new Beats(startTime + term * 69, "S"), new Beats(startTime + term * 69, "L"),
						new Beats(startTime + term * 73, "D"), new Beats(startTime + term * 73, "K", true),
						new Beats(startTime + term * 77, "D"), new Beats(startTime + term * 77, "K"),
						new Beats(startTime + term * 81, "S", true), new Beats(startTime + term * 81, "L"),
						new Beats(startTime + term * 85, "D"), new Beats(startTime + term * 85, "K"),
						new Beats(startTime + term * 89, "D"), new Beats(startTime + term * 89, "K", true),
						new Beats(startTime + term * 93, "S", true), new Beats(startTime + term * 93, "L"),
						new Beats(startTime + term * 97, "D"), new Beats(startTime + term * 97, "K"),
						new Beats(startTime + term * 101, "D"), new Beats(startTime + term * 101, "K"),
						new Beats(startTime + term * 105, "S"), new Beats(startTime + term * 105, "L"),
						new Beats(startTime + term * 109, "D"), new Beats(startTime + term * 109, "K", true),
						new Beats(startTime + term * 113, "D", true), new Beats(startTime + term * 113, "K"),
						new Beats(startTime + term * 117, "S"), new Beats(startTime + term * 117, "L"),
						new Beats(startTime + term * 121, "D"), new Beats(startTime + term * 121, "K", true),
						new Beats(startTime + term * 125, "D", true), new Beats(startTime + term * 125, "K"),
						new Beats(startTime + term * 129, "S"), new Beats(startTime + term * 129, "L"),
						new Beats(startTime + term * 133, "D", true), new Beats(startTime + term * 133, "K"),
						new Beats(startTime + term * 137, "D"), new Beats(startTime + term * 137, "K"),
						new Beats(startTime + term * 141, "S", true), new Beats(startTime + term * 141, "L"),
						new Beats(startTime + term * 145, "D"), new Beats(startTime + term * 145, "K"),
						new Beats(startTime + term * 149, "D"), new Beats(startTime + term * 149, "K"),
						new Beats(startTime + term * 153, "S"), new Beats(startTime + term * 153, "L"),
						new Beats(startTime + term * 157, "D"), new Beats(startTime + term * 157, "K"),
						new Beats(startTime + term * 161, "D"), new Beats(startTime + term * 161, "K", true),
						new Beats(startTime + term * 165, "S", true), new Beats(startTime + term * 165, "L"),
						new Beats(startTime + term * 169, "D"), new Beats(startTime + term * 169, "K"),
						new Beats(startTime + term * 173, "D", true), new Beats(startTime + term * 173, "K"),
						new Beats(startTime + term * 177, "S"), new Beats(startTime + term * 177, "L"),
						new Beats(startTime + term * 181, "D"), new Beats(startTime + term * 181, "K"),
						new Beats(startTime + term * 185, "D", true), new Beats(startTime + term * 185, "K"),
						new Beats(startTime + term * 189, "S"), new Beats(startTime + term * 189, "L"),
						new Beats(startTime + term * 193, "D"), new Beats(startTime + term * 193, "K", true),
						new Beats(startTime + term * 197, "D"), new Beats(startTime + term * 197, "K"),
						new Beats(startTime + term * 201, "S"), new Beats(startTime + term * 201, "L"),
						new Beats(startTime + term * 205, "D", true), new Beats(startTime + term * 205, "K"),
						new Beats(startTime + term * 209, "D"), new Beats(startTime + term * 209, "K"),
						new Beats(startTime + term * 213, "S"), new Beats(startTime + term * 213, "L"),
						new Beats(startTime + term * 217, "D"), new Beats(startTime + term * 217, "K", true),
						new Beats(startTime + term * 221, "D"), new Beats(startTime + term * 221, "K"),
						new Beats(startTime + term * 225, "S", true), new Beats(startTime + term * 225, "L"),
						new Beats(startTime + term * 229, "D"), new Beats(startTime + term * 229, "K"),
						new Beats(startTime + term * 233, "D"), new Beats(startTime + term * 233, "K"),
						new Beats(startTime + term * 237, "S"), new Beats(startTime + term * 237, "L"),
						new Beats(startTime + term * 241, "D"), new Beats(startTime + term * 241, "K"),
						new Beats(startTime + term * 245, "D"), new Beats(startTime + term * 245, "K"),
						new Beats(startTime + term * 249, "S"), new Beats(startTime + term * 249, "L", true),
						new Beats(startTime + term * 253, "D"), new Beats(startTime + term * 253, "K"),
						new Beats(startTime + term * 257, "D"), new Beats(startTime + term * 257, "K"),
						new Beats(startTime + term * 261, "S", true), new Beats(startTime + term * 261, "L"),
						new Beats(startTime + term * 265, "D"), new Beats(startTime + term * 265, "K"),
						new Beats(startTime + term * 269, "D"), new Beats(startTime + term * 269, "K"),
						new Beats(startTime + term * 273, "S", true), new Beats(startTime + term * 273, "L"),
						new Beats(startTime + term * 277, "D"), new Beats(startTime + term * 277, "K"),
						new Beats(startTime + term * 281, "D"), new Beats(startTime + term * 281, "K"),
						new Beats(startTime + term * 285, "S"), new Beats(startTime + term * 285, "L"),
						new Beats(startTime + term * 289, "D"), new Beats(startTime + term * 289, "K", true),
						new Beats(startTime + term * 293, "D"), new Beats(startTime + term * 293, "K"),
						new Beats(startTime + term * 297, "S"), new Beats(startTime + term * 297, "L"),
						new Beats(startTime + term * 301, "D"), new Beats(startTime + term * 301, "K"),
						new Beats(startTime + term * 305, "D", true), new Beats(startTime + term * 305, "K"),
						new Beats(startTime + term * 309, "S"), new Beats(startTime + term * 309, "L"),
						new Beats(startTime + term * 313, "D"), new Beats(startTime + term * 313, "K"),
						new Beats(startTime + term * 317, "D"), new Beats(startTime + term * 317, "K"),
						new Beats(startTime + term * 321, "S"), new Beats(startTime + term * 321, "L"),
						new Beats(startTime + term * 325, "D"), new Beats(startTime + term * 325, "K", true),
						new Beats(startTime + term * 329, "D"), new Beats(startTime + term * 329, "K"),
						new Beats(startTime + term * 333, "S"), new Beats(startTime + term * 333, "L"),
						new Beats(startTime + term * 337, "D", true), new Beats(startTime + term * 337, "K"),
						new Beats(startTime + term * 341, "D"), new Beats(startTime + term * 341, "K"),
						new Beats(startTime + term * 345, "S"), new Beats(startTime + term * 345, "L", true),
						new Beats(startTime + term * 349, "D"), new Beats(startTime + term * 349, "K"),
						new Beats(startTime + term * 353, "D"), new Beats(startTime + term * 353, "K"),
						new Beats(startTime + term * 357, "S"), new Beats(startTime + term * 357, "L"),
						new Beats(startTime + term * 361, "D"), new Beats(startTime + term * 361, "K"),
						new Beats(startTime + term * 365, "D"), new Beats(startTime + term * 365, "K"),
						new Beats(startTime + term * 369, "S"), new Beats(startTime + term * 369, "L"),
						new Beats(startTime + term * 373, "D"), new Beats(startTime + term * 373, "K"),
						new Beats(startTime + term * 377, "D"), new Beats(startTime + term * 377, "K"),
						new Beats(startTime + term * 381, "S"), new Beats(startTime + term * 381, "L"),
						new Beats(startTime + term * 385, "D", true), new Beats(startTime + term * 385, "K"),
						new Beats(startTime + term * 389, "D"), new Beats(startTime + term * 389, "K", true),
						new Beats(startTime + term * 393, "S"), new Beats(startTime + term * 393, "L"),
						new Beats(startTime + term * 397, "D", true), new Beats(startTime + term * 397, "K"),
						new Beats(startTime + term * 401, "D"), new Beats(startTime + term * 401, "K", true),
						new Beats(startTime + term * 405, "S"), new Beats(startTime + term * 405, "L"),
						new Beats(startTime + term * 409, "D", true), new Beats(startTime + term * 409, "K"),
						new Beats(startTime + term * 413, "D"), new Beats(startTime + term * 413, "K", true),
						new Beats(startTime + term * 417, "S"), new Beats(startTime + term * 417, "L"),
						new Beats(startTime + term * 421, "D"), new Beats(startTime + term * 421, "K"),
						new Beats(startTime + term * 425, "D"), new Beats(startTime + term * 425, "K"),
						new Beats(startTime + term * 429, "S"), new Beats(startTime + term * 429, "L"),
						new Beats(startTime + term * 433, "D"), new Beats(startTime + term * 433, "K"),
						new Beats(startTime + term * 437, "D"), new Beats(startTime + term * 437, "K", true),
						new Beats(startTime + term * 441, "S"), new Beats(startTime + term * 441, "L"),
						new Beats(startTime + term * 445, "D", true), new Beats(startTime + term * 445, "K"),
						new Beats(startTime + term * 449, "D"), new Beats(startTime + term * 449, "K"),
						new Beats(startTime + term * 453, "S"), new Beats(startTime + term * 453, "L"),

				};
			} // stargazer 쉬움 모드
			else if (difficulty.equals("HARD")) {

				int startTime = 100;
				int term = 150;
				System.out.println("START");
				BEATS = new Beats[] { new Beats(startTime + term, "D", true), new Beats(startTime + term, "K"),
						new Beats(startTime + term * 5, "D"), new Beats(startTime + term * 5, "K"),
						new Beats(startTime + term * 9, "S"), new Beats(startTime + term * 9, "L"),
						new Beats(startTime + term * 13, "D"), new Beats(startTime + term * 13, "K"),
						new Beats(startTime + term * 17, "D"), new Beats(startTime + term * 17, "K"),
						new Beats(startTime + term * 21, "F"), new Beats(startTime + term * 21, "J", true),
						new Beats(startTime + term * 25, "D"), new Beats(startTime + term * 25, "K"),
						new Beats(startTime + term * 29, "D"), new Beats(startTime + term * 29, "K"),
						new Beats(startTime + term * 33, "S"), new Beats(startTime + term * 33, "L"),
						new Beats(startTime + term * 37, "D"), new Beats(startTime + term * 37, "K"),
						new Beats(startTime + term * 41, "D", true), new Beats(startTime + term * 41, "K"),
						new Beats(startTime + term * 45, "F"), new Beats(startTime + term * 45, "J"),
						new Beats(startTime + term * 49, "D"), new Beats(startTime + term * 49, "K"),
						new Beats(startTime + term * 53, "D"), new Beats(startTime + term * 53, "K", true),
						new Beats(startTime + term * 57, "Space"), new Beats(startTime + term * 61, "D"),
						new Beats(startTime + term * 61, "K"), new Beats(startTime + term * 65, "D"),
						new Beats(startTime + term * 65, "K"), new Beats(startTime + term * 69, "S"),
						new Beats(startTime + term * 69, "L"), new Beats(startTime + term * 73, "D"),
						new Beats(startTime + term * 73, "K"), new Beats(startTime + term * 77, "D"),
						new Beats(startTime + term * 77, "K"), new Beats(startTime + term * 81, "F", true),
						new Beats(startTime + term * 81, "J"), new Beats(startTime + term * 85, "D"),
						new Beats(startTime + term * 85, "K"), new Beats(startTime + term * 89, "D"),
						new Beats(startTime + term * 89, "K"), new Beats(startTime + term * 93, "S"),
						new Beats(startTime + term * 93, "L"), new Beats(startTime + term * 97, "D"),
						new Beats(startTime + term * 97, "K", true), new Beats(startTime + term * 101, "D"),
						new Beats(startTime + term * 101, "K"), new Beats(startTime + term * 105, "F"),
						new Beats(startTime + term * 105, "J"), new Beats(startTime + term * 109, "D"),
						new Beats(startTime + term * 109, "K"), new Beats(startTime + term * 113, "D", true),
						new Beats(startTime + term * 113, "K"), new Beats(startTime + term * 117, "S"),
						new Beats(startTime + term * 117, "L"), new Beats(startTime + term * 121, "D"),
						new Beats(startTime + term * 121, "K", true), new Beats(startTime + term * 125, "D"),
						new Beats(startTime + term * 125, "K"), new Beats(startTime + term * 129, "Space"),
						new Beats(startTime + term * 133, "D"), new Beats(startTime + term * 133, "K"),
						new Beats(startTime + term * 137, "D"), new Beats(startTime + term * 137, "K"),
						new Beats(startTime + term * 141, "Space"), new Beats(startTime + term * 145, "D"),
						new Beats(startTime + term * 145, "K"), new Beats(startTime + term * 149, "D"),
						new Beats(startTime + term * 149, "K"), new Beats(startTime + term * 153, "S", true),
						new Beats(startTime + term * 153, "L"), new Beats(startTime + term * 157, "D"),
						new Beats(startTime + term * 157, "K"), new Beats(startTime + term * 161, "D"),
						new Beats(startTime + term * 161, "K"), new Beats(startTime + term * 165, "F"),
						new Beats(startTime + term * 165, "J"), new Beats(startTime + term * 169, "D"),
						new Beats(startTime + term * 169, "K"), new Beats(startTime + term * 173, "D"),
						new Beats(startTime + term * 173, "K", true), new Beats(startTime + term * 177, "S"),
						new Beats(startTime + term * 177, "L"), new Beats(startTime + term * 181, "D"),
						new Beats(startTime + term * 181, "K"), new Beats(startTime + term * 185, "D"),
						new Beats(startTime + term * 185, "K"), new Beats(startTime + term * 189, "S"),
						new Beats(startTime + term * 189, "L"), new Beats(startTime + term * 193, "D"),
						new Beats(startTime + term * 193, "K"), new Beats(startTime + term * 197, "D"),
						new Beats(startTime + term * 197, "K"), new Beats(startTime + term * 201, "F"),
						new Beats(startTime + term * 201, "J"), new Beats(startTime + term * 205, "D"),
						new Beats(startTime + term * 205, "K"), new Beats(startTime + term * 209, "D", true),
						new Beats(startTime + term * 209, "K"), new Beats(startTime + term * 213, "S"),
						new Beats(startTime + term * 213, "L"), new Beats(startTime + term * 217, "D"),
						new Beats(startTime + term * 217, "K"), new Beats(startTime + term * 221, "D"),
						new Beats(startTime + term * 221, "K"), new Beats(startTime + term * 225, "S"),
						new Beats(startTime + term * 225, "L"), new Beats(startTime + term * 229, "D"),
						new Beats(startTime + term * 229, "K", true), new Beats(startTime + term * 233, "D"),
						new Beats(startTime + term * 233, "K"), new Beats(startTime + term * 237, "Space"),
						new Beats(startTime + term * 241, "D"), new Beats(startTime + term * 241, "K"),
						new Beats(startTime + term * 245, "D"), new Beats(startTime + term * 245, "K"),
						new Beats(startTime + term * 249, "S"), new Beats(startTime + term * 249, "L"),
						new Beats(startTime + term * 253, "D"), new Beats(startTime + term * 253, "K"),
						new Beats(startTime + term * 257, "D"), new Beats(startTime + term * 257, "K"),
						new Beats(startTime + term * 261, "S"), new Beats(startTime + term * 261, "L"),
						new Beats(startTime + term * 265, "D"), new Beats(startTime + term * 265, "K"),
						new Beats(startTime + term * 269, "D"), new Beats(startTime + term * 269, "K"),
						new Beats(startTime + term * 273, "Space", true), new Beats(startTime + term * 277, "D"),
						new Beats(startTime + term * 277, "K"), new Beats(startTime + term * 281, "D"),
						new Beats(startTime + term * 281, "K"), new Beats(startTime + term * 285, "S"),
						new Beats(startTime + term * 285, "L"), new Beats(startTime + term * 289, "D"),
						new Beats(startTime + term * 289, "K"), new Beats(startTime + term * 293, "D", true),
						new Beats(startTime + term * 293, "K"), new Beats(startTime + term * 297, "S"),
						new Beats(startTime + term * 297, "L"), new Beats(startTime + term * 301, "D"),
						new Beats(startTime + term * 301, "K"), new Beats(startTime + term * 305, "D"),
						new Beats(startTime + term * 305, "K"), new Beats(startTime + term * 309, "F"),
						new Beats(startTime + term * 309, "J"), new Beats(startTime + term * 313, "D"),
						new Beats(startTime + term * 313, "K"), new Beats(startTime + term * 317, "D"),
						new Beats(startTime + term * 317, "K", true), new Beats(startTime + term * 321, "Space"),
						new Beats(startTime + term * 325, "D"), new Beats(startTime + term * 325, "K"),
						new Beats(startTime + term * 329, "D"), new Beats(startTime + term * 329, "K"),
						new Beats(startTime + term * 333, "S", true), new Beats(startTime + term * 333, "L"),
						new Beats(startTime + term * 337, "D"), new Beats(startTime + term * 337, "K"),
						new Beats(startTime + term * 341, "D"), new Beats(startTime + term * 341, "K"),
						new Beats(startTime + term * 345, "Space"), new Beats(startTime + term * 349, "D"),
						new Beats(startTime + term * 349, "K"), new Beats(startTime + term * 353, "D"),
						new Beats(startTime + term * 353, "K"), new Beats(startTime + term * 357, "S"),
						new Beats(startTime + term * 357, "L"), new Beats(startTime + term * 361, "D"),
						new Beats(startTime + term * 361, "K"), new Beats(startTime + term * 365, "D"),
						new Beats(startTime + term * 365, "K", true), new Beats(startTime + term * 369, "F"),
						new Beats(startTime + term * 369, "J"), new Beats(startTime + term * 373, "D", true),
						new Beats(startTime + term * 373, "K"), new Beats(startTime + term * 377, "D"),
						new Beats(startTime + term * 377, "K"), new Beats(startTime + term * 381, "F"),
						new Beats(startTime + term * 381, "J"), new Beats(startTime + term * 385, "D"),
						new Beats(startTime + term * 385, "K"), new Beats(startTime + term * 389, "D"),
						new Beats(startTime + term * 389, "K"), new Beats(startTime + term * 393, "Space"),
						new Beats(startTime + term * 397, "D"), new Beats(startTime + term * 397, "K"),
						new Beats(startTime + term * 401, "D"), new Beats(startTime + term * 401, "K"),
						new Beats(startTime + term * 405, "S"), new Beats(startTime + term * 405, "L", true),
						new Beats(startTime + term * 409, "D"), new Beats(startTime + term * 409, "K"),
						new Beats(startTime + term * 413, "D"), new Beats(startTime + term * 413, "K"),
						new Beats(startTime + term * 417, "Space"), new Beats(startTime + term * 421, "F"),
						new Beats(startTime + term * 421, "J"), new Beats(startTime + term * 425, "F"),
						new Beats(startTime + term * 425, "J"), new Beats(startTime + term * 429, "D"),
						new Beats(startTime + term * 429, "K"), new Beats(startTime + term * 433, "F", true),
						new Beats(startTime + term * 433, "J"), new Beats(startTime + term * 437, "F"),
						new Beats(startTime + term * 437, "J"), new Beats(startTime + term * 441, "S"),
						new Beats(startTime + term * 441, "L"), new Beats(startTime + term * 445, "F"),
						new Beats(startTime + term * 445, "J"), new Beats(startTime + term * 449, "F"),
						new Beats(startTime + term * 449, "J"), new Beats(startTime + term * 453, "Space", true),
						new Beats(startTime + term * 457, "F"), new Beats(startTime + term * 457, "J"),
						new Beats(startTime + term * 461, "S"), new Beats(startTime + term * 461, "L"),
						new Beats(startTime + term * 465, "F"), new Beats(startTime + term * 465, "J"),
						new Beats(startTime + term * 469, "F"), new Beats(startTime + term * 469, "J"),
						new Beats(startTime + term * 473, "Space", true),

				};
				System.out.println("INIT");
			} // 어려움 모드
		} // STARGAZER의 악보

		else if (titleName.equals("WE WILL ROCK YOU")) {// WE WILL ROCK YOU 곡
			if (difficulty.equals("EASY")) {
				int startTime = 4200;// 시작 시간 맞춤
				int term = 125;//
				System.out.println("START");
				BEATS = new Beats[] { new Beats(startTime + term, "D"), new Beats(startTime + term, "K"),
						new Beats(startTime + term * 5, "D"), new Beats(startTime + term * 5, "K"),
						new Beats(startTime + term * 9, "S"), new Beats(startTime + term * 9, "L"),
						new Beats(startTime + term * 13, "D", true), new Beats(startTime + term * 13, "K"),
						new Beats(startTime + term * 17, "D"), new Beats(startTime + term * 17, "K"),
						new Beats(startTime + term * 21, "S"), new Beats(startTime + term * 21, "L"),
						new Beats(startTime + term * 25, "D"), new Beats(startTime + term * 25, "K"),
						new Beats(startTime + term * 29, "D"), new Beats(startTime + term * 29, "K", true),
						new Beats(startTime + term * 33, "S"), new Beats(startTime + term * 33, "L"),
						new Beats(startTime + term * 37, "D"), new Beats(startTime + term * 37, "K"),
						new Beats(startTime + term * 41, "D"), new Beats(startTime + term * 41, "K"),
						new Beats(startTime + term * 45, "S"), new Beats(startTime + term * 45, "L", true),
						new Beats(startTime + term * 49, "D"), new Beats(startTime + term * 49, "K"),
						new Beats(startTime + term * 53, "D"), new Beats(startTime + term * 53, "K"),
						new Beats(startTime + term * 57, "S", true), new Beats(startTime + term * 57, "L"),
						new Beats(startTime + term * 61, "D"), new Beats(startTime + term * 61, "K"),
						new Beats(startTime + term * 65, "D"), new Beats(startTime + term * 65, "K"),
						new Beats(startTime + term * 69, "S"), new Beats(startTime + term * 69, "L"),
						new Beats(startTime + term * 73, "D", true), new Beats(startTime + term * 73, "K"),
						new Beats(startTime + term * 77, "D"), new Beats(startTime + term * 77, "K"),
						new Beats(startTime + term * 81, "S"), new Beats(startTime + term * 81, "L"),
						new Beats(startTime + term * 85, "D"), new Beats(startTime + term * 85, "K"),
						new Beats(startTime + term * 89, "D"), new Beats(startTime + term * 89, "K"),
						new Beats(startTime + term * 93, "S"), new Beats(startTime + term * 93, "L", true),
						new Beats(startTime + term * 97, "D"), new Beats(startTime + term * 97, "K"),
						new Beats(startTime + term * 101, "D"), new Beats(startTime + term * 101, "K"),
						new Beats(startTime + term * 105, "S"), new Beats(startTime + term * 105, "L"),
						new Beats(startTime + term * 109, "D"), new Beats(startTime + term * 109, "K"),
						new Beats(startTime + term * 113, "D"), new Beats(startTime + term * 113, "K"),
						new Beats(startTime + term * 117, "S", true), new Beats(startTime + term * 117, "L"),
						new Beats(startTime + term * 121, "D"), new Beats(startTime + term * 121, "K"),
						new Beats(startTime + term * 125, "D"), new Beats(startTime + term * 125, "K"),
						new Beats(startTime + term * 129, "S"), new Beats(startTime + term * 129, "L"),
						new Beats(startTime + term * 133, "D"), new Beats(startTime + term * 133, "K", true),
						new Beats(startTime + term * 137, "D"), new Beats(startTime + term * 137, "K"),
						new Beats(startTime + term * 141, "S"), new Beats(startTime + term * 141, "L"),
						new Beats(startTime + term * 145, "D"), new Beats(startTime + term * 145, "K", true),
						new Beats(startTime + term * 149, "D"), new Beats(startTime + term * 149, "K"),
						new Beats(startTime + term * 153, "S"), new Beats(startTime + term * 153, "L"),
						new Beats(startTime + term * 157, "D", true), new Beats(startTime + term * 157, "K"),
						new Beats(startTime + term * 161, "D"), new Beats(startTime + term * 161, "K"),
						new Beats(startTime + term * 165, "S"), new Beats(startTime + term * 165, "L"),
						new Beats(startTime + term * 169, "D"), new Beats(startTime + term * 169, "K"),
						new Beats(startTime + term * 173, "D"), new Beats(startTime + term * 173, "K"),
						new Beats(startTime + term * 177, "S"), new Beats(startTime + term * 177, "L"),
						new Beats(startTime + term * 181, "D", true), new Beats(startTime + term * 181, "K"),
						new Beats(startTime + term * 185, "D"), new Beats(startTime + term * 185, "K"),
						new Beats(startTime + term * 189, "S"), new Beats(startTime + term * 189, "L"),
						new Beats(startTime + term * 193, "D"), new Beats(startTime + term * 193, "K"),
						new Beats(startTime + term * 197, "D", true), new Beats(startTime + term * 197, "K"),
						new Beats(startTime + term * 201, "S"), new Beats(startTime + term * 201, "L", true),
						new Beats(startTime + term * 205, "D"), new Beats(startTime + term * 205, "K"),
						new Beats(startTime + term * 209, "D", true), new Beats(startTime + term * 209, "K"),
						new Beats(startTime + term * 213, "S"), new Beats(startTime + term * 213, "L"),
						new Beats(startTime + term * 217, "D"), new Beats(startTime + term * 217, "K"),
						new Beats(startTime + term * 221, "D"), new Beats(startTime + term * 221, "K"),
						new Beats(startTime + term * 225, "S"), new Beats(startTime + term * 225, "L"),
						new Beats(startTime + term * 229, "D"), new Beats(startTime + term * 229, "K", true),
						new Beats(startTime + term * 233, "D"), new Beats(startTime + term * 233, "K"),
						new Beats(startTime + term * 237, "S"), new Beats(startTime + term * 237, "L"),
						new Beats(startTime + term * 241, "D"), new Beats(startTime + term * 241, "K"),
						new Beats(startTime + term * 245, "D", true), new Beats(startTime + term * 245, "K"),
						new Beats(startTime + term * 249, "S"), new Beats(startTime + term * 249, "L"),
						new Beats(startTime + term * 253, "D"), new Beats(startTime + term * 253, "K"),
						new Beats(startTime + term * 257, "D"), new Beats(startTime + term * 257, "K"),
						new Beats(startTime + term * 261, "S"), new Beats(startTime + term * 261, "L"),
						new Beats(startTime + term * 265, "D"), new Beats(startTime + term * 265, "K"),
						new Beats(startTime + term * 269, "D"), new Beats(startTime + term * 269, "K", true),
						new Beats(startTime + term * 273, "S"), new Beats(startTime + term * 273, "L"),
						new Beats(startTime + term * 277, "D"), new Beats(startTime + term * 277, "K"),
						new Beats(startTime + term * 281, "D", true), new Beats(startTime + term * 281, "K"),
						new Beats(startTime + term * 285, "S"), new Beats(startTime + term * 285, "L"),
						new Beats(startTime + term * 289, "D"), new Beats(startTime + term * 289, "K"),
						new Beats(startTime + term * 293, "D"), new Beats(startTime + term * 293, "K"),
						new Beats(startTime + term * 297, "S"), new Beats(startTime + term * 297, "L", true),
						new Beats(startTime + term * 301, "D"), new Beats(startTime + term * 301, "K"),
						new Beats(startTime + term * 305, "D"), new Beats(startTime + term * 305, "K"),
						new Beats(startTime + term * 309, "S", true), new Beats(startTime + term * 309, "L"),
						new Beats(startTime + term * 313, "D"), new Beats(startTime + term * 313, "K"),
						new Beats(startTime + term * 317, "D"), new Beats(startTime + term * 317, "K"),
						new Beats(startTime + term * 321, "S"), new Beats(startTime + term * 321, "L"),
						new Beats(startTime + term * 325, "D"), new Beats(startTime + term * 325, "K"),
						new Beats(startTime + term * 329, "D"), new Beats(startTime + term * 329, "K", true),
						new Beats(startTime + term * 333, "S"), new Beats(startTime + term * 333, "L"),
						new Beats(startTime + term * 337, "D"), new Beats(startTime + term * 337, "K"),
						new Beats(startTime + term * 341, "D", true), new Beats(startTime + term * 341, "K"),
						new Beats(startTime + term * 345, "S"), new Beats(startTime + term * 345, "L"),
						new Beats(startTime + term * 349, "D"), new Beats(startTime + term * 349, "K"),
						new Beats(startTime + term * 353, "D"), new Beats(startTime + term * 353, "K"),
						new Beats(startTime + term * 357, "S"), new Beats(startTime + term * 357, "L"),
						new Beats(startTime + term * 361, "D"), new Beats(startTime + term * 361, "K"),
						new Beats(startTime + term * 365, "D"), new Beats(startTime + term * 365, "K"),
						new Beats(startTime + term * 369, "S"), new Beats(startTime + term * 369, "L"),
						new Beats(startTime + term * 373, "D"), new Beats(startTime + term * 373, "K"),
						new Beats(startTime + term * 377, "D", true), new Beats(startTime + term * 377, "K"),
						new Beats(startTime + term * 381, "S"), new Beats(startTime + term * 381, "L"),
						new Beats(startTime + term * 385, "D"), new Beats(startTime + term * 385, "K", true),
						new Beats(startTime + term * 389, "D"), new Beats(startTime + term * 389, "K"),
						new Beats(startTime + term * 393, "S"), new Beats(startTime + term * 393, "L"),
						new Beats(startTime + term * 397, "D"), new Beats(startTime + term * 397, "K"),
						new Beats(startTime + term * 401, "D"), new Beats(startTime + term * 401, "K"),
						new Beats(startTime + term * 405, "S"), new Beats(startTime + term * 405, "L"),
						new Beats(startTime + term * 409, "D", true), new Beats(startTime + term * 409, "K"),
						new Beats(startTime + term * 413, "D"), new Beats(startTime + term * 413, "K"),
						new Beats(startTime + term * 417, "S"), new Beats(startTime + term * 417, "L"),
						new Beats(startTime + term * 421, "D"), new Beats(startTime + term * 421, "K"),
						new Beats(startTime + term * 425, "D"), new Beats(startTime + term * 425, "K"),
						new Beats(startTime + term * 429, "S"), new Beats(startTime + term * 429, "L"),
						new Beats(startTime + term * 433, "D", true), new Beats(startTime + term * 433, "K"),
						new Beats(startTime + term * 437, "D"), new Beats(startTime + term * 437, "K"),
						new Beats(startTime + term * 441, "S"), new Beats(startTime + term * 441, "L"),
						new Beats(startTime + term * 445, "D"), new Beats(startTime + term * 445, "K"),
						new Beats(startTime + term * 449, "D"), new Beats(startTime + term * 449, "K", true),
						new Beats(startTime + term * 453, "S"), new Beats(startTime + term * 453, "L"),

				};
				// 비트 칸
			} // 쉬움
			else if (difficulty.equals("HARD")) {
				int startTime = 4200;// 시작 시간 맞춤
				int term = 125;// 비트 약 125
				System.out.println("START");
				BEATS = new Beats[] { new Beats(startTime + term, "D"), new Beats(startTime + term, "K"),
						new Beats(startTime + term * 5, "D"), new Beats(startTime + term * 5, "K"),
						new Beats(startTime + term * 9, "S"), new Beats(startTime + term * 9, "L"),
						new Beats(startTime + term * 13, "D"), new Beats(startTime + term * 13, "K"),
						new Beats(startTime + term * 17, "D"), new Beats(startTime + term * 17, "K"),
						new Beats(startTime + term * 21, "F"), new Beats(startTime + term * 21, "J"),
						new Beats(startTime + term * 25, "D"), new Beats(startTime + term * 25, "K"),
						new Beats(startTime + term * 29, "D"), new Beats(startTime + term * 29, "K"),
						new Beats(startTime + term * 33, "S"), new Beats(startTime + term * 33, "L"),
						new Beats(startTime + term * 37, "D"), new Beats(startTime + term * 37, "K"),
						new Beats(startTime + term * 41, "D"), new Beats(startTime + term * 41, "K"),
						new Beats(startTime + term * 45, "F"), new Beats(startTime + term * 45, "J"),
						new Beats(startTime + term * 49, "D"), new Beats(startTime + term * 49, "K"),
						new Beats(startTime + term * 53, "D"), new Beats(startTime + term * 53, "K"),
						new Beats(startTime + term * 57, "Space"), new Beats(startTime + term * 61, "D"),
						new Beats(startTime + term * 61, "K"), new Beats(startTime + term * 65, "D"),
						new Beats(startTime + term * 65, "K"), new Beats(startTime + term * 69, "S"),
						new Beats(startTime + term * 69, "L"), new Beats(startTime + term * 73, "D"),
						new Beats(startTime + term * 73, "K"), new Beats(startTime + term * 77, "D"),
						new Beats(startTime + term * 77, "K"), new Beats(startTime + term * 81, "F"),
						new Beats(startTime + term * 81, "J"), new Beats(startTime + term * 85, "D"),
						new Beats(startTime + term * 85, "K"), new Beats(startTime + term * 89, "D"),
						new Beats(startTime + term * 89, "K"), new Beats(startTime + term * 93, "S"),
						new Beats(startTime + term * 93, "L"), new Beats(startTime + term * 97, "D"),
						new Beats(startTime + term * 97, "K"), new Beats(startTime + term * 101, "D"),
						new Beats(startTime + term * 101, "K"), new Beats(startTime + term * 105, "F"),
						new Beats(startTime + term * 105, "J"), new Beats(startTime + term * 109, "D"),
						new Beats(startTime + term * 109, "K"), new Beats(startTime + term * 113, "D"),
						new Beats(startTime + term * 113, "K"), new Beats(startTime + term * 117, "S"),
						new Beats(startTime + term * 117, "L"), new Beats(startTime + term * 121, "D"),
						new Beats(startTime + term * 121, "K"), new Beats(startTime + term * 125, "D"),
						new Beats(startTime + term * 125, "K"), new Beats(startTime + term * 129, "Space"),
						new Beats(startTime + term * 133, "D"), new Beats(startTime + term * 133, "K"),
						new Beats(startTime + term * 137, "D"), new Beats(startTime + term * 137, "K"),
						new Beats(startTime + term * 141, "Space"), new Beats(startTime + term * 145, "D"),
						new Beats(startTime + term * 145, "K"), new Beats(startTime + term * 149, "D"),
						new Beats(startTime + term * 149, "K"), new Beats(startTime + term * 153, "S"),
						new Beats(startTime + term * 153, "L"), new Beats(startTime + term * 157, "D"),
						new Beats(startTime + term * 157, "K"), new Beats(startTime + term * 161, "D"),
						new Beats(startTime + term * 161, "K"), new Beats(startTime + term * 165, "F"),
						new Beats(startTime + term * 165, "J"), new Beats(startTime + term * 169, "D"),
						new Beats(startTime + term * 169, "K"), new Beats(startTime + term * 173, "D"),
						new Beats(startTime + term * 173, "K"), new Beats(startTime + term * 177, "S"),
						new Beats(startTime + term * 177, "L"), new Beats(startTime + term * 181, "D"),
						new Beats(startTime + term * 181, "K"), new Beats(startTime + term * 185, "D"),
						new Beats(startTime + term * 185, "K"), new Beats(startTime + term * 189, "S"),
						new Beats(startTime + term * 189, "L"), new Beats(startTime + term * 193, "D"),
						new Beats(startTime + term * 193, "K"), new Beats(startTime + term * 197, "D"),
						new Beats(startTime + term * 197, "K"), new Beats(startTime + term * 201, "F"),
						new Beats(startTime + term * 201, "J"), new Beats(startTime + term * 205, "D"),
						new Beats(startTime + term * 205, "K"), new Beats(startTime + term * 209, "D"),
						new Beats(startTime + term * 209, "K"), new Beats(startTime + term * 213, "S"),
						new Beats(startTime + term * 213, "L"), new Beats(startTime + term * 217, "D"),
						new Beats(startTime + term * 217, "K"), new Beats(startTime + term * 221, "D"),
						new Beats(startTime + term * 221, "K"), new Beats(startTime + term * 225, "S"),
						new Beats(startTime + term * 225, "L"), new Beats(startTime + term * 229, "D"),
						new Beats(startTime + term * 229, "K"), new Beats(startTime + term * 233, "D"),
						new Beats(startTime + term * 233, "K"), new Beats(startTime + term * 237, "Space"),
						new Beats(startTime + term * 241, "D"), new Beats(startTime + term * 241, "K"),
						new Beats(startTime + term * 245, "D"), new Beats(startTime + term * 245, "K"),
						new Beats(startTime + term * 249, "S"), new Beats(startTime + term * 249, "L"),
						new Beats(startTime + term * 253, "D"), new Beats(startTime + term * 253, "K"),
						new Beats(startTime + term * 257, "D"), new Beats(startTime + term * 257, "K"),
						new Beats(startTime + term * 261, "S"), new Beats(startTime + term * 261, "L"),
						new Beats(startTime + term * 265, "D"), new Beats(startTime + term * 265, "K"),
						new Beats(startTime + term * 269, "D"), new Beats(startTime + term * 269, "K"),
						new Beats(startTime + term * 273, "Space"), new Beats(startTime + term * 277, "D"),
						new Beats(startTime + term * 277, "K"), new Beats(startTime + term * 281, "D"),
						new Beats(startTime + term * 281, "K"), new Beats(startTime + term * 285, "S"),
						new Beats(startTime + term * 285, "L"), new Beats(startTime + term * 289, "D"),
						new Beats(startTime + term * 289, "K"), new Beats(startTime + term * 293, "D"),
						new Beats(startTime + term * 293, "K"), new Beats(startTime + term * 297, "S"),
						new Beats(startTime + term * 297, "L"), new Beats(startTime + term * 301, "D"),
						new Beats(startTime + term * 301, "K"), new Beats(startTime + term * 305, "D"),
						new Beats(startTime + term * 305, "K"), new Beats(startTime + term * 309, "F"),
						new Beats(startTime + term * 309, "J"), new Beats(startTime + term * 313, "D"),
						new Beats(startTime + term * 313, "K"), new Beats(startTime + term * 317, "D"),
						new Beats(startTime + term * 317, "K"), new Beats(startTime + term * 321, "Space"),
						new Beats(startTime + term * 325, "D"), new Beats(startTime + term * 325, "K"),
						new Beats(startTime + term * 329, "D"), new Beats(startTime + term * 329, "K"),
						new Beats(startTime + term * 333, "S"), new Beats(startTime + term * 333, "L"),
						new Beats(startTime + term * 337, "D"), new Beats(startTime + term * 337, "K"),
						new Beats(startTime + term * 341, "D"), new Beats(startTime + term * 341, "K"),
						new Beats(startTime + term * 345, "Space"), new Beats(startTime + term * 349, "D"),
						new Beats(startTime + term * 349, "K"), new Beats(startTime + term * 353, "D"),
						new Beats(startTime + term * 353, "K"), new Beats(startTime + term * 357, "S"),
						new Beats(startTime + term * 357, "L"), new Beats(startTime + term * 361, "D"),
						new Beats(startTime + term * 361, "K"), new Beats(startTime + term * 365, "D"),
						new Beats(startTime + term * 365, "K"), new Beats(startTime + term * 369, "F"),
						new Beats(startTime + term * 369, "J"), new Beats(startTime + term * 373, "D"),
						new Beats(startTime + term * 373, "K"), new Beats(startTime + term * 377, "D"),
						new Beats(startTime + term * 377, "K"), new Beats(startTime + term * 381, "F"),
						new Beats(startTime + term * 381, "J"), new Beats(startTime + term * 385, "D"),
						new Beats(startTime + term * 385, "K"), new Beats(startTime + term * 389, "D"),
						new Beats(startTime + term * 389, "K"), new Beats(startTime + term * 393, "Space"),
						new Beats(startTime + term * 397, "D"), new Beats(startTime + term * 397, "K"),
						new Beats(startTime + term * 401, "D"), new Beats(startTime + term * 401, "K"),
						new Beats(startTime + term * 405, "S"), new Beats(startTime + term * 405, "L"),
						new Beats(startTime + term * 409, "D"), new Beats(startTime + term * 409, "K"),
						new Beats(startTime + term * 413, "D"), new Beats(startTime + term * 413, "K"),
						new Beats(startTime + term * 417, "Space"), new Beats(startTime + term * 421, "F"),
						new Beats(startTime + term * 421, "J"), new Beats(startTime + term * 425, "F"),
						new Beats(startTime + term * 425, "J"), new Beats(startTime + term * 429, "D"),
						new Beats(startTime + term * 429, "K"), new Beats(startTime + term * 433, "F"),
						new Beats(startTime + term * 433, "J"), new Beats(startTime + term * 437, "F"),
						new Beats(startTime + term * 437, "J"), new Beats(startTime + term * 441, "S"),
						new Beats(startTime + term * 441, "L"), new Beats(startTime + term * 445, "F"),
						new Beats(startTime + term * 445, "J"), new Beats(startTime + term * 449, "F"),
						new Beats(startTime + term * 449, "J"), new Beats(startTime + term * 453, "Space"), };

			}

		} // WE WILL ROCK YOU 악보
		else { // MIRROR NIGHT (REFLECTION) 곡
			if (difficulty.equals("EASY")) {

				int startTime = 0;
				int term = 170;
				System.out.println("START");
				BEATS = new Beats[] { new Beats(startTime + term, "D"), new Beats(startTime + term, "K"),
						new Beats(startTime + term * 5, "D"), new Beats(startTime + term * 5, "K"),
						new Beats(startTime + term * 9, "S"), new Beats(startTime + term * 9, "L"),
						new Beats(startTime + term * 13, "D"), new Beats(startTime + term * 13, "K"),
						new Beats(startTime + term * 17, "D"), new Beats(startTime + term * 17, "K"),
						new Beats(startTime + term * 21, "S"), new Beats(startTime + term * 21, "L"),
						new Beats(startTime + term * 25, "D"), new Beats(startTime + term * 25, "K"),
						new Beats(startTime + term * 29, "D"), new Beats(startTime + term * 29, "K"),
						new Beats(startTime + term * 33, "S"), new Beats(startTime + term * 33, "L"),
						new Beats(startTime + term * 37, "D"), new Beats(startTime + term * 37, "K"),
						new Beats(startTime + term * 41, "D"), new Beats(startTime + term * 41, "K"),
						new Beats(startTime + term * 45, "S"), new Beats(startTime + term * 45, "L"),
						new Beats(startTime + term * 49, "D"), new Beats(startTime + term * 49, "K"),
						new Beats(startTime + term * 53, "D"), new Beats(startTime + term * 53, "K"),
						new Beats(startTime + term * 57, "S"), new Beats(startTime + term * 57, "L"),
						new Beats(startTime + term * 61, "D"), new Beats(startTime + term * 61, "K"),
						new Beats(startTime + term * 65, "D"), new Beats(startTime + term * 65, "K"),
						new Beats(startTime + term * 69, "S"), new Beats(startTime + term * 69, "L"),
						new Beats(startTime + term * 73, "D"), new Beats(startTime + term * 73, "K"),
						new Beats(startTime + term * 77, "D"), new Beats(startTime + term * 77, "K"),
						new Beats(startTime + term * 81, "S"), new Beats(startTime + term * 81, "L"),
						new Beats(startTime + term * 85, "D"), new Beats(startTime + term * 85, "K"),
						new Beats(startTime + term * 89, "D"), new Beats(startTime + term * 89, "K"),
						new Beats(startTime + term * 93, "S"), new Beats(startTime + term * 93, "L"),
						new Beats(startTime + term * 97, "D"), new Beats(startTime + term * 97, "K"),
						new Beats(startTime + term * 101, "D"), new Beats(startTime + term * 101, "K"),
						new Beats(startTime + term * 105, "S"), new Beats(startTime + term * 105, "L"),
						new Beats(startTime + term * 109, "D"), new Beats(startTime + term * 109, "K"),
						new Beats(startTime + term * 113, "D"), new Beats(startTime + term * 113, "K"),
						new Beats(startTime + term * 117, "S"), new Beats(startTime + term * 117, "L"),
						new Beats(startTime + term * 121, "D"), new Beats(startTime + term * 121, "K"),
						new Beats(startTime + term * 125, "D"), new Beats(startTime + term * 125, "K"),
						new Beats(startTime + term * 129, "S"), new Beats(startTime + term * 129, "L"),
						new Beats(startTime + term * 133, "D"), new Beats(startTime + term * 133, "K"),
						new Beats(startTime + term * 137, "D"), new Beats(startTime + term * 137, "K"),
						new Beats(startTime + term * 141, "S"), new Beats(startTime + term * 141, "L"),
						new Beats(startTime + term * 145, "D"), new Beats(startTime + term * 145, "K"),
						new Beats(startTime + term * 149, "D"), new Beats(startTime + term * 149, "K"),
						new Beats(startTime + term * 153, "S"), new Beats(startTime + term * 153, "L"),
						new Beats(startTime + term * 157, "D"), new Beats(startTime + term * 157, "K"),
						new Beats(startTime + term * 161, "D"), new Beats(startTime + term * 161, "K"),
						new Beats(startTime + term * 165, "S"), new Beats(startTime + term * 165, "L"),
						new Beats(startTime + term * 169, "D"), new Beats(startTime + term * 169, "K"),
						new Beats(startTime + term * 173, "D"), new Beats(startTime + term * 173, "K"),
						new Beats(startTime + term * 177, "S"), new Beats(startTime + term * 177, "L"),
						new Beats(startTime + term * 181, "D"), new Beats(startTime + term * 181, "K"),
						new Beats(startTime + term * 185, "D"), new Beats(startTime + term * 185, "K"),
						new Beats(startTime + term * 189, "S"), new Beats(startTime + term * 189, "L"),
						new Beats(startTime + term * 193, "D"), new Beats(startTime + term * 193, "K"),
						new Beats(startTime + term * 197, "D"), new Beats(startTime + term * 197, "K"),
						new Beats(startTime + term * 201, "S"), new Beats(startTime + term * 201, "L"),
						new Beats(startTime + term * 205, "D"), new Beats(startTime + term * 205, "K"),
						new Beats(startTime + term * 209, "D"), new Beats(startTime + term * 209, "K"),
						new Beats(startTime + term * 213, "S"), new Beats(startTime + term * 213, "L"),
						new Beats(startTime + term * 217, "D"), new Beats(startTime + term * 217, "K"),
						new Beats(startTime + term * 221, "D"), new Beats(startTime + term * 221, "K"),
						new Beats(startTime + term * 225, "S"), new Beats(startTime + term * 225, "L"),
						new Beats(startTime + term * 229, "D"), new Beats(startTime + term * 229, "K"),
						new Beats(startTime + term * 233, "D"), new Beats(startTime + term * 233, "K"),
						new Beats(startTime + term * 237, "S"), new Beats(startTime + term * 237, "L"),
						new Beats(startTime + term * 241, "D"), new Beats(startTime + term * 241, "K"),
						new Beats(startTime + term * 245, "D"), new Beats(startTime + term * 245, "K"),
						new Beats(startTime + term * 249, "S"), new Beats(startTime + term * 249, "L"),
						new Beats(startTime + term * 253, "D"), new Beats(startTime + term * 253, "K"),
						new Beats(startTime + term * 257, "D"), new Beats(startTime + term * 257, "K"),
						new Beats(startTime + term * 261, "S"), new Beats(startTime + term * 261, "L"),
						new Beats(startTime + term * 265, "D"), new Beats(startTime + term * 265, "K"),
						new Beats(startTime + term * 269, "D"), new Beats(startTime + term * 269, "K"),
						new Beats(startTime + term * 273, "S"), new Beats(startTime + term * 273, "L"),
						new Beats(startTime + term * 277, "D"), new Beats(startTime + term * 277, "K"),
						new Beats(startTime + term * 281, "D"), new Beats(startTime + term * 281, "K"),
						new Beats(startTime + term * 285, "S"), new Beats(startTime + term * 285, "L"),
						new Beats(startTime + term * 289, "D"), new Beats(startTime + term * 289, "K"),
						new Beats(startTime + term * 293, "D"), new Beats(startTime + term * 293, "K"),
						new Beats(startTime + term * 297, "S"), new Beats(startTime + term * 297, "L"),
						new Beats(startTime + term * 301, "D"), new Beats(startTime + term * 301, "K"),
						new Beats(startTime + term * 305, "D"), new Beats(startTime + term * 305, "K"),
						new Beats(startTime + term * 309, "S"), new Beats(startTime + term * 309, "L"),
						new Beats(startTime + term * 313, "D"), new Beats(startTime + term * 313, "K"),
						new Beats(startTime + term * 317, "D"), new Beats(startTime + term * 317, "K"),
						new Beats(startTime + term * 321, "S"), new Beats(startTime + term * 321, "L"),
						new Beats(startTime + term * 325, "D"), new Beats(startTime + term * 325, "K"),
						new Beats(startTime + term * 329, "D"), new Beats(startTime + term * 329, "K"),
						new Beats(startTime + term * 333, "S"), new Beats(startTime + term * 333, "L"),
						new Beats(startTime + term * 337, "D"), new Beats(startTime + term * 337, "K"),
						new Beats(startTime + term * 341, "D"), new Beats(startTime + term * 341, "K"),
						new Beats(startTime + term * 345, "S"), new Beats(startTime + term * 345, "L"),
						new Beats(startTime + term * 349, "D"), new Beats(startTime + term * 349, "K"),
						new Beats(startTime + term * 353, "D"), new Beats(startTime + term * 353, "K"),
						new Beats(startTime + term * 357, "S"), new Beats(startTime + term * 357, "L"),
						new Beats(startTime + term * 361, "D"), new Beats(startTime + term * 361, "K"),
						new Beats(startTime + term * 365, "D"), new Beats(startTime + term * 365, "K"),
						new Beats(startTime + term * 369, "S"), new Beats(startTime + term * 369, "L"),
						new Beats(startTime + term * 373, "D"), new Beats(startTime + term * 373, "K"),
						new Beats(startTime + term * 377, "D"), new Beats(startTime + term * 377, "K"),
						new Beats(startTime + term * 381, "S"), new Beats(startTime + term * 381, "L"),
						new Beats(startTime + term * 385, "D"), new Beats(startTime + term * 385, "K"),
						new Beats(startTime + term * 389, "D"), new Beats(startTime + term * 389, "K"),
						new Beats(startTime + term * 393, "S"), new Beats(startTime + term * 393, "L"),
						new Beats(startTime + term * 397, "D"), new Beats(startTime + term * 397, "K"),
						new Beats(startTime + term * 401, "D"), new Beats(startTime + term * 401, "K"),
						new Beats(startTime + term * 405, "S"), new Beats(startTime + term * 405, "L"),
						new Beats(startTime + term * 409, "D"), new Beats(startTime + term * 409, "K"),
						new Beats(startTime + term * 413, "D"), new Beats(startTime + term * 413, "K"),
						new Beats(startTime + term * 417, "S"), new Beats(startTime + term * 417, "L"),
						new Beats(startTime + term * 421, "D"), new Beats(startTime + term * 421, "K"),
						new Beats(startTime + term * 425, "D"), new Beats(startTime + term * 425, "K"),
						new Beats(startTime + term * 429, "S"), new Beats(startTime + term * 429, "L"),
						new Beats(startTime + term * 433, "D"), new Beats(startTime + term * 433, "K"),
						new Beats(startTime + term * 437, "D"), new Beats(startTime + term * 437, "K"),
						new Beats(startTime + term * 441, "S"), new Beats(startTime + term * 441, "L"),
						new Beats(startTime + term * 445, "D"), new Beats(startTime + term * 445, "K"),
						new Beats(startTime + term * 449, "D"), new Beats(startTime + term * 449, "K"),
						new Beats(startTime + term * 453, "S"), new Beats(startTime + term * 453, "L"),

				};
			} else {
				int startTime = 0;
				int term = 170;
				System.out.println("START");
				BEATS = new Beats[] { new Beats(startTime + term, "D"), new Beats(startTime + term, "K"),
						new Beats(startTime + term * 5, "D"), new Beats(startTime + term * 5, "K"),
						new Beats(startTime + term * 9, "S"), new Beats(startTime + term * 9, "L"),
						new Beats(startTime + term * 13, "D"), new Beats(startTime + term * 13, "K"),
						new Beats(startTime + term * 17, "D"), new Beats(startTime + term * 17, "K"),
						new Beats(startTime + term * 21, "F"), new Beats(startTime + term * 21, "J"),
						new Beats(startTime + term * 25, "D"), new Beats(startTime + term * 25, "K"),
						new Beats(startTime + term * 29, "D"), new Beats(startTime + term * 29, "K"),
						new Beats(startTime + term * 33, "S"), new Beats(startTime + term * 33, "L"),
						new Beats(startTime + term * 37, "D"), new Beats(startTime + term * 37, "K"),
						new Beats(startTime + term * 41, "D"), new Beats(startTime + term * 41, "K"),
						new Beats(startTime + term * 45, "F"), new Beats(startTime + term * 45, "J"),
						new Beats(startTime + term * 49, "D"), new Beats(startTime + term * 49, "K"),
						new Beats(startTime + term * 53, "D"), new Beats(startTime + term * 53, "K"),
						new Beats(startTime + term * 57, "Space", true), new Beats(startTime + term * 61, "D"),
						new Beats(startTime + term * 61, "K"), new Beats(startTime + term * 65, "D"),
						new Beats(startTime + term * 65, "K"), new Beats(startTime + term * 69, "S"),
						new Beats(startTime + term * 69, "L", true), new Beats(startTime + term * 73, "D"),
						new Beats(startTime + term * 73, "K"), new Beats(startTime + term * 77, "D"),
						new Beats(startTime + term * 77, "K"), new Beats(startTime + term * 81, "F"),
						new Beats(startTime + term * 81, "J"), new Beats(startTime + term * 85, "D"),
						new Beats(startTime + term * 85, "K"), new Beats(startTime + term * 89, "D"),
						new Beats(startTime + term * 89, "K"), new Beats(startTime + term * 93, "S"),
						new Beats(startTime + term * 93, "L"), new Beats(startTime + term * 97, "D", true),
						new Beats(startTime + term * 97, "K"), new Beats(startTime + term * 101, "D"),
						new Beats(startTime + term * 101, "K"), new Beats(startTime + term * 105, "F"),
						new Beats(startTime + term * 105, "J"), new Beats(startTime + term * 109, "D"),
						new Beats(startTime + term * 109, "K"), new Beats(startTime + term * 113, "D"),
						new Beats(startTime + term * 113, "K", true), new Beats(startTime + term * 117, "S"),
						new Beats(startTime + term * 117, "L"), new Beats(startTime + term * 121, "D"),
						new Beats(startTime + term * 121, "K"), new Beats(startTime + term * 125, "D"),
						new Beats(startTime + term * 125, "K"), new Beats(startTime + term * 129, "Space"),
						new Beats(startTime + term * 133, "D"), new Beats(startTime + term * 133, "K"),
						new Beats(startTime + term * 137, "D"), new Beats(startTime + term * 137, "K"),
						new Beats(startTime + term * 141, "Space", true), new Beats(startTime + term * 145, "D"),
						new Beats(startTime + term * 145, "K"), new Beats(startTime + term * 149, "D"),
						new Beats(startTime + term * 149, "K"), new Beats(startTime + term * 153, "S"),
						new Beats(startTime + term * 153, "L"), new Beats(startTime + term * 157, "D"),
						new Beats(startTime + term * 157, "K"), new Beats(startTime + term * 161, "D"),
						new Beats(startTime + term * 161, "K", true), new Beats(startTime + term * 165, "F"),
						new Beats(startTime + term * 165, "J"), new Beats(startTime + term * 169, "D"),
						new Beats(startTime + term * 169, "K"), new Beats(startTime + term * 173, "D"),
						new Beats(startTime + term * 173, "K", true), new Beats(startTime + term * 177, "S"),
						new Beats(startTime + term * 177, "L"), new Beats(startTime + term * 181, "D", true),
						new Beats(startTime + term * 181, "K"), new Beats(startTime + term * 185, "D"),
						new Beats(startTime + term * 185, "K"), new Beats(startTime + term * 189, "S"),
						new Beats(startTime + term * 189, "L"), new Beats(startTime + term * 193, "D"),
						new Beats(startTime + term * 193, "K"), new Beats(startTime + term * 197, "D"),
						new Beats(startTime + term * 197, "K"), new Beats(startTime + term * 201, "F", true),
						new Beats(startTime + term * 201, "J"), new Beats(startTime + term * 205, "D"),
						new Beats(startTime + term * 205, "K"), new Beats(startTime + term * 209, "D"),
						new Beats(startTime + term * 209, "K"), new Beats(startTime + term * 213, "S"),
						new Beats(startTime + term * 213, "L"), new Beats(startTime + term * 217, "D"),
						new Beats(startTime + term * 217, "K"), new Beats(startTime + term * 221, "D"),
						new Beats(startTime + term * 221, "K", true), new Beats(startTime + term * 225, "S"),
						new Beats(startTime + term * 225, "L"), new Beats(startTime + term * 229, "D", true),
						new Beats(startTime + term * 229, "K"), new Beats(startTime + term * 233, "D"),
						new Beats(startTime + term * 233, "K"), new Beats(startTime + term * 237, "Space"),
						new Beats(startTime + term * 241, "D"), new Beats(startTime + term * 241, "K"),
						new Beats(startTime + term * 245, "D"), new Beats(startTime + term * 245, "K"),
						new Beats(startTime + term * 249, "S"), new Beats(startTime + term * 249, "L"),
						new Beats(startTime + term * 253, "D"), new Beats(startTime + term * 253, "K"),
						new Beats(startTime + term * 257, "D"), new Beats(startTime + term * 257, "K"),
						new Beats(startTime + term * 261, "S"), new Beats(startTime + term * 261, "L"),
						new Beats(startTime + term * 265, "D"), new Beats(startTime + term * 265, "K"),
						new Beats(startTime + term * 269, "D"), new Beats(startTime + term * 269, "K"),
						new Beats(startTime + term * 273, "Space"), new Beats(startTime + term * 277, "D"),
						new Beats(startTime + term * 277, "K"), new Beats(startTime + term * 281, "D"),
						new Beats(startTime + term * 281, "K"), new Beats(startTime + term * 285, "S", true),
						new Beats(startTime + term * 285, "L"), new Beats(startTime + term * 289, "D"),
						new Beats(startTime + term * 289, "K"), new Beats(startTime + term * 293, "D"),
						new Beats(startTime + term * 293, "K", true), new Beats(startTime + term * 297, "S"),
						new Beats(startTime + term * 297, "L"), new Beats(startTime + term * 301, "D"),
						new Beats(startTime + term * 301, "K", true), new Beats(startTime + term * 305, "D"),
						new Beats(startTime + term * 305, "K"), new Beats(startTime + term * 309, "F", true),
						new Beats(startTime + term * 309, "J"), new Beats(startTime + term * 313, "D"),
						new Beats(startTime + term * 313, "K"), new Beats(startTime + term * 317, "D"),
						new Beats(startTime + term * 317, "K"), new Beats(startTime + term * 321, "Space"),
						new Beats(startTime + term * 325, "D"), new Beats(startTime + term * 325, "K"),
						new Beats(startTime + term * 329, "D"), new Beats(startTime + term * 329, "K"),
						new Beats(startTime + term * 333, "S"), new Beats(startTime + term * 333, "L"),
						new Beats(startTime + term * 337, "D", true), new Beats(startTime + term * 337, "K"),
						new Beats(startTime + term * 341, "D"), new Beats(startTime + term * 341, "K"),
						new Beats(startTime + term * 345, "Space", true), new Beats(startTime + term * 349, "D"),
						new Beats(startTime + term * 349, "K"), new Beats(startTime + term * 353, "D"),
						new Beats(startTime + term * 353, "K"), new Beats(startTime + term * 357, "S"),
						new Beats(startTime + term * 357, "L"), new Beats(startTime + term * 361, "D"),
						new Beats(startTime + term * 361, "K"), new Beats(startTime + term * 365, "D"),
						new Beats(startTime + term * 365, "K"), new Beats(startTime + term * 369, "F"),
						new Beats(startTime + term * 369, "J"), new Beats(startTime + term * 373, "D"),
						new Beats(startTime + term * 373, "K", true), new Beats(startTime + term * 377, "D"),
						new Beats(startTime + term * 377, "K"), new Beats(startTime + term * 381, "F"),
						new Beats(startTime + term * 381, "J"), new Beats(startTime + term * 385, "D"),
						new Beats(startTime + term * 385, "K"), new Beats(startTime + term * 389, "D"),
						new Beats(startTime + term * 389, "K"), new Beats(startTime + term * 393, "Space"),
						new Beats(startTime + term * 397, "D"), new Beats(startTime + term * 397, "K"),
						new Beats(startTime + term * 401, "D"), new Beats(startTime + term * 401, "K"),
						new Beats(startTime + term * 405, "S"), new Beats(startTime + term * 405, "L"),
						new Beats(startTime + term * 409, "D", true), new Beats(startTime + term * 409, "K"),
						new Beats(startTime + term * 413, "D"), new Beats(startTime + term * 413, "K"),
						new Beats(startTime + term * 417, "Space"), new Beats(startTime + term * 421, "F"),
						new Beats(startTime + term * 421, "J"), new Beats(startTime + term * 425, "F"),
						new Beats(startTime + term * 425, "J"), new Beats(startTime + term * 429, "D"),
						new Beats(startTime + term * 429, "K"), new Beats(startTime + term * 433, "F"),
						new Beats(startTime + term * 433, "J"), new Beats(startTime + term * 437, "F"),
						new Beats(startTime + term * 437, "J", true), new Beats(startTime + term * 441, "S"),
						new Beats(startTime + term * 441, "L"), new Beats(startTime + term * 445, "F"),
						new Beats(startTime + term * 445, "J"), new Beats(startTime + term * 449, "F"),
						new Beats(startTime + term * 449, "J"), new Beats(startTime + term * 453, "Space"),

				};

			}
		} // REFELECTION 악보
		System.out.println("DROPS");
		// 떨어트리기를 시작하기 전단계이다.
		PutThingsIn();// 싱크 맞추기
		// 이제 악보를 받았으니 그 악보에 따라 노트를 떨어트려주면 된다.

	}// START

	/// PutThingsIn은 악보를 받은후 그에 따라 떨어트려줄수 있도록 싱크를 맞춘다.
	// 주석 완료
	public void PutThingsIn() {
		int i = 0;
		gameMusic.start();
		// 게임음악을 시작하고 BEATS배열 처음부터 접근하여
		while (i < BEATS.length && !this.isInterrupted()) {
			// i가 0부터 BEATS끝전까지이며 game이 interrupted가 아닐시에
			boolean dropped = false;// 초기 dropped를 거짓으로 선언
			if (BEATS[i].getTime() <= gameMusic.getTime()) {
				// 비트에 대해 정한 시간과 음악 실행시간을 받아와 비교하여 떨어트려주는 방식으로 싱크를 맞춘다.
				boolean special = BEATS[i].getSpecial();// 비트의 special판별여부를 받아서
				if (special == true) {// 만약 special이면
					Note note = new Note(BEATS[i].getNoteName(), special);
					// 노트 생성자에 special 값에 true값을 넣고 그 생성자로 만든후
					note.start();// 노트 실행
					noteList.add(note);// 관리하기 편하게 리스트에 넣는다.
					i++;// 또한 다음 비트에 접근할수 있도록 i를 증가시킨다.
				} else {
					Note note = new Note(BEATS[i].getNoteName());
					// 일반노트일 경우 파라미터를 하나만 받는 생성자를 사용하여
					note.start();// 시작시키고
					noteList.add(note);// 리스트에 추가시킨다.
					i++;// 또한 다음 비트에 접근할수 있도록 i를 증가시킨다.
				}
				dropped = true;
			} // 시간 전꺼만 받을수 있도록
			if (!dropped) {// 안떨어졌으면
				try {
					if (!Thread.interrupted())// 쓰레드가 안멈쳤으면
					{
						Thread.sleep(3);// 잠시 재운다
						// 안재웠을시에 너무 빨라 보임
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		} // while문
		MusicFlag = true;
		// 나중에 음악이 끝날때를 알려주기 위한 flag
		// 이것을 확인 안하면 시작할때 어쩔때마다 점수곡이 시작되는 경우가 있기에
		// 그것을 막기 위하여 사용하였다.
	}

	// JUDGELEFT와 JUDGERIGHT의 차이는 sdf에대해 반응하느냐 아니냐이다.
	// 동시 키값이 무시되는 경우가 있어서 나누었다.
	// 이 함수는 입력받은 키값과 noteList내에 있는 가장 가까운 같은 키와 비교하여 판정을 얻어내는 것이다.
	// LEFT와 RIGHT함수는 완전히 동일하므로 설명은 LEFT에대해서만 하였다./
	public void judgeLeft(String input) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);// 노트 리스트 사이의 노트중 하나 받아와서
			if (input.equals(note.getNoteType())) {// 입력받은 노트와 노트의 타입이 같은경우
				boolean special = note.special;
				returnedValueOfNoteJudgement = note.judge();//
				if (returnedValueOfNoteJudgement.equals("EARLY")) {
					if (special) {
						JUDGEMENT = "SPECIAL EARLY";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += EARLY;
						JUDGEMENT = "EARLY";
						combo++;
					}
				} else if (returnedValueOfNoteJudgement.equals("GOOD")) {
					if (special) {
						JUDGEMENT = "SPECIAL GOOD";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += GOOD;
						JUDGEMENT = "GOOD";
						combo++;
					}
				} else if (returnedValueOfNoteJudgement.equals("LATE")) {

					if (special) {
						JUDGEMENT = "SPECIAL LATE";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += LATE;
						JUDGEMENT = "LATE";
						combo++;
					}

				} else if (returnedValueOfNoteJudgement.equals("PERFECT")) {
					if (special) {
						JUDGEMENT = "SPECIAL PERFECT";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += PERFECT;
						JUDGEMENT = "PERFECT";
						combo++;
					}

				} else if (returnedValueOfNoteJudgement.equals("GREAT")) {
					if (special) {
						JUDGEMENT = "SPECIAL GREAT";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {

						Gamescore += GREAT;
						JUDGEMENT = "GREAT";
						combo++;
					}

				} else {// y가 535보다 위에서 찍히거나 MISS인 경우
					combo = 0;
					if (special) {
						JUDGEMENT = "SPECIAL MISS";

					}
				}
				if (combo >= Maxcombo)
					Maxcombo = combo;

				break;
			}
		}
	}

	public void judgeRight(String input) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);// 노트 리스트 사이의 노트중 하나 받아와서
			if (input.equals(note.getNoteType())) {// 입력받은 노트와 노트의 타입이 같은경우
				boolean special = note.special;
				returnedValueOfNoteJudgement = note.judge();//
				if (returnedValueOfNoteJudgement.equals("EARLY")) {
					if (special) {
						JUDGEMENT = "SPECIAL EARLY";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += EARLY;
						JUDGEMENT = "EARLY";
						combo++;
					}
				} else if (returnedValueOfNoteJudgement.equals("GOOD")) {
					if (special) {
						JUDGEMENT = "SPECIAL GOOD";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += GOOD;
						JUDGEMENT = "GOOD";
						combo++;
					}
				} else if (returnedValueOfNoteJudgement.equals("LATE")) {

					if (special) {
						JUDGEMENT = "SPECIAL LATE";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += LATE;
						JUDGEMENT = "LATE";
						combo++;
					}

				} else if (returnedValueOfNoteJudgement.equals("PERFECT")) {
					if (special) {
						JUDGEMENT = "SPECIAL PERFECT";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {
						Gamescore += PERFECT;
						JUDGEMENT = "PERFECT";
						combo++;
					}

				} else if (returnedValueOfNoteJudgement.equals("GREAT")) {
					if (special) {
						JUDGEMENT = "SPECIAL GREAT";
						Gamescore += SpecialNotes;
						combo = combo + 5;
					} else {

						Gamescore += GREAT;
						JUDGEMENT = "GREAT";
						combo++;
					}

				} else {// y가 535보다 위에서 찍히거나 MISS인 경우
					combo = 0;
					if (special) {
						JUDGEMENT = "SPECIAL MISS";

					}
				}
				if (combo >= Maxcombo)
					Maxcombo = combo;

				break;
			}
		}
	}
}
