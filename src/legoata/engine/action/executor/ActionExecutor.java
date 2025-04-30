package legoata.engine.action.executor;

import legoata.engine.model.LGObject;

public abstract class ActionExecutor<T> extends Executor {

	public abstract ExecutionResult executeAction(LGObject actor, T input);
	
}
