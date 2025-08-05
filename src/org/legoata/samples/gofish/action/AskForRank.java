package org.legoata.samples.gofish.action;

import java.io.PrintStream;

import org.legoata.action.ActionResult;
import org.legoata.action.ModelAction;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameControls;
import org.legoata.execute.TurnControls;
import org.legoata.model.LGObject;
import org.legoata.model.structure.LGCollection;
import org.legoata.samples.gofish.Keys;
import org.legoata.samples.gofish.asset.Book;
import org.legoata.samples.gofish.asset.Card;
import org.legoata.samples.gofish.asset.Deck;
import org.legoata.samples.gofish.asset.Rank;
import org.legoata.samples.gofish.decision.CardRequest;
import org.legoata.samples.gofish.model.GoFishGame;
import org.legoata.samples.gofish.model.Player;
import org.legoata.samples.gofish.util.GoFishUtils;

public class AskForRank extends ModelAction<CardRequest> {
	
	public AskForRank(ControlSet controls) {
		super(controls);
	}

	public static final String LABEL = "AskForRank";

	@Override
	public ActionResult execute(CardRequest input) {
		GameControls gameControls = this.getGameControls();
		PrintStream out = gameControls.getOutStream();
		
		// get cards from opponent
		Player player = (Player) this.getTurnControls().getTurnTaker();
		Player opponent = (Player) gameControls.getPlayers().get(input.getOpponent());
		Rank rank = input.getRank();
		out.printf("%s asks %s, \"Got any %s?\"%s",
				player.getName(),
				opponent.getName(),
				GoFishUtils.getPluralString(rank),
				System.lineSeparator());
		Card[] cards = opponent.giveAwayCards(rank);
		
		// take card from player or go fish
		GoFishGame gameState = (GoFishGame) gameControls.getLooseObjects().get(Keys.getGameKey());
		Deck deck = gameState.getDeck();
		Book[] newBooks = null;
		if (cards.length == 0) { // go fish!
			out.println("Go fish!");
			Card draw = deck.draw();
			if (draw == null) {
				out.println("There's nothing left to draw.");
			} else {
				newBooks = player.acceptCard(draw);
			}
		} else { // take cards
			newBooks = player.acceptCards(cards);
			out.printf("%s takes %d %s!%s",
					player.getName(),
					cards.length,
					cards.length > 1 ? GoFishUtils.getPluralString(rank) : GoFishUtils.getString(rank),
					System.lineSeparator());
		}
		
		// check for books
		if (newBooks != null && newBooks.length > 0) {
			out.printf("%s just got a book of %s!%s",
					player.getName(),
					GoFishUtils.getPluralString(newBooks[0].getRank()),
					System.lineSeparator());
			out.printf("\"%s\"%s", player.getCatchphrase(), System.lineSeparator());
			
			// add follow-up turn, unless game is over
			if (!winnerFound(gameControls.getPlayers())) {
				out.printf("%s gets another turn!%s", player.getName(), System.lineSeparator());
				
				// take another action this turn by increasing action limit
				TurnControls turnControls = this.getTurnControls();
				turnControls.setActionLimit(turnControls.getActionLimit() + 1);
			}
		}
		
		return this.actionCompletedWithConsequence();
	}
	
	private boolean winnerFound(LGCollection players) {
		for (LGObject lgPlayer : players) {
			Player player = (Player) lgPlayer;
			if (player.getHandSize() == 0) {
				return true;
			}
		}
		return false;
	}

}
