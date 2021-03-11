package FINAL;
import javax.swing.*;

// 오델로 실행에 필요한 LevelPanel 과 PlayPanel 을 담고 있는 클래스
public class PanelReversi extends JPanel
{	
	private JPanel FirstScreen;	// 뒤로가기시 게임 선택 화면 표시를 위한 변수 (업콜)
	private LevelReversi LevelPanel;	// 레벨 선택 패널
	private PlayReversi PlayPanel;		// 오델로 플레이 패널
	
	public PanelReversi(JPanel primary, JPanel THEFIRSTPANEL)
	{
		FirstScreen = THEFIRSTPANEL;
		LevelPanel = new LevelReversi(this);
		PlayPanel = new PlayReversi(this);
		
		PlayPanel.setVisible(false);	// 초기에 레벨 선택 화면만 보이게 하기 위함
		LevelPanel.setBounds(0,0,1280,720);
		PlayPanel.setBounds(0,0,1280,720);
		
		setBounds(0, 0, 1280, 720);
		primary.add(this);
		
		add(LevelPanel);
		add(PlayPanel);
	} // PanelReversi()

	// 레벨 선택 화면 표시
	public void selectLevel()
	{
		LevelPanel.setVisible(true);
		PlayPanel.setVisible(false);
	} // selectLevel()
	
	// LevelPanel 에서 레벨이 선택되면, 버튼 리스너에서 호출하는 함수이다
	public void runReversi()
	{
		LevelPanel.setVisible(false);
		PlayPanel.init(LevelPanel.getLevel()); // 선택한 레벨로 초기화
		PlayPanel.setVisible(true);	
	} // runReversi()
	
	// 게임 종료
	public void exit()
	{
		LevelPanel.setVisible(false);
		PlayPanel.setVisible(false);
		setVisible(false);
		FirstScreen.setVisible(true);
	} // exit()
	
	// 게임을 합칠때 편의를 위해 오버라이딩
	@Override
	public void setVisible(boolean aFlag)
	{
		super.setVisible(aFlag);
	
		// aFlag 에 true 가 인자로 들어오면 레벨 선택 화면을 보여준다
		if (aFlag)
			selectLevel();
	}
}