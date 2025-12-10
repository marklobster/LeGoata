package org.legoata.map;

public class Coordinates3D extends Coordinates {
	
	private int z;

	public Coordinates3D(int x, int y, int z) {
		super(x, y);
		this.z = z;
	}
	
	public int getZ() {
		return this.z;
	}

}
