package org.legoata.action;

/**
 * The result of an action's execution.
 * @author Mark
 *
 */
public enum ActionResultCode {
	/**
	 * The action completed and the player progresses toward the end of their turn.
	 */
	Consequential,
	/**
	 * The action completed, but it is not an action that normally progresses a turn.
	 */
	Inconsequential,
	/**
	 * The action could not be completed.
	 */
	Incomplete,
	/**
	 * An exception occurred when executing the action.
	 */
	Error,
	/**
	 * The player requested to exit the game.
	 */
	ExitRequested
}
