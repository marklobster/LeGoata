package legoata.engine.map.location;

import legoata.engine.decision.DecisionBuilder;

public abstract class Scene extends Location implements SceneNode {

	public Scene(boolean isPath, boolean isBarrier) {
		super(isPath, isBarrier);
	}

	@Override
	public abstract DecisionBuilder getDecisionBuilder();

}
