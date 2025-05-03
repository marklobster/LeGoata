package legoata.engine.action;

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
