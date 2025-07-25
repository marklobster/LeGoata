package org.legoata.samples.gofish.eventhandler;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.legoata.event.GameCycleEvent;
import org.legoata.event.GameCycleEventHandler;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameControls;
import org.legoata.model.structure.LGCollection;
import org.legoata.samples.gofish.asset.Deck;
import org.legoata.samples.gofish.model.GoFishGame;
import org.legoata.samples.gofish.model.MeanBot;
import org.legoata.samples.gofish.model.NiceBot;
import org.legoata.samples.gofish.model.Player;
import org.legoata.samples.gofish.model.User;
import org.legoata.utils.Utils;

public class InitGame implements GameCycleEventHandler {

	@Override
	public void consume(GameCycleEvent event, ControlSet controls) {
		
		GameControls gameControls = controls.getGameControls();
		
		// get user name
		String name = getName(gameControls.getScanner(), gameControls.getOutStream());
		
		// add players to game and to turn order
		LGCollection playerSet = gameControls.getPlayers();
		ArrayList<UUID> turnOrder = gameControls.getTurnOrder();
		Player[] players = new Player[] { new User(name), new NiceBot(), new MeanBot() };
		for (Player player : players) {
			playerSet.put(player);
			turnOrder.add(player.getId());
		}
		
		// randomize turn order
		gameControls.setTurnOrder(Utils.shuffle(turnOrder));
		
		// add game state object to loose objects
		GoFishGame goFishGame = new GoFishGame();
		gameControls.getLooseObjects().put(goFishGame);
		
		// deal hands
		Deck deck = goFishGame.getDeck();
		for (int i = 0; i < goFishGame.INITIAL_HAND_SIZE; i++) {
			for (Player player : players) {
				player.acceptCard(deck.draw());
			}
		}
		
		gameControls.getOutStream().println("Game initialized!");
	}
	
	private String getName(Scanner in, PrintStream out) {
		String name;
		do {
			out.println("What is your name?");
			name = in.nextLine();
		} while (name == null || name == "");
		return name;
	}

}
