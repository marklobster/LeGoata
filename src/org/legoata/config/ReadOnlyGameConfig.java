package org.legoata.config;

/**
 * Shows the framework configuration options.
 */
public class ReadOnlyGameConfig implements LGConfig {
	
	private boolean actionCountingEnabled = false;
	private int defaultActionLimit = 0;
	private boolean coolDownTrackingEnabled = false;
	private boolean autoClockTickEnabled = true;

	public ReadOnlyGameConfig(GameConfig config) {
		this.actionCountingEnabled = config.isActionCountingEnabled();
		this.defaultActionLimit = config.getDefaultActionLimit();
		this.coolDownTrackingEnabled = config.isCoolDownTrackingEnabled();
		this.autoClockTickEnabled = config.isAutoClockTickEnabled();
	}

	public boolean isActionCountingEnabled() {
		return actionCountingEnabled;
	}
	
	public int getDefaultActionLimit() {
		return this.defaultActionLimit;
	}

	public boolean isCoolDownTrackingEnabled() {
		return coolDownTrackingEnabled;
	}

	public boolean isAutoClockTickEnabled() {
		return autoClockTickEnabled;
	}
}
