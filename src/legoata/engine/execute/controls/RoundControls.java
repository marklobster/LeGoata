package legoata.engine.execute.controls;

public class RoundControls {

	private boolean isComplete = false;
	private int index = 0;
	
	public boolean isRoundComplete() {
		return this.isComplete;
	}
	
	public void finishRound() {
		this.isComplete = true;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void incrementIndex() {
		this.index++;
	}

}
