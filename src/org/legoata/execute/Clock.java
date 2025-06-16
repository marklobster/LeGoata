package org.legoata.execute;

import java.util.function.LongConsumer;

public class Clock {

	private long moment;
	private LongConsumer onMomentStrike;

	public long getMoment() {
		return moment;
	}

	public void setMoment(long moment) {
		this.moment = moment;
		if (this.onMomentStrike != null) {
			this.onMomentStrike.accept(moment);
		}
	}
	
	public long increment() {
		this.moment += 1;
		if (this.onMomentStrike != null) {
			this.onMomentStrike.accept(this.moment);
		}
		return this.moment;
	}

	public LongConsumer getOnMomentStrike() {
		return onMomentStrike;
	}

	public void setOnMomentStrike(LongConsumer onMomentStrike) {
		this.onMomentStrike = onMomentStrike;
	}
	
}
