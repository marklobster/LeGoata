package org.legoata.action.decision;

/**
 * A decision which designates a ModelAction or ModelActionNullData.
 * Setting data on this object will result in a ModelAction being called. Otherwise, ModelActionNullData is called.
 */
public class ActionDecision {

	private String actionName;
	
	private Object data;

	/**
	 * Returns the data which will be a parameter for the executing Action.
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data which will be a parameter for the executing Action.
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Gets the name of the registered Action to invoke.
	 * @return
	 */
	public String getAction() {
		return actionName;
	}

	/**
	 * Sets the name of the registered Action to invoke.
	 * @param actionName
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
}
