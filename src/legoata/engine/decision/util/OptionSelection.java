package legoata.engine.decision.util;

import legoata.engine.decision.Decision;
import legoata.engine.decision.Option;
import legoata.engine.decision.OptionSet;
import legoata.engine.gamecharacter.GameCharacter;

public interface OptionSelection {
	
	OptionSet select(Decision decision, Option selection, GameCharacter actor);
	
}
