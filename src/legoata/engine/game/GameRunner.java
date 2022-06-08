package legoata.engine.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

import legoata.engine.actions.MeleeAttack;
import legoata.engine.actions.TargetType;
import legoata.engine.decision.Decision;
import legoata.engine.decision.DecisionBuilder;
import legoata.engine.decision.Option;
import legoata.engine.decision.OptionSet;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.situation.Battle;
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
		}
		
		postGameDebrief();
	}
	
	private Decision buildUserDecision(DecisionBuilder builder, GameCharacter actor) {
		
		Stack<OptionSet> menuStack = new Stack<OptionSet>();
		menuStack.push(builder.getOptionSet());
		boolean decisionComplete = false;
		
		do {
			OptionSet currentMenu = menuStack.peek();
			ArrayList<Option> options = currentMenu.getOptions(actor);
			boolean isSubMenu = menuStack.size() > 1;
			
			if (!isSubMenu && (options == null || options.size() == 0)) {
				throw new IllegalStateException("Root menu cannot have zero options.");
			}
			
			Option selection = getUserSelection(
					currentMenu.getPrompt(),
					options,
					isSubMenu,
					isSubMenu ? currentMenu.getEmptySetText() : null);
			
			if (selection == null && isSubMenu) {
				
				// revert previous selection
				menuStack.pop();
				OptionSet previousMenu = menuStack.peek();
				previousMenu.undoSelection(builder.getDecision());
				
			} else if (selection != null) {
				
				// make selection NOT DONE YET
				OptionSet nextMenu = currentMenu.select(builder.getDecision(), selection);
				
				if (nextMenu == null) {
					// end of tree reached, decision complete
					decisionComplete = true;
				} else {
					// push next sub menu
					menuStack.push(nextMenu);
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
				System.out.println("[b] back");
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
