package org.legoata.action;

/**
 * An action to be performed by a player during a turn.
 */
public abstract class Action {

	/**
	 * Returns an ActionResult with Code Consequential.
	 * @return ActionResult
	 */
	protected ActionResult actionCompletedWithConsequence() {
		return new ActionResult(ActionResultCode.Consequential);
	}
	
	/**
	 * Returns an ActionResult with Code Inconsequential.
	 * @return ActionResult
	 */
	protected ActionResult actionCompletedWithoutConsequence() {
		return new ActionResult(ActionResultCode.Inconsequential);
	}

	/**
	 * Returns an ActionResult with Code Incomplete.
	 * @return ActionResult
	 */
	protected ActionResult actionCancelled() {
		return new ActionResult(ActionResultCode.Incomplete);
	}
	
	/**
	 * Returns an ActionResult with Code Error.
	 * @return ActionResult
	 */
	protected ActionResult actionError() {
		return new ActionResult(ActionResultCode.Error);
	}
	
	/**
	 * Returns an ActionResult with Code ExitRequested.
	 * @return ActionResult
	 */
	protected ActionResult exitGame() {
		return new ActionResult(ActionResultCode.ExitRequested);
	}
}
