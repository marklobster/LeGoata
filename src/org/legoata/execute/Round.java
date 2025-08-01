package org.legoata.execute;

class Round {

	private int index = 0;
	private int nextIncrement = 1;
	private boolean completeFlag = false;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		setIndex(index, true);
	}
	
	public void setIndex(int index, boolean updateNextIncrement) {
		this.index = index;
		if (updateNextIncrement) {
			this.nextIncrement = index + 1;
		}
	}
	
	public int getNextIncrement() {
		return nextIncrement;
	}

	public void setNextIncrement(int nextIncrement) {
		this.nextIncrement = nextIncrement;
	}

	public void incrementIndex() {
		setIndex(this.nextIncrement);
	}

	public boolean isComplete() {
		return this.completeFlag;
	}

	public void complete() {
		this.completeFlag = true;
	}

}
