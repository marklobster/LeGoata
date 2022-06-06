package legoata.engine.situation;

import java.util.ArrayList;

import legoata.engine.actions.CharacterAction;
import legoata.engine.actions.MeleeAttack;
import legoata.engine.actions.TargetType;
import legoata.engine.gamecharacter.GameCharacter;

public class Battle extends TimedActionSituation {
	private ArrayList<GameCharacter> goodies = new ArrayList<GameCharacter>();
	private ArrayList<GameCharacter> baddies = new ArrayList<GameCharacter>();
	
	public Battle(ArrayList<GameCharacter> heroes, ArrayList<GameCharacter> enemies) {

		for (GameCharacter gChar : heroes) {
			goodies.add(gChar);
			addGameCharacter(gChar);
		}
		
		for (GameCharacter gChar : enemies) {
			baddies.add(gChar);
			addGameCharacter(gChar);
		}
	}
	
	@Override
	public ArrayList<CharacterAction> getPossibleActions() {
		ArrayList<CharacterAction> actionList = new ArrayList<CharacterAction>();
		actionList.add(new MeleeAttack());
		return actionList;
	}
	
	@Override
	public ArrayList<GameCharacter> getPossibleTargets(TargetType targetType) {
		ArrayList<GameCharacter> targets = new ArrayList<GameCharacter>();
		ArrayList<GameCharacter> allies;
		ArrayList<GameCharacter> foes;
		if (goodies.contains(getCurrentCharacter())) {
			allies = goodies;
			foes = baddies;
		} else {
			allies = baddies;
			foes = goodies;
		}
		
		switch (targetType) {
			case Ally:
				for (GameCharacter gc : allies) {
					if (!gc.isFallen()) {
						targets.add(gc);
					}
				}
				break;
			case Foe:
				for (GameCharacter gc : foes) {
					if (!gc.isFallen()) {
						targets.add(gc);
					}
				}
				break;
		}
		
		return targets;
	}

	@Override
	protected void afterActionComplete() {
		removeFallenSoldiers();
	}

	@Override
	public boolean isSituationOver() throws IllegalStateException {
		boolean goodTeamRemains = false;
		for (GameCharacter member : goodies) {
			if (!member.isFallen()) {
				goodTeamRemains = true;
				break;
			}
		}
		
		boolean badTeamRemains = false;
		for (GameCharacter member : baddies) {
			if (!member.isFallen()) {
				badTeamRemains = true;
				break;
			}
		}
		
		if (!goodTeamRemains && !badTeamRemains) {
			throw new IllegalStateException("Everyone is dead :(");
		}
		
		return goodTeamRemains ^ badTeamRemains;
	}

	private void removeFallenSoldiers() {
		for (GameCharacter member : goodies) {
			if (member.isFallen()) {
				removeGameCharacter(member);
			}
		}
		
		for (GameCharacter member : baddies) {
			if (member.isFallen()) {
				removeGameCharacter(member);
			}
		}
	}

}
