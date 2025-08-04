package org.legoata.samples.gofish.action;

import java.io.PrintStream;

import org.legoata.action.ActionResult;
import org.legoata.action.ModelActionNullData;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameControls;
import org.legoata.model.LGObject;
import org.legoata.samples.gofish.model.Player;

public class ShowStatus extends ModelActionNullData {
	
	public static final String LABEL = "ShowStatus";

	@Override
	public ActionResult execute(LGObject actor, ControlSet controls) {
		GameControls gameControls = controls.getGameControls();
		PrintStream out = gameControls.getOutStream();
		
		for (LGObject lgPlayer : gameControls.getPlayers()) {
			Player goFishPlayer = (Player) lgPlayer;
			out.printf("%s: %d book(s), %d card(s) left%s",
					goFishPlayer.getName(),
					goFishPlayer.getNumberBooks(),
					goFishPlayer.getHandSize(),
					System.lineSeparator());
		}
		
		return this.actionCompletedWithoutConsequence();
	}

}
