package legoata.engine.gamecharacter;

public class ActionManager {
	private GameCharacter gameChar;
	public ActionManager(GameCharacter gChar) {
		gameChar = gChar;
	}
	public boolean attemptMeleeStrike() {
		return false;
	}
	public boolean attemptCriticalMeleeStrike() {
		return false;
	}
	public boolean attemptRangedStrike() {
		return false;
	}
	public boolean attemptCriticalRangedStrike() {
		return false;
	}
	public boolean attemptDodge() {
		return false;
	}
	public boolean attemptDeflection() {
		return false;
	}
}
