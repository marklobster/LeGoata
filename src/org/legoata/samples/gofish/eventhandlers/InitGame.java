package org.legoata.samples.gofish.eventhandlers;

import org.legoata.event.GameCycleEvent;
import org.legoata.event.GameCycleEventHandler;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;
import org.legoata.model.structure.LGCollection;
import org.legoata.samples.gofish.players.MeanBot;
import org.legoata.samples.gofish.players.NiceBot;
import org.legoata.samples.gofish.players.User;

public class InitGame implements GameCycleEventHandler {

	@Override
	public void consume(GameCycleEvent event, ControlSet controls) {
		
		// add players to game
		LGCollection players = controls.getGameControls().getPlayers();
		for (LGObject player : new LGObject[] { new User(), new NiceBot(), new MeanBot() }) {
			players.put(player);
		}

		// schedule one-time turn order randomizer for moment 0
		controls.getSchedulingControls().scheduleInitRoundEvent(0, new TurnOrderInitializer());
	}

}
