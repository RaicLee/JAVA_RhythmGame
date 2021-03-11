package FINAL;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	// 노트 이미지
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	// Beats에 Special
	private Image noteSpecialImage = new ImageIcon(Main.class.getResource("../images/specialImage.png")).getImage();
	private Image noteYellowImage = new ImageIcon(Main.class.getResource("../images/yellow.png")).getImage();
	private Image notePurpleImage = new ImageIcon(Main.class.getResource("../images/purple.png")).getImage();

	// 노트의 이미지이다.
	// 기본 노트이미지, 노란색이미지,특별이미지,보라 이미지들을 선언하였다
	// 사용하는것은 noteBasicImage로서 만약 필요하다면 그 이미지만 바꿔서 나타낸다./

	private int x, y = -100;// 모든 노트들은 높이 -100에서 만들어진다.
	// x는 어떤 위치에 그려질에 대한 것이고, y는 무조건 -100으로 사용자가 안보이는곳에서부터 노트를 떨어지도록 하는
	// 초기값이다.

	private String noteType;
	private boolean proceeding = true;
	public boolean special = false;
	// 타입, 진행중, 스폐셜 노트등을 나타낸다.

	public boolean isProceeding() {
		return proceeding;
	}
	// 실행중인지 값을 얻기 위한 method

	public String getNoteType() {
		return noteType;
	}
	// 노트타입 받는용

	public void close() {
		proceeding = false;
	}
	// 진행상황에 거짓을준다

	public void KILLTHREAD() {
		if (this != null) {
			interrupt();
		}
	}
	// noteList에서 노트를 죽이지 않으면
	// 게임도중에 나올경우 계속 실행하게 되므로
	// 그것을 방지하기 위하여 선언하였다.

	public Note(String noteType) {
		// 기본 생성자이다.
		// noteType에 따라 어느 위치에 그려질지 정해지고
		// 그에 따라 어떤 이미지로 그려질지 정한다.
		if (noteType.equals("S")) {
			x = 228;
		} else if (noteType.equals("D")) {
			x = 332;
			noteBasicImage = noteYellowImage;
		} else if (noteType.equals("F")) {
			x = 436;
		} else if (noteType.equals("Space")) {
			x = 540;
			noteBasicImage = notePurpleImage;
		} else if (noteType.equals("J")) {
			x = 744;
		} else if (noteType.equals("K")) {
			x = 848;
			noteBasicImage = noteYellowImage;
		} else if (noteType.equals("L")) {
			x = 952;
		}
		this.noteType = noteType;

	}

	public Note(String noteType, boolean special) {
		// special이 추가된 생성자이다
		// 위치를 정하고
		// 이미지는 특별이미지로 만든다.

		if (noteType.equals("S")) {
			x = 228;
		} else if (noteType.equals("D")) {
			x = 332;
		} else if (noteType.equals("F")) {
			x = 436;
		} else if (noteType.equals("Space")) {
			x = 540;
		} else if (noteType.equals("J")) {
			x = 744;
		} else if (noteType.equals("K")) {
			x = 848;
		} else if (noteType.equals("L")) {
			x = 952;
		}
		this.noteType = noteType;
		this.special = special;
		noteBasicImage = noteSpecialImage;
		// 설명은 기본 생성자와 동일
	}

	// 노트가 어떤 식으로 그려질지 정하는 함수이다.
	public void screenDraw(Graphics2D g) {
		if (noteType.contains("Space")) {
			g.drawImage(noteBasicImage, x + 100, y, null);
			g.drawImage(noteBasicImage, x, y, null);

		} else {
			g.drawImage(noteBasicImage, x, y, null);
		}
	}
	// space인 경우는 x,x+100위치에 길게 그려지고
	// 일반 경우는 하나의 노트만 그려진다.

	public void drop() {
		y = y + Main.NOTE_SPEED;
		if (y > 625) {
			System.out.println("MISS");
			Game.Gamescore += -100;
			Game.JUDGEMENT = "MISS";
			Game.combo = 0;
			Game.Life += Game.Wrong;
			close();
		}
	}

	// gamescore,judgement,combo,life,wrong등을 static 으로 선언했기에
	// Game.으로 바로 접근할수 있도록 하였다.
	// 노트 속도만큼 계속 더해준다.
	// 만약 y값이 625보다 넘어갈경우 miss에 대한 판정을 하면서
	// combo나 gamescore,life등에 대해 결과를 갱신하고
	// 그 노트를 interrupt하면서 끝낸다.
	@Override
	public void run() {
		try {
			while (true) {
				drop();
				if (proceeding) {
					Thread.sleep(Main.SLEEP_TIME);
				} // 진행중인 경우
				else {
					interrupt();
					break;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
	// 기본 run 함수에서는 노트를 노트가진행중일떄 즉 interrupt되기 전까지
	// 계속 drop 함수를 호출하여 y값에 NOTESPEED만큼 더하고
	// sleeptime만큼 재운다.

	// judge함수는 노트 각각이 어떤 위치에 있는지를 판정하여
	// 해당하는 값을 리턴하고
	// 노트의 진행여부를 나타내는 proceeding값을 거짓을 주어
	// run method안에서 노트에 대한 interrupt를 호출하게 만든다
	public String judge() {
		if (y >= 620) {
			System.out.println("LATE");
			close();
			return "LATE";
		} else if (y >= 613) {
			System.out.println("LATE");
			close();
			return "LATE";
		} else if (y >= 600) {
			System.out.println("Good");
			close();
			return "GOOD";
		} else if (y >= 590) {
			System.out.println("Great");
			close();
			return "GREAT";
		} else if (y >= 575) {
			System.out.println("Perfect");
			close();
			return "PERFECT";
		} else if (y >= 560) {
			System.out.println("Great");
			close();
			return "GREAT";
		} else if (y >= 550) {
			System.out.println("Good");
			close();
			return "GOOD";
		} else if (y >= 535) {
			System.out.println("Early");
			close();
			return "EARLY";
		}
		return "";
	}
	// 생성자로어떤 노트타입인지를 받는다 --> 어떤 위치에 노트가 있을지를 받는다.
	// run에서 drop함수를 호출하여 실행중이면 슬립타임만큼 멈추었다가 계속 진행하고
	// 실행중이 아니면 노트를 죽인다.
	// judge함수를 사용하여 판정을 나타낸다.
	// 그림 그리는것에 대해서는 space경우 x가 100만큼 떨어진 위치에 하나 더 그려서 길게 만들고
	// 나머지는 그대로 나오게 한다.
}
