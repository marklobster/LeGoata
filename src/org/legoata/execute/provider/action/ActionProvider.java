package org.legoata.execute.provider.action;

import org.legoata.action.Action;

/**
 * Resolves a String to the instantiation of an Action.
 */
public interface ActionProvider {

	/**
	 * Instantiate an Action given the requested name.
	 * @param name
	 * @return
	 */
	public Action getAction(String name);
	
}
