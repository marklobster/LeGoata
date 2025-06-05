package legoata.engine.action;

import legoata.engine.execute.ControlSet;
import legoata.engine.model.LGObject;

public abstract class ModelActionNullData extends Action {

	public abstract ActionResult execute(LGObject actor, ControlSet controls);
	
}
