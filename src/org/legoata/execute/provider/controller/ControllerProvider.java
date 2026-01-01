package org.legoata.execute.provider.controller;

import org.legoata.controller.Controller;
import org.legoata.execute.ControlUnit;

/**
 * Resolves a String to the instantiation of an Controller, also injecting Controller dependencies.
 */
public interface ControllerProvider {

	public Controller getController(String name, ControlUnit controls);

}
