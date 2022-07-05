package legoata.engine.decision.node.nonbranching;

import legoata.engine.decision.node.DecisionBuilderNode;

/**
 * Indicates that the user needs to go back 1 or more menus.  Use this when it is necessary
 * to force the user back n-number of menus after making a selection.  The default value of 
 * n is 1.
 * @author Mark
 *
 */
public final class GoBack implements DecisionBuilderNode {

	private int frames = 1;
	
	public GoBack() {
		
	}
	
	public GoBack(int steps) {
		this.setNumberOfStepsBack(steps);
	}

	public int getNumberOfStepsBack() {
		return frames;
	}

	public void setNumberOfStepsBack(int steps) {
		this.frames = steps;
	}

}
