package legoata.engine.action.executor;

import legoata.engine.action.ActionResultCode;

public class ExecutionResult {
	
	private ActionResultCode actionResultCode;

	public ExecutionResult(ActionResultCode code) {
		this.actionResultCode = code;
	}

	public ActionResultCode getCode() {
		return this.actionResultCode;
	}
}
