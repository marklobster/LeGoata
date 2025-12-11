package org.legoata.map;

public class XYZ extends XY {

	private int z;
	
	public XYZ(int x, int y, int z) {
		super(x, y);
		this.z = z;
	}
	
	public int getZ() {
		return this.z;
	}

}
