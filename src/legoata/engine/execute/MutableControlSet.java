package legoata.engine.execute;

class MutableControlSet implements ControlSet {
	
	private GameControls gameControls;
	private RoundControls roundControls;
	private TurnControls turnControls;
	
	public GameControls getGameControls() {
		return gameControls;
	}
	void setGameControls(GameControls gameControls) {
		this.gameControls = gameControls;
	}
	public RoundControls getRoundControls() {
		return roundControls;
	}
	void setRoundControls(RoundControls roundControls) {
		this.roundControls = roundControls;
	}
	public TurnControls getTurnControls() {
		return turnControls;
	}
	void setTurnControls(TurnControls turnControls) {
		this.turnControls = turnControls;
	}
}
