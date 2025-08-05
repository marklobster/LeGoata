package org.legoata.event;

import org.legoata.action.ActionResult;

/**
 * An event that fires when an action is performed.
 */
public class ActionEvent extends GameEvent {
	private String actionName;
	private Object actionData;
	private ActionResult result;
	public ActionEvent(String actionName, Object actionData, ActionResult result) {
		this.actionName = actionName;
		this.actionData = actionData;
		this.result = result;
	}
	/**
	 * Name of action, as determined by player decision.
	 * @return String - action name
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * Data which was input to the action.
	 * @return Object - any data object
	 */
	public Object getActionData() {
		return this.actionData;
	}
	/**
	 * The ActionResult returned when the action was executed.
	 * @return ActionResult
	 */
	public ActionResult getResult() {
		return result;
	}
}
