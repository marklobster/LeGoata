package org.legoata.execute;

class MutableControlSet implements ControlUnit {
	
	private GameControls gameControls;
	private ClockControls clockControls;
	private SchedulingControls schedulingControls;
	private RoundControls roundControls;
	private TurnControls turnControls;
	
	public GameControls getGameControls() {
		return gameControls;
	}
	void setGameControls(GameControls gameControls) {
		this.gameControls = gameControls;
	}
	public ClockControls getClockControls() {
		return this.clockControls;
	}
	void setClockControls(ClockControls clockControls) {
		this.clockControls = clockControls;
	}
	public SchedulingControls getSchedulingControls() {
		return this.schedulingControls;
	}
	void setSchedulingControls(SchedulingControls schedulingControls) {
		this.schedulingControls = schedulingControls;
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
