package org.legoata.samples.gofish.controller;

import org.legoata.controller.Controller;
import org.legoata.controller.command.ChangeController;
import org.legoata.controller.command.TurnCommand;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;
import org.legoata.samples.gofish.model.User;

public class DefaultController extends Controller {

	public DefaultController(ControlSet controls) {
		super(controls);
	}
	
	@Override
	public TurnCommand preActionCommand() {
		LGObject player = this.getTurnControls().getTurnTaker();
		if (player instanceof User) {
			return new ChangeController(UserController.LABEL);
		} else {
			return new ChangeController(BotController.LABEL);
		}
	}

}
