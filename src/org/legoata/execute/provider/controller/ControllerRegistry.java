package org.legoata.execute.provider.controller;

import java.util.HashMap;
import java.util.Map;

import org.legoata.controller.Controller;
import org.legoata.execute.ControlUnit;

/**
 * Default implementation of a ControllerProvider.  Register a SingleControllerProvider for each Controller 
 * you want to instantiate at runtime.
 */
public class ControllerRegistry implements ControllerProvider {
	
	private Map<String, SingleControllerProvider> providerMap = new HashMap<String, SingleControllerProvider>();

	@Override
	public Controller getController(String name, ControlUnit controls) {
		return providerMap.get(name).constructController(controls);
	}
	
	/**
	 * Correlate a unique name to a single controller instance provider.
	 * @param name
	 * @param instanceMaker
	 */
	public void registerController(String name, SingleControllerProvider instanceMaker) {
		providerMap.put(name, instanceMaker);
	}

}
