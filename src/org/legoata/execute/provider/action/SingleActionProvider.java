package org.legoata.execute.provider.action;

import org.legoata.action.Action;
import org.legoata.execute.ControlUnit;

/**
 * Constructs a particular Action.
 */
public interface SingleActionProvider {
	/**
	 * Instantiate the action.
	 * @param controls
	 * @return
	 */
	public Action constructAction(ControlUnit controls);
}
