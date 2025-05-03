package legoata.engine.execute.provider.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import legoata.engine.controller.Controller;

public class ControllerRegistry implements ControllerProvider {
	
	private Map<String, SingleControllerProvider> providerMap = new HashMap<String, SingleControllerProvider>();

	@Override
	public Controller getController(String name, Scanner scanner) {
		return providerMap.get(name).constructController(scanner);
	}
	
	public void registerController(String name, SingleControllerProvider instanceMaker) {
		providerMap.put(name, instanceMaker);
	}

}
