package org.legoata.execute.provider.controller;

import org.legoata.controller.Controller;
import org.legoata.execute.ControlUnit;

/**
 * Constructs a particular Controller.
 */
public interface SingleControllerProvider {
	public Controller constructController(ControlUnit controls);
}
