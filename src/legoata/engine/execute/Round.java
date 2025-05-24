package legoata.engine.execute;

class Round {

	private int index = 0;
	private RoundState state = RoundState.PRE_INIT;
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

	public RoundState getState() {
		return state;
	}

	public void setState(RoundState state) {
		this.state = state;
	}
	
	public boolean isRoundComplete() {
		return this.completeFlag;
	}

	public void completeRound() {
		this.completeFlag = true;
	}
}
