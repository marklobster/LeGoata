package org.legoata.decision.util;

import java.util.ArrayList;

import org.legoata.decision.Decision;
import org.legoata.decision.node.branching.Option;
import org.legoata.model.LGObject;

public interface OptionProvider {

	ArrayList<Option> getOptions(Decision decision, LGObject actor);
	
}
