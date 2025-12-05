package org.legoata.execute.provider.action;

import org.legoata.action.Action;
import org.legoata.execute.ControlSet;

/**
 * Resolves a String to the instantiation of an Action.
 */
public interface ActionProvider {

	/**
	 * Instantiate an Action given the requested name.
	 * @param name
	 * @param controls
	 * @return
	 */
	public Action getAction(String name, ControlSet controls);
	
}
