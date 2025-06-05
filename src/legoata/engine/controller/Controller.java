package legoata.engine.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

import legoata.engine.action.Action;
import legoata.engine.action.ActionResultCode;
import legoata.engine.action.ModelAction;
import legoata.engine.action.ModelActionNullData;
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
import legoata.engine.execute.ControlSet;
import legoata.engine.execute.GameControls;
import legoata.engine.execute.provider.action.ActionProvider;
import legoata.engine.model.LGObject;

public abstract class Controller {
	
	private LGObject turnTaker = null;
	private ControlSet controls = null;

	public Controller(LGObject turnTaker, ControlSet controls) {
		this.turnTaker = turnTaker;
		this.controls = controls;
	}
	
	public TurnCommand init() {
		return null;
	}
	
	public Decision getDecision() {
		return null;
	}
	
	public Action resolveActionName(ActionProvider provider, Decision decision) {
		return provider.getAction(decision.getAction());
	}
	
	public ActionResult executeAction(Action action, Object input) {
		ActionResult result = null;
		if (action instanceof ModelAction) {
			ModelAction ma = (ModelAction)action;
			result = ma.execute(turnTaker, input, this.controls);
		} else {
			ModelActionNullData ma = (ModelActionNullData)action;
			result = ma.execute(turnTaker, this.controls);
		}
		return result;
	}
	
	public void onExecute(ActionResult result) {
		
	}
	
	public TurnCommand close(ActionResult result) {
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
		
		PrintStream out = this.controls.getGameControls().getOutStream();
		
		// put root level menu onto stack
		Stack<OptionSet> menuStack = new Stack<OptionSet>();
		menuStack.push(builder.getRootMenu());
		boolean decisionComplete = false;
		
		// print initial text of menu tree
		String initialText = builder.getInitialText();
		if (initialText != null && initialText != "") {
			out.println(initialText);
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
				DecisionBuilderNode nextNode = currentMenu.select(builder.getDecision(), selection, actor, out);
				
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
		PrintStream out = this.controls.getGameControls().getOutStream();
		Option selection = null;
		boolean escape = false;
				
		do {
			// show prompt
			out.println(prompt);
			
			// show options
			Hashtable<String, Option> map = new Hashtable<String, Option>();
			if (options.isEmpty() && emptyListText != null && emptyListText != "") {
				out.println(emptyListText);
			} else {
				int counter = 0;
				for (Option option : options) {
					map.put(String.valueOf(counter), option);
					out.println(String.format("[%d] %s", counter++, option.getTitle()));
				}
			}
			
			// show 'back' option, when applicable
			if (allowEscape) {
				out.println("[b] Back");
			}
			
			// get input, check if valid
			selection = null;
			String input = this.controls.getGameControls().getScanner().nextLine();
			if (input.equalsIgnoreCase(BACK)) {
				escape = allowEscape;
			} else {
				selection = map.get(input);
			}
			
		} while (selection == null && !escape);
		
		return selection;
	}
	
}
