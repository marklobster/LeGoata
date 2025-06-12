package legoata.engine.event;

import legoata.engine.action.Action;
import legoata.engine.action.ActionResult;

public class ActionEvent extends GameEvent {
	private String actionName;
	private Action action;
	private ActionResult result;
	public ActionEvent(String actionName, Action action, ActionResult result) {
		this.actionName = actionName;
		this.action = action;
		this.result = result;
	}
	public String getActionName() {
		return actionName;
	}
	public Action getAction() {
		return action;
	}
	public ActionResult getResult() {
		return result;
	}
}
