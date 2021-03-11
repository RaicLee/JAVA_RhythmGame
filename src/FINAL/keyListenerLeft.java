package FINAL;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//이 클래스는 외부 클래스로 정의되었고 사용자가 키를 입력함에 따라 game안에 있는
//키값에 따른 행동실행을 trigger할수 있는 클래스이다. game을 public static으로 선언했기에 외부에서도
//접근이 가능하므로 사용하기 편하게 외부 클래스로 나타내였다.
//game이 null일때는 아무런 행동도 시작하지 않는다.
//game이 null이 아닐 경우에는 각 각 해당하는 키값에 따른 이벤트에 대한 
//처리를 시작할수 있도록 하는 행동들을 trigger시킨다.
public class keyListenerLeft extends KeyAdapter {
	@Override
	public void keyPressed(KeyEvent e) {
		if (RhythmGameMusicSelection.game == null) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) { // S키
			RhythmGameMusicSelection.game.PS();
		} else if (e.getKeyCode() == KeyEvent.VK_D) { // D키
			RhythmGameMusicSelection.game.PD();
		} else if (e.getKeyCode() == KeyEvent.VK_F) { // F키
			RhythmGameMusicSelection.game.PF();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) { // SpaceBar키
			RhythmGameMusicSelection.game.PSP();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (RhythmGameMusicSelection.game == null) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {// S키
			RhythmGameMusicSelection.game.RS();
		} else if (e.getKeyCode() == KeyEvent.VK_D) {// D키
			RhythmGameMusicSelection.game.RD();
		} else if (e.getKeyCode() == KeyEvent.VK_F) {// F키
			RhythmGameMusicSelection.game.RF();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {// SpaceBar키
			RhythmGameMusicSelection.game.RSP();
		}

	}
	// 이클래스는 사용자에게 키를 입력받고 난 후에 누르는 액션과 놓는 액션을 실행시킬수 있도록 한다
}
