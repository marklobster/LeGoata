package legoata.engine.action;

/**
 * A wrapper for the ActionResultCode.  This class can be extended if needed, if you need to 
 * return extra data after execution of an Action.
 */
public class ActionResult {
	
	private ActionResultCode actionResultCode;

	public ActionResult(ActionResultCode code) {
		this.actionResultCode = code;
	}

	public ActionResultCode getCode() {
		return this.actionResultCode;
	}
}
