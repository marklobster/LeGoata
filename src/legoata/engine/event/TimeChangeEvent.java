package legoata.engine.event;

/**
 * An event fired when the clock updates.
 */
public class TimeChangeEvent extends GameEvent {
	
	private long time;

	public TimeChangeEvent(long time) {
		this.time = time;
	}

	/**
	 * The clock moment in which the event fired.
	 * @return
	 */
	public long getTime() {
		return time;
	}

}
