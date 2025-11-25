package org.legoata.samples.gofish.decision;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.legoata.decision.Decision;
import org.legoata.decision.DecisionBuilder;
import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.decision.node.branching.InputSpecificity;
import org.legoata.decision.node.branching.ListDisplayMode;
import org.legoata.decision.node.branching.OptionSet;
import org.legoata.decision.node.branching.Option;
import org.legoata.decision.node.nonbranching.DecisionComplete;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;
import org.legoata.samples.gofish.action.AskForRank;
import org.legoata.samples.gofish.action.ShowStatus;
import org.legoata.samples.gofish.asset.Card;
import org.legoata.samples.gofish.asset.Rank;
import org.legoata.samples.gofish.model.Player;
import org.legoata.samples.gofish.util.GoFishUtils;

public class CardRequestBuilder extends DecisionBuilder {
	
	private ControlSet controls;

	public CardRequestBuilder(Player player, ControlSet controls) {
		this.controls = controls;
		this.setRootNode(new RootOptionSet());
		String txt = String.join(
				System.lineSeparator(),
				player.getName() + "'s turn!",
				printHand(player));
		this.setInitialText(txt);
	}
	
	private String printHand(Player player) {
		StringBuilder sb = new StringBuilder();
		Card[] cards = player.getCardsArrayCopy();
		for (int i = 0; i < cards.length; i++) {
			Card card = cards[i];
			sb.append(GoFishUtils.getString(card.getRank()));
			sb.append("/");
			sb.append(GoFishUtils.getString(card.getSuit()));
			if (i < cards.length - 1) {
				sb.append('\t');
			}
		}
		return sb.toString();
	}
	
	private class RootOptionSet extends OptionSet {
		
		public RootOptionSet() {
			super(ListDisplayMode.NUMBER_FROM_ONE, InputSpecificity.EXACT_MATCH);
		}

		private static final String SELECT_OPPONENT = "Select Opponent";
		private static final String INFO = "Check Player Status";

		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, LGObject actor, PrintStream out) {
			String title = selection.getTitle();
			DecisionBuilderNode node = null;
			if (title == INFO) {
				decision.setActionName(ShowStatus.LABEL);
				node = new DecisionComplete();
			} else if (title == SELECT_OPPONENT) {
				decision.setActionName(AskForRank.LABEL);
				decision.setData(new CardRequest());
				node = new OpponentSelectOptionSet();
			}
			return node;
		}

		@Override
		public void undo(ControlSet controls, Decision decision, LGObject actor) {
			decision.setActionName(null);
			decision.setData(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, LGObject actor) {
			ArrayList<Option> options = new ArrayList<Option>();
			options.add(new Option(SELECT_OPPONENT));
			options.add(new Option(INFO));
			return options;
		}

		@Override
		public String getPrompt() {
			return "Select an action.";
		}

	}
	
	private class OpponentSelectOptionSet extends OptionSet {

		public OpponentSelectOptionSet() {
			super(ListDisplayMode.NUMBER_FROM_ONE, InputSpecificity.EXACT_MATCH, true);
		}

		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, LGObject actor, PrintStream out) {
			CardRequest cardRequest = (CardRequest) decision.getData();
			cardRequest.setOpponent((UUID)selection.getAttachedData());
			return new RankSelectOptionSet();
		}

		@Override
		public void undo(ControlSet controls, Decision decision, LGObject actor) {
			CardRequest cardRequest = (CardRequest) decision.getData();
			cardRequest.setOpponent(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, LGObject actor) {
			ArrayList<Option> options = new ArrayList<Option>();
			for (LGObject lgPlayer : controls.getGameControls().getPlayers()) {
				if (lgPlayer != actor) {
					Player player = (Player) lgPlayer;
					options.add(new Option(player.getName(), player.getId()));
				}
			}
			return options;
		}

		@Override
		public String getPrompt() {
			return "Select opponent.";
		}

	}
	
	private class RankSelectOptionSet extends OptionSet {

		public RankSelectOptionSet() {
			super(ListDisplayMode.KEYS_AND_TITLES, InputSpecificity.EXACT_MATCH, true);
		}

		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, LGObject actor, PrintStream out) {
			CardRequest cardRequest = (CardRequest) decision.getData();
			String rankString = (String)selection.getAttachedData();
			cardRequest.setRank(Rank.valueOf(rankString));
			return new DecisionComplete();
		}

		@Override
		public void undo(ControlSet controls, Decision decision, LGObject actor) {
			CardRequest cardRequest = (CardRequest) decision.getData();
			cardRequest.setRank(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, LGObject actor) {
			Player player = (Player) actor;
			HashSet<Rank> hashSet = new HashSet<Rank>();
			for (Card card : player.getCardsArrayCopy()) {
				hashSet.add(card.getRank());
			}
			Rank[] ranksHeld = hashSet.toArray(new Rank[hashSet.size()]);
			Arrays.sort(ranksHeld);
			ArrayList<Option> options = new ArrayList<Option>();
			for (Rank rank : ranksHeld) {
				String key;
				int ordinal = rank.ordinal();
				switch (ordinal) {
				case 0:
					key = "A";
					break;
				case 10:
					key = "J";
					break;
				case 11:
					key = "Q";
					break;
				case 12:
					key = "K";
					break;
				default:
					key = Integer.toString(ordinal + 1);
					break;
				}
				options.add(new Option(GoFishUtils.getString(rank), rank.toString(), key));
			}
			return options;
		}

		@Override
		public String getPrompt() {
			return "Select a rank.";
		}

	}

}
