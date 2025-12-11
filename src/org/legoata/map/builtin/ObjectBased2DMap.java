package org.legoata.map.builtin;

import org.legoata.map.LGGameMap;
import org.legoata.map.XY;

public class ObjectBased2DMap<T> implements LGGameMap<T, XY> {
	
	private T[][] map;

	public ObjectBased2DMap(T[][] map) {
		this.map = map;
	}

	@Override
	public T getRoom(XY coords) {
		return this.map[coords.getX()][coords.getY()];
	}

	@Override
	public void setRoom(T room, XY coords) {
		this.map[coords.getX()][coords.getY()] = room;
	}

	@Override
	public void deleteRoom(XY coords) {
		this.map[coords.getX()][coords.getY()] = null;
	}

}
