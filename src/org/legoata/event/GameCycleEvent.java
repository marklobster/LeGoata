package org.legoata.event;

import org.legoata.execute.Phase;

/**
 * An event that fires when the game cycles to certain phases.
 */
public class GameCycleEvent extends GameEvent {
	
	private Phase phase;

	public GameCycleEvent(Phase phase) {
		this.phase = phase;
	}

	/**
	 * The phase change which caused the event to fire.
	 * @return
	 */
	public Phase getPhase() {
		return phase;
	}

}
