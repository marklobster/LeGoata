package org.legoata.execute.provider.controller;

import org.legoata.controller.Controller;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

/**
 * Resolves a String to the instantiation of an Controller, also injecting Controller dependencies.
 */
public interface ControllerProvider {

	public Controller getController(String name, LGObject turnTaker, ControlSet controls);

}
