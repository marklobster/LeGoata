package legoata.engine.decision.util;

import legoata.engine.decision.Decision;
import legoata.engine.gamecharacter.GameCharacter;

public interface UndoOptionSelection {
	
	void undoSelection(Decision decision, GameCharacter character);
	
}
