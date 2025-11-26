package org.legoata.samples.gofish.controller;

import org.legoata.controller.Controller;
import org.legoata.decision.ActionDecision;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameControls;
import org.legoata.samples.gofish.decision.CardRequestBuilder;
import org.legoata.samples.gofish.model.Player;

public class UserController extends Controller {
	
	public static final String LABEL = "User";

	public UserController(ControlSet controls) {
		super(controls);
	}
	
	@Override
	public ActionDecision getDecision() {
		Player user = (Player) this.getTurnControls().getTurnTaker();
		CardRequestBuilder decisionBuilder = new CardRequestBuilder(user, this.getControls());
		GameControls gameControls = this.getGameControls();
		return decisionBuilder.getUserDecision(user, new ActionDecision(), gameControls.getScanner(), gameControls.getOutStream());
	}

}
