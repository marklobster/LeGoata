package org.legoata.decision.node.nonbranching;

import org.legoata.decision.node.DecisionBuilderNode;

/**
 * Indicates that the user needs to go back 1 or more menus.  Use this when it is necessary
 * to force the user back n-number of menus after making a selection.  The default value of 
 * n is 0.
 * @author Mark
 *
 */
public final class GoBack implements DecisionBuilderNode {

	private int steps = 0;
	
	public GoBack() {
		this(1);
	}
	
	public GoBack(int steps) {
		this.setNumberOfStepsBack(steps);
	}

	public int getNumberOfStepsBack() {
		return steps;
	}

	public void setNumberOfStepsBack(int steps) {
		this.steps = steps;
	}

}
