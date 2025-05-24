package legoata.engine.action;

import legoata.engine.execute.GameControls;
import legoata.engine.model.LGObject;

public abstract class ModelAction<T> extends Action {

	public abstract ActionResult execute(LGObject actor, T input, GameControls controls);
	
}
