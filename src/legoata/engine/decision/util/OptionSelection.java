package legoata.engine.decision.util;

import legoata.engine.decision.Decision;
import legoata.engine.decision.Option;
import legoata.engine.decision.OptionSet;

public interface OptionSelection {
	
	OptionSet select(Decision decision, Option selection);
	
}
