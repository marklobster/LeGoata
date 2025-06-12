package legoata.engine.execute;

public class ClockControls {
	private Clock clock;
	ClockControls(Clock clock) {
		this.clock = clock;
	}
	public long getMoment() {
		return this.clock.getMoment();
	}
}
