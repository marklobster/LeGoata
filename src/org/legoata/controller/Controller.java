package org.legoata.controller;

import org.legoata.action.Action;
import org.legoata.action.ActionResult;
import org.legoata.action.ModelAction;
import org.legoata.action.ModelActionNullData;
import org.legoata.action.decision.ActionDecision;
import org.legoata.config.LGConfig;
import org.legoata.controller.command.CompleteTurn;
import org.legoata.controller.command.RepeatController;
import org.legoata.controller.command.TurnCommand;
import org.legoata.execute.ClockControls;
import org.legoata.execute.ControlUnit;
import org.legoata.execute.GameControls;
import org.legoata.execute.RoundControls;
import org.legoata.execute.SchedulingControls;
import org.legoata.execute.TurnControls;
import org.legoata.execute.provider.action.ActionProvider;

/**
 * Class for handling a given situation for a player, or designating a different controller for the job.
 */
public abstract class Controller {
	
	private ControlUnit controls;

	public Controller(ControlUnit controls) {
		this.controls = controls;
	}
	
	/**
	 * Return a command to forego this controller, or null to continue with it.  Returning a RepeatController 
	 * command at this point will result in an exception.
	 * @return TurnCommand
	 */
	public TurnCommand preActionCommand() {
		return null;
	}
	
	/**
	 * Instantiate a Decision for the player.
	 * @return
	 */
	public ActionDecision getDecision() {
		return null;
	}
	
	/**
	 * Instantiate the Action.  It is very unlikely that this function will need to be overridden.
	 * @param provider
	 * @param decision
	 * @return
	 */
	public Action resolveActionName(ActionProvider provider, ActionDecision decision) {
		return provider.getAction(decision.getAction(), this.getControls());
	}
	
	/**
	 * Execute the action, using the appropriate executor.  It is not likely this function will need overridden.
	 * @param action
	 * @param input
	 * @return
	 */
	public ActionResult executeAction(Action action, Object input) {
		ActionResult result = null;
		if (action instanceof ModelAction) {
			ModelAction ma = (ModelAction)action;
			result = ma.execute(input);
		} else {
			ModelActionNullData ma = (ModelActionNullData)action;
			result = ma.execute();
		}
		return result;
	}
	
	/**
	 * Perform any post-action upkeep for this controller.
	 * @param result
	 */
	public void onExecute(ActionResult result) {
		
	}
	
	/**
	 * Translate the ActionResult to a TurnCommand, thereby either finalizing the turn, or continuing it.
	 * By default, this calls the corresponding method for each type of action result: onNullResult, 
	 * onConsequentialResult, onInconsequentialResult, onIncompleteResult, onErrorResult, or onExitGameResult.
	 * @param result - ActionResult
	 * @return TurnCommand
	 */
	public TurnCommand postActionCommand(ActionResult result) {
		if (result == null) {
			return this.onNullResult();
		}
		switch (result.getCode()) {
			case Consequential:
				return this.onConsequentialResult(result);
			case Inconsequential:
				return this.onInconsequentialResult(result);
			case Incomplete:
				return this.onIncompleteResult(result);
			case Error:
				return this.onErrorResult(result);
			default:
				return this.onExitGameResult(result);
		}
	}
	
	/**
	 * Translates Consequential ActionResult to a TurnCommand.
	 * Default behavior:
	 * A) If action counting is enabled: Action count is incremented. If action limit 
	 * is reached, CompleteTurn is returned. Otherwise, RepeatController is returned.
	 * B) If action counting is disabled, CompleteTurn is returned.
	 * @param result - ActionResult
	 * @return TurnCommand
	 */
	protected TurnCommand onConsequentialResult(ActionResult result) {
		LGConfig settings = this.getGameControls().getSettings();
		if (settings.isActionCountingEnabled()) {
			TurnControls turnControls = this.getTurnControls();
			turnControls.setActionCount(turnControls.getActionCount() + 1);
			if (turnControls.getActionCount() < turnControls.getActionLimit()) {
				return new RepeatController();
			}
		}
		return new CompleteTurn();
	}
	
	/**
	 * Translates Inconsequential ActionResult to a TurnCommand.
	 * Default behavior: Returns RepeatController.
	 * @param result - ActionResult
	 * @return TurnCommand
	 */
	protected TurnCommand onInconsequentialResult(ActionResult result) {
		return new RepeatController();
	}
	
	/**
	 * Translates Incomplete ActionResult to a TurnCommand.
	 * Default behavior: Returns RepeatController.
	 * @param result - ActionResult
	 * @return TurnCommand
	 */
	protected TurnCommand onIncompleteResult(ActionResult result) {
		return new RepeatController();
	}
	
	/**
	 * Translates Error ActionResult to a TurnCommand.
	 * Default behavior: Sets the exit flag, to exit the game.
	 * @param result - ActionResult
	 * @return TurnCommand
	 */
	protected TurnCommand onErrorResult(ActionResult result) {
		this.getGameControls().setExitFlag(true);
		return new CompleteTurn();
	}
	
	/**
	 * Translates ExitGame ActionResult to a TurnCommand.
	 * Default behavior: Sets the exit flag, to exit the game.
	 * @param result - ActionResult
	 * @return TurnCommand
	 */
	protected TurnCommand onExitGameResult(ActionResult result) {
		this.getGameControls().setExitFlag(true);
		return new CompleteTurn();
	}
	
	/**
	 * Handles a null ActionResult returned by the execution of an Action.
	 * Default behavior: Returns null, which causes the GameRunner to cycle back to the 
	 * DefaultController.
	 * @return TurnCommand
	 */
	protected TurnCommand onNullResult() {
		return null;
	}
	
	/**
	 * The whole set of controls.
	 * @return ControlSet
	 */
	protected ControlUnit getControls() {
		return this.controls;
	}
	
	/**
	 * Reference to the GameControls.
	 * @return GameControls
	 */
	protected GameControls getGameControls() {
		return this.controls.getGameControls();
	}
	
	/**
	 * Reference to the ClockControls.
	 * @return ClockControls
	 */
	protected ClockControls getClockControls() {
		return this.controls.getClockControls();
	}
	
	/**
	 * Reference to the SchedulingControls.
	 * @return SchedulingControls
	 */
	protected SchedulingControls getSchedulingControls() {
		return this.controls.getSchedulingControls();
	}
	
	/**
	 * Reference to the RoundControls.
	 * @return RoundControls
	 */
	protected RoundControls getRoundControls() {
		return this.controls.getRoundControls();
	}
	
	/**
	 * Reference to the TurnControls.
	 * @return TurnControls
	 */
	protected TurnControls getTurnControls() {
		return this.controls.getTurnControls();
	}
	
}
