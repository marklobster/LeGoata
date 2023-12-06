package legoata.engine.game;

import legoata.engine.map.Direction;

public class Navigator {
	
	private Direction direction = Direction.North;

	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void step() {
		
	}
	
}
