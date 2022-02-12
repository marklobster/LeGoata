package Engine.Incident;

import Engine.GameCharacter.GameCharacter;

public class TurnTracker {
	
	private final int INITIAL_DELAY = 25;
	
	private int delayState;
	
	private GameCharacter gameChar;
	
	public TurnTracker(GameCharacter gameChar) {
		this.gameChar = gameChar;
	}
	
	public int getDelayState() {
		return delayState;
	}
	
	public int payActionCost(int cost) {
		delayState += cost;
		return delayState;
	}
	
	public int recoverFromAction(int time) {
		delayState -= time;
		if (delayState < 0) {
			delayState = 0;
		}
		return delayState;
	}
	
	public void initialize() {
		delayState = INITIAL_DELAY - gameChar.getStats().getInitiative();
		if (delayState < 0) {
			delayState = 0;
		}
	}
}
