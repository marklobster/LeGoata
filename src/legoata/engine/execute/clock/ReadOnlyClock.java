package legoata.engine.execute.clock;

public class ReadOnlyClock {
	
	private GameClock gameClock;

	public ReadOnlyClock(GameClock gameClock) {
		this.gameClock = gameClock;
	}
	
	public int time() {
		return this.gameClock.time();
	}

}
