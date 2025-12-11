package org.legoata.map.builtin;

import org.legoata.map.LGGameMap;
import org.legoata.map.XYZ;

public class ObjectBased3DMap <T> implements LGGameMap<T, XYZ> {
	
	private T[][][] map;

	public ObjectBased3DMap(T[][][] map) {
		this.map = map;
	}

	@Override
	public T getRoom(XYZ coords) {
		return this.map[coords.getX()][coords.getY()][coords.getZ()];
	}

	@Override
	public void setRoom(T room, XYZ coords) {
		this.map[coords.getX()][coords.getY()][coords.getZ()] = room;
	}

	@Override
	public void deleteRoom(XYZ coords) {
		this.map[coords.getX()][coords.getY()][coords.getZ()] = null;
	}

}
