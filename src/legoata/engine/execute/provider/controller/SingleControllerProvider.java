package legoata.engine.execute.provider.controller;

import legoata.engine.controller.Controller;
import legoata.engine.execute.ControlSet;
import legoata.engine.model.LGObject;

/**
 * Constructs a particular Controller.
 */
public interface SingleControllerProvider {
	public Controller constructController(LGObject turnTaker, ControlSet controls);
}
