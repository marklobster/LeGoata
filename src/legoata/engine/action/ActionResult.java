package legoata.engine.action;

public class ActionResult {
	
	private ActionResultCode actionResultCode;

	public ActionResult(ActionResultCode code) {
		this.actionResultCode = code;
	}

	public ActionResultCode getCode() {
		return this.actionResultCode;
	}
}
