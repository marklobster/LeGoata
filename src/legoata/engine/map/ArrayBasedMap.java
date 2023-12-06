package legoata.engine.map;

public class ArrayBasedMap<T> implements GameMap<T> {
	
	private T[][] objectArray = null;
	
	public ArrayBasedMap() {
	}

	public ArrayBasedMap(T[][] objectArray) {
		this.objectArray = objectArray;
	}
	
	public void initialize(T[][] objectArray) {
		this.objectArray = objectArray;
	}

	@Override
	public T getSpace(int x, int y) {
		return objectArray[x][y];
	}

}
