package org.legoata.action;

import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

/**
 * An Action with a data parameter done by a player.
 * @param <T>
 */
public abstract class ModelAction<T> extends Action {

	/**
	 * Execute the action.
	 * @param actor
	 * @param input
	 * @param controls
	 * @return
	 */
	public abstract ActionResult execute(LGObject actor, T input, ControlSet controls);
	
}
