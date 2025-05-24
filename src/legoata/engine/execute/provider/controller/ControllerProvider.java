package legoata.engine.execute.provider.controller;

import legoata.engine.controller.Controller;
import legoata.engine.execute.GameControls;
import legoata.engine.execute.RoundControls;
import legoata.engine.execute.TurnControls;
import legoata.engine.model.LGObject;

public interface ControllerProvider {

	public Controller getController(String name, LGObject turnTaker, GameControls gameControls);

}
