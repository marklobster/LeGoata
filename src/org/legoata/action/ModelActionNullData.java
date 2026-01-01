package org.legoata.action;

import org.legoata.execute.ControlUnit;

/**
 * An Action done by a player without a data parameter.
 */
public abstract class ModelActionNullData extends Action {

	public ModelActionNullData(ControlUnit controls) {
		super(controls);
	}

	/**
	 * Execute the action.
	 * @return
	 */
	public abstract ActionResult execute();
	
}
