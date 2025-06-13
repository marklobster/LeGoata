package legoata.engine.execute.provider.action;

import legoata.engine.action.Action;

/**
 * Constructs a particular Action.
 */
public interface SingleActionProvider {
	public Action constructAction();
}
