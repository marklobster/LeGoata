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
import org.legoata.samples.gofish.GoFishConstants;
import org.legoata.samples.gofish.asset.Book;
import org.legoata.samples.gofish.asset.Card;
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
		PrintStream out = gameControls.getOutStream();
		
		// get user name and catch phrase
		out.println("Let's play Go Fish!");
		String name = getName(gameControls.getScanner(), out);
		String phrase = getCatchphrase(gameControls.getScanner(), out);
		
		// add players to game and to turn order
		LGCollection playerSet = gameControls.getPlayers();
		ArrayList<UUID> turnOrder = gameControls.getTurnOrder();
		Player[] players = new Player[] { new User(name, phrase), new NiceBot(), new MeanBot() };
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
		out.println("Time to deal.");
		Deck deck = goFishGame.getDeck();
		Card[][] hands = new Card[][] {
			new Card[goFishGame.INITIAL_HAND_SIZE],
			new Card[goFishGame.INITIAL_HAND_SIZE],
			new Card[goFishGame.INITIAL_HAND_SIZE]
		};
		for (int i = 0; i < goFishGame.INITIAL_HAND_SIZE; i++) {
			for (int j = 0; j < hands.length; j++) {
				hands[j][i] = deck.draw();
			}
		}
		for (int i = 0; i < players.length; i++) {
			Book[] books = players[i].acceptCards(hands[i]);
			if (books.length > 0) {
				// not likely, but technically possible someone will get dealt a book
				out.printf("%s already has a book!%s", players[i].getName(), System.lineSeparator());
			}
		}
	}
	
	private String getName(Scanner in, PrintStream out) {
		String name;
		do {
			out.println("What is your name?");
			name = in.nextLine();
		} while (name == null || name == "");
		return name;
	}
	
	private String getCatchphrase(Scanner in, PrintStream out) {
		out.println("Enter a catchphrase, or press enter for a default phrase.");
		String phrase = in.nextLine();
		if (phrase == null || phrase == "") {
			phrase = GoFishConstants.DEFAULT_CATCHPHRASE;
		}
		return phrase;
	}

}
