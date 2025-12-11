package org.legoata.map.builtin;

import org.legoata.map.XY;
import org.legoata.map.LGGameMap;

public class IntBased2DMap implements LGGameMap<Integer, XY> {
	
	private int[][] map;

	public IntBased2DMap(int[][] map) {
		this.map = map;
	}

	@Override
	public Integer getRoom(XY coords) {
		return this.map[coords.getX()][coords.getY()];
	}

	@Override
	public void setRoom(Integer room, XY coords) {
		this.map[coords.getX()][coords.getY()] = room;
	}

	@Override
	public void deleteRoom(XY coords) {
		this.map[coords.getX()][coords.getY()] = 0;
	}

}
