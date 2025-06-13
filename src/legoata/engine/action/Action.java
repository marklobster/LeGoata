package legoata.engine.action;

/**
 * An action to be performed by a player during a turn.
 */
public abstract class Action {

	protected ActionResult actionCompleteWithConsequence() {
		return new ActionResult(ActionResultCode.Consequential);
	}
	
	protected ActionResult actionCompleteWithoutConsequence() {
		return new ActionResult(ActionResultCode.Inconsequential);
	}

	protected ActionResult actionCancelled() {
		return new ActionResult(ActionResultCode.Incomplete);
	}
	
	protected ActionResult exitGame() {
		return new ActionResult(ActionResultCode.ExitRequested);
	}
}
