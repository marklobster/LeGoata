package org.legoata.decision.util;

import java.io.PrintStream;

import org.legoata.decision.Decision;
import org.legoata.decision.node.branching.Option;
import org.legoata.decision.node.branching.OptionSet;
import org.legoata.model.LGObject;

public interface OptionSelection {
	
	OptionSet select(Decision decision, Option selection, LGObject actor, PrintStream out);
	
}
