package org.legoata.action;

/**
 * An action to be performed by a player during a turn.
 */
public abstract class Action {

	protected ActionResult actionCompletedWithConsequence() {
		return new ActionResult(ActionResultCode.Consequential);
	}
	
	protected ActionResult actionCompletedWithoutConsequence() {
		return new ActionResult(ActionResultCode.Inconsequential);
	}

	protected ActionResult actionCancelled() {
		return new ActionResult(ActionResultCode.Incomplete);
	}
	
	protected ActionResult exitGame() {
		return new ActionResult(ActionResultCode.ExitRequested);
	}
}
