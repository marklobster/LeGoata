package legoata.engine.execute.provider.action;

import legoata.engine.action.Action;

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
