package FINAL;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// 레벨 선택 화면
public class LevelReversi extends JPanel
{
	private JButton[] btnLevels;	// 레벨을 표시해주는 버튼 (5개 사용)
	private JButton btnBack;		// 뒤로가기 버튼
	private LevelListener lstnLevel;	// 버튼 클릭 리스너
	private HoverListener hbtnLevel;	// 마우스 호버 리스너
	private Level level;			// 선택한 레벨을 저장하기 위한 변수
	
	public static Music BACKGROUNDMUSIC;	// BGM
	private PanelReversi panelRev;	// PanelReversi 의 함수를 호출하기 위한 변수 (업콜)
	
	public LevelReversi(PanelReversi panel)
	{
		BACKGROUNDMUSIC = new Music("ReversiMain.mp3", true);
		BACKGROUNDMUSIC.start();
		
		setPreferredSize(new Dimension(1280,720));
		setBackground(new Color(57,131,66));
		setLayout(null);
		
		panelRev = panel;
		
		lstnLevel = new LevelListener();
		hbtnLevel = new HoverListener();
		
		// 5개의 레벨 선택 버튼 생성
		btnLevels = new JButton[5];
		btnLevels[0] = new JButton("EASY");
		btnLevels[1] = new JButton("NORMAL");
		btnLevels[2] = new JButton("HARD");
		btnLevels[3] = new JButton("EXPERT");
		btnLevels[4] = new JButton("MASTER");

		// 뒤로가기 버튼 설정 //
		// 이미지 사이즈 변환
		ImageIcon image = new ImageIcon(Main.class.getResource("../images/back.png"));
		Image pre = image.getImage();
		Image scale = pre.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		
		btnBack = new JButton(new ImageIcon(scale));
		// 버튼 경계 제거
		btnBack.setBorderPainted(false);
		// 버튼 내용제거
		btnBack.setContentAreaFilled(false);
		// 포커스시 경계 제거
		btnBack.setFocusPainted(false);
		
		// 레벨 버튼의 위치, 색상, 글꼴, 리스너 설정
		for (int i=0; i<5; i++)
		{
			btnLevels[i].setBackground(new Color(232,224,223));
			btnLevels[i].setFont(new Font("Consolas", Font.BOLD, 30));
			btnLevels[i].addActionListener(lstnLevel);
			btnLevels[i].addMouseListener(hbtnLevel);
			btnLevels[i].setBounds(460,60+i*120,360,100);
			add(btnLevels[i]);
		}
		
		btnBack.addActionListener(lstnLevel);
		btnBack.setBounds(1050,630,200,60);
		add(btnBack);
		
	} // LevelReversi Constructor
	
	// Getter
	public Level getLevel() { return level; }
	
	// 호버시 모양을 바꾸기 위한 호버 리스너
	public class HoverListener implements MouseListener
	{
		public void mouseClicked(MouseEvent evt)	{}
		public void mousePressed(MouseEvent evt)	{}
		public void mouseReleased(MouseEvent evt)	{}
		
		// 호버시 버튼 색상과 마우스 모양 변경
		public void mouseEntered(MouseEvent evt)
		{
			int i;
			Object obj = evt.getSource();
			
			for(i=0; i<5 && obj!=btnLevels[i]; i++);
			
			btnLevels[i].setBackground(new Color(221, 134, 117));
			btnLevels[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
		} // mouseEntered()
		
		public void mouseExited(MouseEvent evt)
		{
			int i;
			Object obj = evt.getSource();
			
			for(i=0; i<5 && obj!=btnLevels[i]; i++);
			
			btnLevels[i].setBackground(new Color(232,224,223));
			btnLevels[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} // mouseExited()
	} // HoverListener Inner Class
	
	// 레벨, 뒤로가기 버튼들의 클릭 리스너
	public class LevelListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent evt)
		{
			int idx;
			Object obj = evt.getSource();
			
			// 이벤트가 발생한 버튼을 검색
			for (idx=0; idx<5 && obj!=btnLevels[idx]; idx++);
			
			// 선택한 버튼에 따라 level 설정
			switch (idx)
			{
			case 0 : level = Level.EASY; break;
			case 1 : level = Level.NORMAL; break;
			case 2 : level = Level.HARD; break;
			case 3 : level = Level.EXPERT; break;
			case 4 : level = Level.MASTER; break;
			}
			
			if (idx < 5) // 레벨 버튼들 중 하나를 누른 경우
			{
				(new Music("levelselect.mp3",false)).start();
				panelRev.runReversi();
			}
			else // 뒤로가기 버튼을 눌렀을 경우
			{
				BACKGROUNDMUSIC.close();
				(new Music("back.mp3",false)).start();
				panelRev.exit();
			}
		} // actionPerformed()
	}  // LevelListener Inner Class
} // LevelReversi Class