package legoata.engine.decision.util;

import java.io.PrintStream;

import legoata.engine.decision.Decision;
import legoata.engine.decision.node.branching.Option;
import legoata.engine.decision.node.branching.OptionSet;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.model.LGObject;

public interface OptionSelection {
	
	OptionSet select(Decision decision, Option selection, LGObject actor, PrintStream out);
	
}
