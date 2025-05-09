package legoata.engine.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

import legoata.engine.action.Action;
import legoata.engine.action.ActionResultCode;
import legoata.engine.action.ActionResult;
import legoata.engine.controller.command.RepeatController;
import legoata.engine.controller.command.CompleteTurn;
import legoata.engine.controller.command.ExitGame;
import legoata.engine.controller.command.TurnCommand;
import legoata.engine.controller.command.ChangeController;
import legoata.engine.decision.Decision;
import legoata.engine.decision.DecisionBuilder;
import legoata.engine.decision.node.DecisionBuilderNode;
import legoata.engine.decision.node.branching.Option;
import legoata.engine.decision.node.branching.OptionSet;
import legoata.engine.decision.node.nonbranching.DecisionComplete;
import legoata.engine.decision.node.nonbranching.GoBack;
import legoata.engine.decision.node.nonbranching.ReturnToRoot;
import legoata.engine.execute.controls.TurnControls;
import legoata.engine.execute.provider.action.ActionProvider;
import legoata.engine.model.LGObject;

public abstract class Controller {
	
	private Scanner scanner = null;

	public Controller(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public TurnCommand init(LGObject turnTaker, TurnControls controls) {
		return null;
	}
	
	public Decision getDecision(LGObject turnTaker, TurnControls controls) {
		return null;
	}
	
	public Action resolveActionName(LGObject turnTaker, ActionProvider provider, Decision decision, TurnControls controls) {
		return provider.getAction(decision.getAction());
	}
	
	public ActionResult executeAction(LGObject turnTaker, Action action, TurnControls controls) {
		return null;
	}
	
	public void onExecute(LGObject turnTaker, ActionResult result, TurnControls controls) {
		
	}
	
	public TurnCommand close(LGObject turnTaker, ActionResult result, TurnControls controls) {
		// default to error
		ActionResultCode code = result == null ? ActionResultCode.Error : result.getCode();
		switch (code) {
			case Consequential:
				return new CompleteTurn();
			case Inconsequential:
			case Incomplete:
				return new RepeatController();
			// if exit requested, or if in error state, exit the game
			default:
				return new ExitGame();
		}
	}
	
	protected Decision getUserDecision(DecisionBuilder builder, LGObject actor) {
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
				DecisionBuilderNode nextNode = currentMenu.select(builder.getDecision(), selection, actor, System.out);
				
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
			String input = this.scanner.nextLine();
			if (input.equalsIgnoreCase(BACK)) {
				escape = allowEscape;
			} else {
				selection = map.get(input);
			}
			
		} while (selection == null && !escape);
		
		return selection;
	}
	
}
