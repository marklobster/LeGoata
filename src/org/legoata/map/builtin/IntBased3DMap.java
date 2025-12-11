package org.legoata.map.builtin;

import org.legoata.map.LGGameMap;
import org.legoata.map.XYZ;

public class IntBased3DMap implements LGGameMap<Integer, XYZ> {
	
	private int[][][] map;

	public IntBased3DMap(int[][][] map) {
		this.map = map;
	}

	@Override
	public Integer getRoom(XYZ coords) {
		return this.map[coords.getX()][coords.getY()][coords.getZ()];
	}

	@Override
	public void setRoom(Integer room, XYZ coords) {
		this.map[coords.getX()][coords.getY()][coords.getZ()] = room;
	}

	@Override
	public void deleteRoom(XYZ coords) {
		this.map[coords.getX()][coords.getY()][coords.getZ()] = 0;
	}

}
