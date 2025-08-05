package org.legoata.action;

/**
 * A wrapper for the ActionResultCode.  This class can be extended if you need to 
 * return additional data after execution of an Action.
 */
public class ActionResult {
	
	private ActionResultCode actionResultCode;

	/**
	 * Construct ActionResult with its required ActionResultCode.
	 * @param code - describes the type of ActionResult
	 */
	public ActionResult(ActionResultCode code) {
		this.actionResultCode = code;
	}

	/**
	 * Returns the ActionResultCode for the ActionResult.
	 * @return ActionResultCode
	 */
	public ActionResultCode getCode() {
		return this.actionResultCode;
	}
}
