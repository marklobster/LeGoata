package org.legoata.samples.gofish.controller;

import org.legoata.controller.Controller;
import org.legoata.controller.command.ChangeController;
import org.legoata.controller.command.TurnCommand;
import org.legoata.execute.ControlUnit;
import org.legoata.samples.gofish.model.User;
import org.legoata.tracking.LGTrackable;

public class DefaultController extends Controller {

	public DefaultController(ControlUnit controls) {
		super(controls);
	}
	
	@Override
	public TurnCommand preActionCommand() {
		LGTrackable player = this.getTurnControls().getTurnTaker();
		if (player instanceof User) {
			return new ChangeController(UserController.LABEL);
		} else {
			return new ChangeController(BotController.LABEL);
		}
	}

}
