package org.legoata.decision.node.branching;

import org.legoata.decision.node.DecisionBuilderNode;

public interface InputNode<T> extends DecisionBuilderNode {
	
	public DecisionBuilderNode getInput(T decisionData);
	
	public void undo(T decisionData);
	
}
