package org.legoata.execute;

public class ClockControls {
	private Clock clock;
	ClockControls(Clock clock) {
		this.clock = clock;
	}
	public long getMoment() {
		return this.clock.getMoment();
	}
	public void setMoment(long moment) {
		this.clock.setMoment(moment);
	}
	public long getNextIncrement() {
		return this.clock.getNextIncrement();
	}
	public void setNextIncrement(long nextIncrement) {
		this.clock.setNextIncrement(nextIncrement);
	}
}
