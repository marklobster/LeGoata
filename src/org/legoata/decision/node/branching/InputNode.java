package org.legoata.decision.node.branching;

import org.legoata.decision.Decision;
import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

public interface InputNode extends DecisionBuilderNode {
	
	public DecisionBuilderNode getInput(ControlSet controls, Decision decision, LGObject actor);
	
}
