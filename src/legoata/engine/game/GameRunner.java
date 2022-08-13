package legoata.engine.game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

import legoata.engine.actions.MeleeAttack;
import legoata.engine.actions.TargetType;
import legoata.engine.decision.Decision;
import legoata.engine.decision.DecisionBuilder;
import legoata.engine.decision.node.DecisionBuilderNode;
import legoata.engine.decision.node.branching.Option;
import legoata.engine.decision.node.branching.OptionSet;
import legoata.engine.decision.node.nonbranching.DecisionComplete;
import legoata.engine.decision.node.nonbranching.GoBack;
import legoata.engine.decision.node.nonbranching.ReturnToRoot;
import legoata.engine.game.battle.Battle;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.situation.TimedActionSituation;
import legoata.engine.utils.Utils;

public class GameRunner {
	
	private Scanner scanner = new Scanner(System.in);
	
	private ArrayList<GameCharacter> heroes = new ArrayList<GameCharacter>();
	private ArrayList<GameCharacter> enemies = new ArrayList<GameCharacter>();
	
	public void addTestHero(GameCharacter hero) {
		heroes.add(hero);
	}
	
	public void addTestEnemy(GameCharacter enemy) {
		enemies.add(enemy);
	}
	
	public void run() {
		
		TimedActionSituation situation = new Battle(heroes, enemies);
		
		while (!situation.isSituationOver()) {
			
			GameCharacter actor = situation.getNextUp();
			Decision decision = null;
			
			// check if user or not
			if (actor.isUserControlled()) {
				
				// build decision from user input
				DecisionBuilder builder = situation.getDecisionBuilder();
				decision = buildUserDecision(builder, actor);
				
			} else {
				
				// NPC attacks random foe
				MeleeAttack attack = new MeleeAttack();
				attack.setActionPerformer(actor);
				ArrayList<GameCharacter> possibleTargets = situation.getPossibleTargets(TargetType.Foe);
				GameCharacter target = selectTarget(possibleTargets);
				attack.setTarget(target);
				decision = new Decision();
				decision.setAction(attack);
			}
			
			situation.submitDecision(decision, System.out);
			pause();
			System.out.println();
		}
		
		postGameDebrief();
	}
	
	private Decision buildUserDecision(DecisionBuilder builder, GameCharacter actor) throws UnsupportedOperationException {
		
		// put root level menu onto stack
		Stack<OptionSet> menuStack = new Stack<OptionSet>();
		menuStack.push(builder.getRootMenu());
		boolean decisionComplete = false;
		
		// print initial text of menu tree
		String initialText = builder.getInitialText();
		if (initialText != null && initialText != "") {
			System.out.println(initialText);
		}
		
		do {
			// get current menu
			OptionSet currentMenu = menuStack.peek();
			ArrayList<Option> options = currentMenu.getOptions(builder.getDecision(), actor);
			boolean isSubMenu = menuStack.size() > 1;
			
			// check for invalid state
			if (!isSubMenu && (options == null || options.size() == 0)) {
				throw new IllegalStateException("Root menu cannot have zero options.");
			}
			
			// get option from user input
			Option selection = getUserSelection(
					currentMenu.getPrompt(),
					options,
					isSubMenu,
					isSubMenu ? currentMenu.getEmptySetText() : null);
			
			// none selected - go back to previous menu
			// but if there is no previous menu, just get input again
			if (selection == null && isSubMenu) {
				
				// revert previous selection
				menuStack.pop();
				OptionSet previousMenu = menuStack.peek();
				previousMenu.undoSelection(builder.getDecision(), actor);
			}
			
			// handle selected option
			else if (selection != null) {
				
				// run method for selected option
				DecisionBuilderNode nextNode = currentMenu.select(builder.getDecision(), selection, actor);
				
				// repeat same decision
				if (nextNode == null) {
					currentMenu.undoSelection(builder.getDecision(), actor);
				}
				
				// decision completed
				else if (nextNode instanceof DecisionComplete) {
					decisionComplete = true;
				}
				
				// go back n number of nodes
				else if (nextNode instanceof GoBack) {
					GoBack goBackSignal = (GoBack)nextNode;
					menuStack.peek().undoSelection(builder.getDecision(), actor);
					int counter = 0;
					while (counter++ < goBackSignal.getNumberOfStepsBack()
							&& menuStack.size() > 1) {
						menuStack.pop();
						menuStack.peek().undoSelection(builder.getDecision(), actor);
					}
				}
				
				// return to menu root
				else if (nextNode instanceof ReturnToRoot) {
					menuStack.peek().undoSelection(builder.getDecision(), actor);
					while (menuStack.size() > 1) {
						menuStack.pop();
						menuStack.peek().undoSelection(builder.getDecision(), actor);
					}
				}
				
				// sub-menu
				else if (nextNode instanceof OptionSet) {
					menuStack.push((OptionSet)nextNode);
				}
				
				// unsupported DecisionBuilderNode type
				else {
					String err = String.format(
							"The legoata.engine.decision.DecisionBuilderNode sub-class %s is not supported.",
							nextNode.getClass());
					throw new UnsupportedOperationException(err);
				}
			}
			
		} while (!decisionComplete);
		
		return builder.getDecision();
	}
	
	private Option getUserSelection(String prompt, ArrayList<Option> options, boolean allowEscape, String emptyListText) {
		
		final String BACK = "b";
		Option selection = null;
		boolean escape = false;
				
		do {
			// show prompt
			System.out.println(prompt);
			
			// show options
			Hashtable<String, Option> map = new Hashtable<String, Option>();
			if (options.isEmpty() && emptyListText != null && emptyListText != "") {
				System.out.println(emptyListText);
			} else {
				int counter = 0;
				for (Option option : options) {
					map.put(String.valueOf(counter), option);
					System.out.println(String.format("[%d] %s", counter++, option.getTitle()));
				}
			}
			
			// show 'back' option, when applicable
			if (allowEscape) {
				System.out.println("[b] Back");
			}
			
			// get input, check if valid
			selection = null;
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase(BACK)) {
				escape = allowEscape;
			} else {
				selection = map.get(input);
			}
			
		} while (selection == null && !escape);
		
		return selection;
	}
	
	private void pause() {
		scanner.nextLine();
	}
	
	private void postGameDebrief() {
		boolean goodTeamRemains = false;
		for (GameCharacter member : heroes) {
			if (!member.isFallen()) {
				goodTeamRemains = true;
				break;
			}
		}
		
		System.out.println("");
		System.out.println("The winner is... " + (goodTeamRemains ? "you!!" : "the bad guys!!"));
		
		System.out.println("");
		System.out.println("The stats:");
		System.out.println("");
		for (GameCharacter gc : heroes) {
			gc.printStats(System.out);
		}
		for (GameCharacter gc : enemies) {
			gc.printStats(System.out);
		}
	}
	
	private GameCharacter selectTarget(ArrayList<GameCharacter> targets) {
		return Utils.pickRandom(targets);
	}
}
