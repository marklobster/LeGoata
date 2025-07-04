package org.legoata.execute.provider.action;

import java.util.HashMap;
import java.util.Map;

import org.legoata.action.Action;

/**
 * Default implementation of ActionProvoider.  Register a SingleActionProvider for each Action you want to 
 * instantiate at runtime.
 */
public class ActionRegistry implements ActionProvider {

	private Map<String, SingleActionProvider> providerMap = new HashMap<String, SingleActionProvider>();
	
	@Override
	public Action getAction(String name) {
		return providerMap.get(name).constructAction();
	}

	/**
	 * Add a SingleActionProvider to the registry and give it a unique identifier.
	 * @param name
	 * @param instanceMaker
	 */
	public void registerAction(String name, SingleActionProvider instanceMaker) {
		providerMap.put(name, instanceMaker);
	}
}
