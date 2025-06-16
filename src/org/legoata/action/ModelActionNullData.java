package org.legoata.action;

import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

/**
 * An Action without a data parameter done by a player.
 */
public abstract class ModelActionNullData extends Action {

	public abstract ActionResult execute(LGObject actor, ControlSet controls);
	
}
