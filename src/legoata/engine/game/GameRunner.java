package legoata.engine.game;

import java.util.ArrayList;
import legoata.engine.actions.CharacterAction;
import legoata.engine.actions.TargetingAction;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.situation.Battle;
import legoata.engine.situation.TimedActionSituation;
import legoata.engine.utils.Utils;

public class GameRunner {
	
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
