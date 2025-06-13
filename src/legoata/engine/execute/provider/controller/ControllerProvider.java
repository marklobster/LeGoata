package legoata.engine.execute.provider.controller;

import legoata.engine.controller.Controller;
import legoata.engine.execute.ControlSet;
import legoata.engine.model.LGObject;

/**
 * Resolves a String to the instantiation of an Controller, also injecting Controller dependencies.
 */
public interface ControllerProvider {

	public Controller getController(String name, LGObject turnTaker, ControlSet controls);

}
