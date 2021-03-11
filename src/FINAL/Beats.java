package FINAL;

//노트를 선언하기 쉽도록 어떤 노트가 어떤 시간에 떨어진다를 알려주는 클래스이다.
//생성자는 2가지 버전이 있는데 특수 노트에 대한 boolean값이 정의된 생성자와
//그냥 생성자 이렇게 2개를 만들었다.
//비트 배열을 악보처럼 만들어서 사용하므로
//어떤 시간에 어떤 노트가 떨어지는지 알아야하기 떄문에 getNoteName과 getTime을 선언하였다.
//노트의 특별함을 알아야할때를 위해서 getSpecial()또한 선언하였다.
public class Beats {
	private int time;
	private String Name;
	boolean special;

	public Beats(int t, String name) {
		this.time = t;
		this.Name = name;
	}
	public Beats(int t,String name,boolean special) {
		this.time = t;
		this.Name = name;
		this.special=special;
	}

	public String getNoteName() {
		return Name;
	}

	public int getTime() {
		return time;
	}
	public boolean getSpecial() {
		return special;
	}
	//요약///////////////////////////////////
	//이 클래스는 시간과 어떤 타입의 노트가 떨어질지를 저장한다./
	//어떤 노트에는 특별함에 대해서 선언한다.////////////
	////////////////////////////////////////
}
