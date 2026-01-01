package org.legoata.action.decision;

import org.legoata.decision.DecisionBuilder;
import org.legoata.execute.ControlUnit;

/**
 * Convenience class for building an ActionDecision.
 */
public class ActionBuilder extends DecisionBuilder<ActionDecision> {

	public ActionBuilder(ControlUnit controls) {
		super(controls);
	}

}
