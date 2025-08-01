package org.legoata.samples.gofish.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.legoata.controller.Controller;
import org.legoata.decision.Decision;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;
import org.legoata.samples.gofish.action.AskForRank;
import org.legoata.samples.gofish.asset.Card;
import org.legoata.samples.gofish.decision.CardRequest;
import org.legoata.samples.gofish.model.Player;
import org.legoata.utils.Utils;

public class BotController extends Controller {
	
	public static final String LABEL = "BotController";

	public BotController(LGObject turnTaker, ControlSet controls) {
		super(turnTaker, controls);
	}
	
	@Override
	public Decision getDecision() {
		Player player = (Player) this.getControls().getTurnControls().getTurnTaker();
		CardRequest request = new CardRequest();
		
		// randomize rank
		Card[] hand = player.getCardsArrayCopy();
		int index = Utils.getRandom(0, hand.length - 1);
		request.setRank(hand[index].getRank());
		
		// randomize opponent
		ArrayList<UUID> opponents = new ArrayList<UUID>();
		for (LGObject lgPlayer : this.getControls().getGameControls().getPlayers()) {
			if (lgPlayer.getId() != player.getId()) {
				opponents.add(lgPlayer.getId());
			}
		}
		request.setOpponent(Utils.pickRandom(opponents));
		
		// instantiate decision
		Decision decision = new Decision();
		decision.setActionName(AskForRank.LABEL);
		decision.setData(request);
		return decision;
	}

}
