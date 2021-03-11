package FINAL;

public class Track {

	private String titleImage;
	private String startImage;
	private String gameImage;
	private String startMusic;
	private String gameMusic;
	private String titleName;
	//
	
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	public String getStartImage() {
		return startImage;
	}
	public void setStartImage(String startImage) {
		this.startImage = startImage;
	}
	public String getGameImage() {
		return gameImage;
	}
	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}
	public String getStartMusic() {
		return startMusic;
	}
	public void setStartMusic(String startMusic) {
		this.startMusic = startMusic;
	}
	public String getGameMusic() {
		return gameMusic;
	}
	public void setGameMusic(String gameMusic) {
		this.gameMusic = gameMusic;
	}
	
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public Track(String titleImage, String startImage, String gameImage, String startMusic, String gameMusic,String titleName) {
		super();
		this.titleImage = titleImage;
		this.startImage = startImage;
		this.gameImage = gameImage;
		this.startMusic = startMusic;
		this.gameMusic = gameMusic;
		this.titleName=titleName;
	}
	
	//곡 정보를 담는 클래스이다.
	//생성자만 생성하였고 나머지는 소스의 generate getters and setters를 사용하여 만들었다.
	//순서대로 곡 정보, 곡 이미지, 게임백그라운드, 하이라이트부분 노래, 풀 게임노래, 곡 이름을 담을수 있다.
}
