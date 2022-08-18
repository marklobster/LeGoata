package legoata.engine.game.battle;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.actions.MeleeAttack;
import legoata.engine.actions.TargetType;
import legoata.engine.actions.TargetingAction;
import legoata.engine.actions.UseItem;
import legoata.engine.decision.Decision;
import legoata.engine.decision.DecisionBuilder;
import legoata.engine.decision.node.DecisionBuilderNode;
import legoata.engine.decision.node.branching.Option;
import legoata.engine.decision.node.branching.OptionSet;
import legoata.engine.decision.node.nonbranching.DecisionComplete;
import legoata.engine.equipment.Item;
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
	
	private class ActionMenu implements OptionSet {
		
		private final String MELEE_MENU_TITLE = "Melee Attack";
		private final String ITEM_MENU_TITLE = "Use Item";
		private final String LOOK_MENU_TITLE = "Look Around";

		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, GameCharacter actor, PrintStream out) {
			
			OptionSet options = null;
			
			switch (selection.getTitle()) {
			
			case ITEM_MENU_TITLE:
				UseItem useItemAction = new UseItem();
				useItemAction.setActionPerformer(actor);
				decision.setAction(useItemAction);
				options = new ItemMenu();
				break;
			case MELEE_MENU_TITLE:
				MeleeAttack meleeAction = new MeleeAttack();
				meleeAction.setActionPerformer(actor);
				decision.setAction(meleeAction);
				options = new TargetMenu();
				break;
			case LOOK_MENU_TITLE:
				options = new LookMenu();
				break;
			}
			
			return options;
		}

		@Override
		public void undoSelection(Decision decision, GameCharacter actor) {
			decision.setAction(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, GameCharacter character) {
			 ArrayList<Option> options = new ArrayList<Option>();
			 options.add(new Option(MELEE_MENU_TITLE));
			 options.add(new Option(ITEM_MENU_TITLE));
			 options.add(new Option(LOOK_MENU_TITLE));
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
	
	private class ItemMenu implements OptionSet {

		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, GameCharacter actor, PrintStream out) {
			UseItem action = (UseItem)decision.getAction();
			Item item = (Item)selection.getAttachedData();
			action.setItem(item);
			if (item.getTargetType() == TargetType.Self) {
				action.setTarget(action.getActionPerformer());
				return null;
			}
			return new TargetMenu();
		}

		@Override
		public void undoSelection(Decision decision, GameCharacter actor) {
			UseItem action = (UseItem)decision.getAction();
			action.setItem(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, GameCharacter character) {
			ArrayList<Option> options = new ArrayList<Option>();
			for (Item item : character.getEquipment()) {
				options.add(new Option(item.getName(), item));
			}
			return options;
		}

		@Override
		public String getPrompt() {
			return "Select item:";
		}

		@Override
		public String getEmptySetText() {
			return "Inventory is emtpy.";
		}
		
	}
	
	private class TargetMenu implements OptionSet {

		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, GameCharacter actor, PrintStream out) {
			TargetingAction action = (TargetingAction)decision.getAction();
			GameCharacter target = (GameCharacter)selection.getAttachedData();
			action.setTarget(target);
			return new DecisionComplete();
		}

		@Override
		public void undoSelection(Decision decision, GameCharacter actor) {
			TargetingAction action = (TargetingAction)decision.getAction();
			action.setTarget(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, GameCharacter actor) {
			
			ArrayList<Option> options = new ArrayList<Option>();
			ArrayList<GameCharacter> possibleTargets;
			
			TargetingAction action = (TargetingAction)decision.getAction();
			
			switch (action.getTargetType()) {
			
			case Self:
				options.add(new Option("Self", actor));
				break;
				
			case Ally:
				possibleTargets = heroes.contains(actor) ?
						heroes :
						enemies;
				for (GameCharacter target : possibleTargets) {
					if (!target.isFallen()) {
						options.add(new Option(target.getFullName(), target));
					}
				}
				break;
				
			case Foe:
				possibleTargets = heroes.contains(actor) ?
						enemies :
						heroes;
				for (GameCharacter target : possibleTargets) {
					if (!target.isFallen()) {
						options.add(new Option(target.getFullName(), target));
					}
				}
				break;
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
	
	private class LookMenu implements OptionSet {
		
		private final String ALLIES = "Allies";
		private final String FOES = "Foes";
		private final String SELF = "Self";
		
		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, GameCharacter actor, PrintStream out) {
			switch (selection.getTitle()) {
			case ALLIES:
				for (GameCharacter gameCharacter : heroes) {
					printHealth(gameCharacter, out);
				}
				break;
			case FOES:
				for (GameCharacter gameCharacter : enemies) {
					printHealth(gameCharacter, out);
				}
				break;
				
			case SELF:
				printHealth(actor, out);
				break;
			}
			return null;
		}

		@Override
		public void undoSelection(Decision decision, GameCharacter actor) {
			decision.setAction(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, GameCharacter actor) {
			
			ArrayList<Option> options = new ArrayList<Option>();
			options.add(new Option(ALLIES));
			options.add(new Option(FOES));
			options.add(new Option(SELF));
			
			return options;
		}

		@Override
		public String getPrompt() {
			return "Look at who?";
		}

		@Override
		public String getEmptySetText() {
			return null;
		}
		
		private void printHealth(GameCharacter gameCharacter, PrintStream out) {
			if (gameCharacter.isFallen()) {
				out.println(
					String.format("%s is down for the count", gameCharacter.getFullName()));
			} else {
				out.println(
					String.format(
						"%s has %d / %d health",
						gameCharacter.getFullName(),
						gameCharacter.getHealth(),
						gameCharacter.getMaxHealth()));
			}
		}
	}
}
