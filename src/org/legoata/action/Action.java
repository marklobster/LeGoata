package org.legoata.action;

import org.legoata.execute.ClockControls;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameControls;
import org.legoata.execute.RoundControls;
import org.legoata.execute.SchedulingControls;
import org.legoata.execute.TurnControls;

/**
 * An action to be performed by a player during a turn.
 */
public abstract class Action {
	
	private ControlSet controls;
	
	public Action(ControlSet controls) {
		this.controls = controls;
	}

	/**
	 * Returns an ActionResult with Code Consequential.
	 * @return ActionResult
	 */
	protected ActionResult actionCompletedWithConsequence() {
		return new ActionResult(ActionResultCode.Consequential);
	}
	
	/**
	 * Returns an ActionResult with Code Inconsequential.
	 * @return ActionResult
	 */
	protected ActionResult actionCompletedWithoutConsequence() {
		return new ActionResult(ActionResultCode.Inconsequential);
	}

	/**
	 * Returns an ActionResult with Code Incomplete.
	 * @return ActionResult
	 */
	protected ActionResult actionCancelled() {
		return new ActionResult(ActionResultCode.Incomplete);
	}
	
	/**
	 * Returns an ActionResult with Code Error.
	 * @return ActionResult
	 */
	protected ActionResult actionError() {
		return new ActionResult(ActionResultCode.Error);
	}
	
	/**
	 * Returns an ActionResult with Code ExitRequested.
	 * @return ActionResult
	 */
	protected ActionResult exitGame() {
		return new ActionResult(ActionResultCode.ExitRequested);
	}
	
	/**
	 * The whole set of controls.
	 * @return ControlSet
	 */
	protected ControlSet getControls() {
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
