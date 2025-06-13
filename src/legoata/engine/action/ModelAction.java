package legoata.engine.action;

import legoata.engine.execute.ControlSet;
import legoata.engine.model.LGObject;

/**
 * An Action with a data parameter done by a player.
 * @param <T>
 */
public abstract class ModelAction<T> extends Action {

	public abstract ActionResult execute(LGObject actor, T input, ControlSet controls);
	
}
