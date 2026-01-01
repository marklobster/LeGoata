package org.legoata.action;

import org.legoata.execute.ControlUnit;

/**
 * An Action with a data parameter done by a player.
 * @param <T>
 */
public abstract class ModelAction<T> extends Action {
	
	public ModelAction(ControlUnit controls) {
		super(controls);
	}

	/**
	 * Execute the action.
	 * @param input
	 * @return
	 */
	public abstract ActionResult execute(T input);
	
}
