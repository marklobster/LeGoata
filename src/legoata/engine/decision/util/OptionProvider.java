package legoata.engine.decision.util;

import java.util.ArrayList;

import legoata.engine.decision.Decision;
import legoata.engine.decision.node.branching.Option;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.model.LGObject;

public interface OptionProvider {

	ArrayList<Option> getOptions(Decision decision, LGObject actor);
	
}
