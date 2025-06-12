package legoata.engine.event;

import legoata.engine.execute.Phase;

public class GameCycleEvent extends GameEvent {
	
	private Phase phase;

	public GameCycleEvent(Phase phase) {
		this.phase = phase;
	}

	public Phase getPhase() {
		return phase;
	}

}
