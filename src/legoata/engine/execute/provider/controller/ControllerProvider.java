package legoata.engine.execute.provider.controller;

import legoata.engine.controller.Controller;
import legoata.engine.execute.ControlSet;
import legoata.engine.model.LGObject;

public interface ControllerProvider {

	public Controller getController(String name, LGObject turnTaker, ControlSet controls);

}
