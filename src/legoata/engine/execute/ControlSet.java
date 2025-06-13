package legoata.engine.execute;

/**
 * Contains controls for manipulating game state and data.
 */
public interface ControlSet {

	public GameControls getGameControls();
	public ClockControls getClockControls();
	public SchedulingControls getSchedulingControls();
	public RoundControls getRoundControls();
	public TurnControls getTurnControls();
	
}
