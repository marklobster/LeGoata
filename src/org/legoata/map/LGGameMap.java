package org.legoata.map;

/**
 * A game map composed of rooms at particular locations, where each 
 * room is type T.
 * @param <T>
 */
public interface LGGameMap<T> {
	/**
	 * Returns the room at the specified coordinates.
	 * @param coords
	 * @return
	 */
	public T getRoom(Coordinates coords);
	/**
	 * Sets the value of a room at the specified coordinates.
	 * @param room
	 * @param coords
	 */
	public void setRoom(T room, Coordinates coords);
	/**
	 * Sets the object at the specified coordinates to a null or default value.
	 * @param coords
	 */
	public void deleteRoom(Coordinates coords);
}
