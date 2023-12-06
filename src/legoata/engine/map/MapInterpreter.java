package legoata.engine.map;

import legoata.engine.map.location.SceneNode;

public interface MapInterpreter<T> {

	public boolean isPath(T mapPoint, int x, int y);
	public boolean isBarrier(T mapPoint, int x, int y);
	public boolean isSceneNode(T mapPoint, int x, int y);
	public SceneNode getSceneNode(T mapPoint, int x, int y);

}
