package legoata.engine.map.location;

import legoata.engine.decision.DecisionBuilder;

public interface SceneNode extends MapNode {

	public DecisionBuilder getDecisionBuilder();
	
}
