package legoata.engine.execute.provider.gameop;

import java.util.HashMap;
import java.util.Map;

import legoata.engine.gameop.GameOp;

public class GameOpRegistry implements GameOpProvider {
	
	private Map<String, SingleGameOpProvider> providerMap = new HashMap<String, SingleGameOpProvider>();

	@Override
	public GameOp getGameOp(String name) {
		return this.providerMap.get(name).constructGameOp();
	}
	
	public GameOpRegistry registerGameOp(String name, SingleGameOpProvider instanceMaker) {
		this.providerMap.put(name, instanceMaker);
		return this;
	}
	
}
