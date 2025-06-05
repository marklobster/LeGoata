package legoata.engine.execute.provider.controller;

import java.util.HashMap;
import java.util.Map;

import legoata.engine.controller.Controller;
import legoata.engine.execute.ControlSet;
import legoata.engine.model.LGObject;

public class ControllerRegistry implements ControllerProvider {
	
	private Map<String, SingleControllerProvider> providerMap = new HashMap<String, SingleControllerProvider>();

	@Override
	public Controller getController(String name, LGObject turnTaker, ControlSet controls) {
		return providerMap.get(name).constructController(turnTaker, controls);
	}
	
	public void registerController(String name, SingleControllerProvider instanceMaker) {
		providerMap.put(name, instanceMaker);
	}

}
