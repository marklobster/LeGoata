package legoata.engine.action;

import legoata.engine.execute.ControlSet;
import legoata.engine.model.LGObject;

/**
 * An Action without a data parameter done by a player.
 */
public abstract class ModelActionNullData extends Action {

	public abstract ActionResult execute(LGObject actor, ControlSet controls);
	
}
