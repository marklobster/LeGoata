package org.legoata.execute;

class Round {

	private int index = 0;
	private boolean completeFlag = false;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void incrementIndex() {
		this.index += 1;
	}

	public boolean isRoundComplete() {
		return this.completeFlag;
	}

	public void completeRound() {
		this.completeFlag = true;
	}
}
