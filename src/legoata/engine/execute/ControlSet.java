package legoata.engine.execute;

public interface ControlSet {

	public GameControls getGameControls();
	public ClockControls getClockControls();
	public SchedulingControls getSchedulingControls();
	public RoundControls getRoundControls();
	public TurnControls getTurnControls();
	
}
