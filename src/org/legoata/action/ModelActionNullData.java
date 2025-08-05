package org.legoata.action;

import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

/**
 * An Action done by a player without a data parameter.
 */
public abstract class ModelActionNullData extends Action {

	/**
	 * Execute the action.
	 * @param actor
	 * @param controls
	 * @return
	 */
	public abstract ActionResult execute(LGObject actor, ControlSet controls);
	
}
