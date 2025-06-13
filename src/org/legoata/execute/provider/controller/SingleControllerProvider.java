package org.legoata.execute.provider.controller;

import org.legoata.controller.Controller;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

/**
 * Constructs a particular Controller.
 */
public interface SingleControllerProvider {
	public Controller constructController(LGObject turnTaker, ControlSet controls);
}
