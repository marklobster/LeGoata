package legoata.engine.execute;

public class RoundControls {

	private boolean isComplete = false;
	private Game grc = null;
	
	RoundControls(Game grc) {
		this.grc = grc;
	}
	
	public boolean isRoundFinished() {
		return this.isComplete;
	}
	
	public void finishRound() {
		this.isComplete = true;
	}

	public int getIndex() {
		return this.grc.getRound().getIndex();
	}

	public void setIndex(int index) {
		this.grc.getRound().setIndex(index);
	}
	
	public void incrementIndex() {
		this.grc.getRound().incrementIndex();
	}
	
	public RoundState getRoundState() {
		return this.grc.getRound().getState();
	}

}
