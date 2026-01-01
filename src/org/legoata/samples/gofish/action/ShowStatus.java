package org.legoata.samples.gofish.action;

import java.io.PrintStream;

import org.legoata.action.ActionResult;
import org.legoata.action.ModelActionNullData;
import org.legoata.execute.ControlUnit;
import org.legoata.execute.GameControls;
import org.legoata.samples.gofish.model.Player;
import org.legoata.tracking.LGTrackable;

public class ShowStatus extends ModelActionNullData {
	
	public ShowStatus(ControlUnit controls) {
		super(controls);
	}

	public static final String LABEL = "ShowStatus";

	@Override
	public ActionResult execute() {
		GameControls gameControls = this.getGameControls();
		PrintStream out = gameControls.getOutStream();
		
		for (LGTrackable lgPlayer : gameControls.getPlayers()) {
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
