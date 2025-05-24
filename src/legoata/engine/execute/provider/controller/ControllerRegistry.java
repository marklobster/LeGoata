package legoata.engine.execute.provider.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import legoata.engine.controller.Controller;
import legoata.engine.execute.GameControls;
import legoata.engine.execute.RoundControls;
import legoata.engine.execute.TurnControls;
import legoata.engine.model.LGObject;

public class ControllerRegistry implements ControllerProvider {
	
	private Map<String, SingleControllerProvider> providerMap = new HashMap<String, SingleControllerProvider>();

	@Override
	public Controller getController(String name, LGObject turnTaker, GameControls gameControls) {
		return providerMap.get(name).constructController(turnTaker, gameControls);
	}
	
	public void registerController(String name, SingleControllerProvider instanceMaker) {
		providerMap.put(name, instanceMaker);
	}

}
