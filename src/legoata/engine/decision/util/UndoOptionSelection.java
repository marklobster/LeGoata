package legoata.engine.decision.util;

import legoata.engine.decision.Decision;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.model.LGObject;

public interface UndoOptionSelection {
	
	void undoSelection(Decision decision, LGObject character);
	
}
