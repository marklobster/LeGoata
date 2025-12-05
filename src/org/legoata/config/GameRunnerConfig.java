package org.legoata.config;

/**
 * Configurations for the GameRunner. Use this to configure the GameRunner before starting the game.
 */
public class GameRunnerConfig implements LGConfig {

	private boolean actionCountingEnabled = false;
	private int defaultActionlimit = 0;
	private boolean autoClockTickEnabled = true;
	private boolean coolDownTrackingEnabled = false;
	
	/**
	 * If true, framework will increment the action count after each Consequential action, and end the turn 
	 * when the action limit is reached.
	 * @return
	 */
	public boolean isActionCountingEnabled() {
		return actionCountingEnabled;
	}

	/**
	 * Set true to enable automatic action counting, false to disable.
	 * @param actionCountingEnabled
	 */
	public void setActionCountingEnabled(boolean actionCountingEnabled) {
		this.actionCountingEnabled = actionCountingEnabled;
	}

	/**
	 * The framework will set the action limit to this count during the TURN_INIT phase. Only occurs if 
	 * Action Counting is enabled.
	 */
	public int getDefaultActionLimit() {
		return defaultActionlimit;
	}

	/**
	 * Set the default action limit per turn.
	 * @param defaultActionlimit
	 */
	public void setDefaultActionLimit(int defaultActionlimit) {
		this.defaultActionlimit = defaultActionlimit;
	}

	/**
	 * For any player which implements HasCoolDown, the framework will check if they have cool-down when the turn 
	 * starts. If it is above zero, it will be decremented and their turn will be skipped. A player must have cool-down 
	 * of zero to have a turn.
	 */
	public boolean isCoolDownTrackingEnabled() {
		return coolDownTrackingEnabled;
	}

	/**
	 * Set true to enable cool-down tracking, false to disable.
	 * @param coolDownTrackingEnabled
	 */
	public void setCoolDownTrackingEnabled(boolean coolDownTrackingEnabled) {
		this.coolDownTrackingEnabled = coolDownTrackingEnabled;
	}

	/**
	 * If true, game clock will tick automatically at the start of each cycle.
	 */
	public boolean isAutoClockTickEnabled() {
		return autoClockTickEnabled;
	}

	/**
	 * Set true to enable automatic clock ticking, false to disable.
	 * @param autoClockTickEnabled
	 */
	public void setAutoClockTickEnabled(boolean autoClockTickEnabled) {
		this.autoClockTickEnabled = autoClockTickEnabled;
	}

}
