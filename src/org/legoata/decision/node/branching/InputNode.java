package org.legoata.decision.node.branching;

import org.legoata.decision.node.DecisionBuilderNode;

/**
 * A node within a Decision Builder which requires user input.
 * @param <T>
 */
public interface InputNode<T> extends DecisionBuilderNode {
	
	/**
	 * Acquire and process user input.
	 * @param decisionData The object being built by the Decision Builder.
	 * @return the next node
	 */
	public DecisionBuilderNode getInput(T decisionData);
	
	/**
	 * Undoes the work done by the getInput function.
	 * @param decisionData The object being built by the Decision Builder.
	 */
	public void undo(T decisionData);
	
}
