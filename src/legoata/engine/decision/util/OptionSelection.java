package legoata.engine.decision.util;

import java.io.PrintStream;

import legoata.engine.decision.Decision;
import legoata.engine.decision.node.branching.Option;
import legoata.engine.decision.node.branching.OptionSet;
import legoata.engine.gamecharacter.GameCharacter;

public interface OptionSelection {
	
	OptionSet select(Decision decision, Option selection, GameCharacter actor, PrintStream out);
	
}
