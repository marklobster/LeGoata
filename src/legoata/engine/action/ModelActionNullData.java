package legoata.engine.action;

import legoata.engine.execute.GameControls;
import legoata.engine.model.LGObject;

public abstract class ModelActionNullData extends Action {

	public abstract ActionResult execute(LGObject actor, GameControls controls);
	
}
