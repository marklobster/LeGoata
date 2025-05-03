package legoata.engine.execute.provider.action;

import java.util.HashMap;
import java.util.Map;

import legoata.engine.action.Action;

public class ActionRegistry implements ActionProvider {

	private Map<String, SingleActionProvider> providerMap = new HashMap<String, SingleActionProvider>();
	
	@Override
	public Action getAction(String name) {
		return providerMap.get(name).constructAction();
	}

	public void registerAction(String name, SingleActionProvider instanceMaker) {
		providerMap.put(name, instanceMaker);
	}
}
