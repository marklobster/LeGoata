package legoata.engine.event;

public class TimeChangeEvent extends GameEvent {
	
	private long time;

	public TimeChangeEvent(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

}
