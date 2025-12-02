package org.legoata.samples.gofish.controller;

import org.legoata.controller.Controller;
import org.legoata.decision.ActionDecision;
import org.legoata.execute.ControlSet;
import org.legoata.samples.gofish.decision.CardRequestBuilder;

public class UserController extends Controller {
	
	public static final String LABEL = "User";

	public UserController(ControlSet controls) {
		super(controls);
	}
	
	@Override
	public ActionDecision getDecision() {
		CardRequestBuilder decisionBuilder = new CardRequestBuilder(this.getControls());
		return decisionBuilder.getUserDecision(new ActionDecision());
	}

}
