package legoata.engine.execute;

public interface ControlSet {

	public GameControls getGameControls();
	public RoundControls getRoundControls();
	public TurnControls getTurnControls();
	public ClockControls getClockControls();
	
}
