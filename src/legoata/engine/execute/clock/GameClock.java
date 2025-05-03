package legoata.engine.execute.clock;

public class GameClock {
	
	private int moment;

	public GameClock() {
		// TODO Auto-generated constructor stub
	}
	
	public int time() {
		return this.moment;
	}
	
	public void increment() {
		this.moment++;
	}

}
