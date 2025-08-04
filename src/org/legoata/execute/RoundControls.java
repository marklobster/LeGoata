package org.legoata.execute;

public class RoundControls {

	private Round round = null;
	
	RoundControls(Round round) {
		this.round = round;
	}

	public int getIndex() {
		return this.round.getIndex();
	}

	public void setIndex(int index) {
		this.round.setIndex(index);
	}
	
	public void setIndex(int index, boolean updateNextIncrement) {
		this.round.setIndex(index, updateNextIncrement);
	}
	
	public int getNextIncrement() {
		return this.round.getNextIncrement();
	}
	
	public void setNextIncrement(int nextIncrement) {
		this.round.setNextIncrement(nextIncrement);
	}
	
	public void incrementIndex() {
		this.round.incrementIndex();
	}
	
	public boolean isComplete() {
		return this.round.isComplete();
	}
	
	public void complete() {
		this.round.complete();
	}

}
