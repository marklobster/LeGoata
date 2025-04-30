package legoata.engine.action.executor;

import legoata.engine.action.ActionResultCode;

public abstract class Executor {

	protected ExecutionResult actionComplete() {
		return new ExecutionResult(ActionResultCode.Complete);
	}

	protected ExecutionResult actionCancelled() {
		return new ExecutionResult(ActionResultCode.Incomplete);
	}
}
