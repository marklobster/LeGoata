package legoata.engine.execute.provider.controller;

import legoata.engine.controller.Controller;
import legoata.engine.execute.GameControls;
import legoata.engine.execute.RoundControls;
import legoata.engine.execute.TurnControls;
import legoata.engine.model.LGObject;

public interface SingleControllerProvider {
	public Controller constructController(LGObject turnTaker, GameControls gameControls);
}
