package org.legoata.samples.gofish.controller;

import org.legoata.controller.Controller;
import org.legoata.decision.Decision;
import org.legoata.execute.ControlSet;
import org.legoata.samples.gofish.decision.CardRequestBuilder;
import org.legoata.samples.gofish.model.Player;

public class UserController extends Controller {
	
	public static final String LABEL = "User";

	public UserController(ControlSet controls) {
		super(controls);
	}
	
	@Override
	public Decision getDecision() {
		Player user = (Player) this.getTurnControls().getTurnTaker();
		return this.getUserDecision(new CardRequestBuilder(user, this.getControls()), user);
	}

}
