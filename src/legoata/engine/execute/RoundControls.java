package legoata.engine.execute;

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
	
	public void incrementIndex() {
		this.round.incrementIndex();
	}

}
