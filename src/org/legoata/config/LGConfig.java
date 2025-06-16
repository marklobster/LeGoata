package org.legoata.config;

/**
 * Shows framework settings for the game.
 */
public interface LGConfig {
	
	/**
	 * If true, framework will increment the action count after each Consequential action, and end the turn 
	 * when the action limit is reached.
	 * @return
	 */
	public boolean isActionCountingEnabled();
	
	/**
	 * The action limit set by the framework when a turn begins. Only utilized if Action Counting is enabled.
	 */
	public int getDefaultActionLimit();

	/**
	 * For any player which implements HasCoolDown, the framework will check if they have cool-down when the turn 
	 * starts. If it is above zero, it will be decremented and their turn will be skipped. A player must have cool-down 
	 * of zero to have a turn.
	 */
	public boolean isCoolDownTrackingEnabled();

	/**
	 * If true, game clock will tick automatically at the start of each cycle.
	 */
	public boolean isAutoClockTickEnabled();
}
