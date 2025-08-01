package org.legoata.samples.gofish.controller;

import org.legoata.controller.Controller;
import org.legoata.controller.command.ChangeController;
import org.legoata.controller.command.TurnCommand;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

public class DefaultController extends Controller {

	public DefaultController(LGObject turnTaker, ControlSet controls) {
		super(turnTaker, controls);
	}
	
	@Override
	public TurnCommand preActionCommand() {
		return new ChangeController(UserController.LABEL);
	}

}
