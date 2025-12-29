package org.legoata.samples.gofish.eventhandler;

import java.io.PrintStream;

import org.legoata.event.ActionEvent;
import org.legoata.event.ActionEventHandler;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameControls;
import org.legoata.model.LGTrackable;
import org.legoata.samples.gofish.model.Player;

public class CheckForWin implements ActionEventHandler {

	@Override
	public void consume(ActionEvent event, ControlSet controls) {
		GameControls gameControls = controls.getGameControls();
		Player[] players = new Player[gameControls.getPlayers().size()];
		int i = 0;
		boolean winnerFound = false;
		for (LGTrackable playerObj : gameControls.getPlayers()) {
			Player player = (Player) playerObj;
			players[i++] = player;
			if (player.getHandSize() == 0) {
				winnerFound = true;
			}
		}
		
		if (winnerFound) {
			gameControls.setExitFlag(true);
			printStats(players, gameControls.getOutStream());
		}
	}
	
	private void printStats(Player[] players, PrintStream out) {
		out.println();
		out.println("We have a winner!");
		out.println();
		out.println("Final stats:");
		out.println();
		for (Player player : players) {
			out.printf("%s: %d book(s)%s", player.getName(), player.getNumberBooks(), System.lineSeparator());
		}
	}

}
