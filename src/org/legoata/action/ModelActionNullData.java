package org.legoata.action;

import org.legoata.execute.ControlSet;

/**
 * An Action done by a player without a data parameter.
 */
public abstract class ModelActionNullData extends Action {

	public ModelActionNullData(ControlSet controls) {
		super(controls);
	}

	/**
	 * Execute the action.
	 * @return
	 */
	public abstract ActionResult execute();
	
}
