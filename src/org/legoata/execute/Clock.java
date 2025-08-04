package org.legoata.execute;

import java.util.function.LongConsumer;

public class Clock {

	private long moment;
	private long nextIncrement;
	private LongConsumer onMomentStrike;

	public long getMoment() {
		return moment;
	}

	public void setMoment(long moment) {
		this.moment = moment;
		this.nextIncrement = moment + 1;
		if (this.onMomentStrike != null) {
			this.onMomentStrike.accept(moment);
		}
	}
	
	public long getNextIncrement() {
		return nextIncrement;
	}

	public void setNextIncrement(long nextIncrement) {
		this.nextIncrement = nextIncrement;
	}

	public long increment() {
		this.moment = this.nextIncrement;
		this.nextIncrement = moment + 1;
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
