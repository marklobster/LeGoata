package legoata.engine.action;

/**
 * 
 * @author Mark
 *
 */
public enum ActionResultCode {
	/**
	 * The action completed and potentially completed the turn.
	 */
	Consequential,
	/**
	 * The action completed, but it is not an action that normally ends a turn.
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
	 * The user requested to exit the game.
	 */
	ExitRequested
}
