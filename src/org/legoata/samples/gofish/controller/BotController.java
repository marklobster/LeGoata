package org.legoata.samples.gofish.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.legoata.action.decision.ActionDecision;
import org.legoata.controller.Controller;
import org.legoata.execute.ControlUnit;
import org.legoata.samples.gofish.action.AskForRank;
import org.legoata.samples.gofish.asset.Card;
import org.legoata.samples.gofish.decision.CardRequest;
import org.legoata.samples.gofish.model.Player;
import org.legoata.tracking.LGTrackable;
import org.legoata.utils.LGUtils;

public class BotController extends Controller {
	
	public static final String LABEL = "BotController";

	public BotController(ControlUnit controls) {
		super(controls);
	}
	
	@Override
	public ActionDecision getDecision() {
		Player player = (Player) this.getTurnControls().getTurnTaker();
		CardRequest request = new CardRequest();
		
		// randomize rank
		Card[] hand = player.getCardsArrayCopy();
		int index = LGUtils.getRandom(0, hand.length - 1);
		request.setRank(hand[index].getRank());
		
		// randomize opponent
		ArrayList<UUID> opponents = new ArrayList<UUID>();
		for (LGTrackable lgPlayer : this.getGameControls().getPlayers()) {
			if (lgPlayer.getId() != player.getId()) {
				opponents.add(lgPlayer.getId());
			}
		}
		request.setOpponent(LGUtils.pickRandom(opponents));
		
		// instantiate decision
		ActionDecision decision = new ActionDecision();
		decision.setActionName(AskForRank.LABEL);
		decision.setData(request);
		return decision;
	}

}
