package legoata.engine.game;

import java.util.ArrayList;
import java.util.Scanner;

import legoata.engine.actions.CharacterAction;
import legoata.engine.actions.TargetingAction;
import legoata.engine.decision.Option;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.situation.Battle;
import legoata.engine.situation.TimedActionSituation;
import legoata.engine.utils.Utils;

public class GameRunner {
	
	private Scanner scanner;
	
	private ArrayList<GameCharacter> heroes = new ArrayList<GameCharacter>();
	private ArrayList<GameCharacter> enemies = new ArrayList<GameCharacter>();
	
	public void addTestHero(GameCharacter hero) {
		heroes.add(hero);
	}
	
	public void addTestEnemy(GameCharacter enemy) {
		enemies.add(enemy);
	}
	
	public void run() {
		
		scanner = new Scanner(System.in);
		
		TimedActionSituation situation = new Battle(heroes, enemies);
		while (!situation.isSituationOver()) {
			GameCharacter gChar = situation.getNextUp();
			ArrayList<CharacterAction> actions = situation.getPossibleActions();
			CharacterAction selectedAction = actions.get(0);
			selectedAction.setActionPerformer(gChar);
			if (selectedAction instanceof TargetingAction) {
				TargetingAction thisAction = (TargetingAction)selectedAction;
				ArrayList<GameCharacter> possibleTargets = situation.getPossibleTargets(thisAction.getTargetType());
				GameCharacter target = selectTarget(possibleTargets);
				thisAction.setTarget(target);
			}
			situation.performAction(selectedAction, System.out);
		}
		
		postGameDebrief();
	}
	
	private String getUserSelection(String prompt, ArrayList<Option> options) {
		String selection = null;
		boolean escape = false;
		
		do {
			System.out.println(prompt);
			int counter = 0;
			for (Option option : options) {
				System.out.println(String.format("%d - %s", counter++, option.getTitle()));
			}
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("b")) {
				escape = true;
			} else {
				for (Option option : options) {
					String key = option.getKey();
					if (key.equalsIgnoreCase(input)) {
						selection = key;
						break;
					}
				}
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
