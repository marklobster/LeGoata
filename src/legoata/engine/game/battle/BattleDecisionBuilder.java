package legoata.engine.game.battle;

import java.util.ArrayList;

import legoata.engine.actions.MeleeAttack;
import legoata.engine.actions.TargetingAction;
import legoata.engine.decision.Decision;
import legoata.engine.decision.DecisionBuilder;
import legoata.engine.decision.Option;
import legoata.engine.decision.OptionSet;
import legoata.engine.gamecharacter.GameCharacter;

/**
 * Decision Builder for Battles.
 * @author Mark
 *
 */
public class BattleDecisionBuilder extends DecisionBuilder {
	
	private ArrayList<GameCharacter> heroes;
	private ArrayList<GameCharacter> enemies;

	public BattleDecisionBuilder(
			ArrayList<GameCharacter> heroes,
			ArrayList<GameCharacter> enemies,
			GameCharacter actor) {
		

		this.heroes = heroes;
		this.enemies = enemies;
		
		this.setOptionSet(new ActionMenu());
		this.setInitialText(actor.getFullName() + "'s turn!");
	}
	
	private class TargetMenu implements OptionSet {

		@Override
		public OptionSet select(Decision decision, Option selection, GameCharacter actor) {
			TargetingAction action = (TargetingAction)decision.getAction();
			GameCharacter target = (GameCharacter)selection.getAttachedData();
			action.setTarget(target);
			return null;
		}

		@Override
		public void undoSelection(Decision decision) {
			TargetingAction action = (TargetingAction)decision.getAction();
			action.setTarget(null);
		}

		@Override
		public ArrayList<Option> getOptions(GameCharacter actor) {
			
			ArrayList<Option> options = new ArrayList<Option>();
			ArrayList<GameCharacter> possibleTargets = heroes.contains(actor) ?
					enemies :
					heroes;
			
			for (GameCharacter target : possibleTargets) {
				if (!target.isFallen()) {
					options.add(new Option(target.getFullName(), target));
				}
			}
			
			return options;
		}

		@Override
		public String getPrompt() {
			return "Select target:";
		}

		@Override
		public String getEmptySetText() {
			return null;
		}
		
	}

	private class ActionMenu implements OptionSet {

		@Override
		public OptionSet select(Decision decision, Option selection, GameCharacter actor) {
			switch (selection.getTitle()) {
			default:
				MeleeAttack action = new MeleeAttack();
				action.setActionPerformer(actor);
				decision.setAction(action);
			}
			return new TargetMenu();
		}

		@Override
		public void undoSelection(Decision decision) {
			decision.setAction(null);
		}

		@Override
		public ArrayList<Option> getOptions(GameCharacter character) {
			 ArrayList<Option> options = new ArrayList<Option>();
			 options.add(new Option("Melee Attack"));
			 return options;
		}

		@Override
		public String getPrompt() {
			return "Select action:";
		}

		@Override
		public String getEmptySetText() {
			return null;
		}
		
	}
}
