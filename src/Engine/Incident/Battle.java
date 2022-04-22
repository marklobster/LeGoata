package Engine.Incident;

import java.util.ArrayList;

import Engine.Actions.MeleeAttack;
import Engine.GameCharacter.GameCharacter;

public class Battle {
	private ArrayList<GameCharacter> goodies;
	private ArrayList<GameCharacter> baddies;
	private TurnTracker turnTracker = new TurnTracker();
	
	public Battle(ArrayList<GameCharacter> heroes, ArrayList<GameCharacter> enemies) {
		goodies = heroes;
		baddies = enemies;
	}
	
	public void run() throws IllegalStateException {
		for (GameCharacter gChar : goodies) {
			turnTracker.addGameCharacter(gChar);
		}
		for (GameCharacter gChar : baddies) {
			turnTracker.addGameCharacter(gChar);
		}
		
		ArrayList<GameCharacter> winners = null;
		while (winners == null) {
			GameCharacter nextUp = turnTracker.getNextUp();
			ArrayList<GameCharacter> opponents = goodies.contains(nextUp) ? baddies : goodies;
			GameCharacter target = nextUp.selectTarget(opponents);
			MeleeAttack action = new MeleeAttack();
			action.setActionPerformer(nextUp);
			action.setTarget(target);
			action.performAction(System.out);
			if (target.isFallen()) {
				turnTracker.removeGameCharacter(target);
			}
			turnTracker.payActionCost(nextUp, action.getCost());
			winners = getVictor();
		}
		
		System.out.println("The winner is... " + (winners == goodies ? "you!!" : "the bad guys!!"));
	}
	
	private ArrayList<GameCharacter> getVictor() throws IllegalStateException {
		boolean goodTeamRemains = false;
		for (GameCharacter member : goodies) {
			if (member.getHealth() > 0) {
				goodTeamRemains = true;
				break;
			}
		}
		
		boolean badTeamRemains = false;
		for (GameCharacter member : baddies) {
			if (member.getHealth() > 0) {
				badTeamRemains = true;
				break;
			}
		}
		
		if (goodTeamRemains && !badTeamRemains) {
			return goodies;
		}
		
		if (!goodTeamRemains && badTeamRemains)
		{
			return baddies;
		}
		
		if (!goodTeamRemains && !badTeamRemains) {
			throw new IllegalStateException("Everyone is dead :(");
		}
		
		return null;
	}
}
