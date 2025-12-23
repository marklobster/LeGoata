package org.legoata.map;

/**
 * A game map composed of rooms at particular locations, where each room 
 * is type R, and you can access the room using its key of type K.
 * @param <R The type of room at each location
 * @param <K> The type of key used to retrieve a room
 */
public interface LGGameMap<R, K> {
	/**
	 * Returns the room having the specified key.
	 * @param cookeyds
	 * @return
	 */
	public R getRoom(K key);
	/**
	 * Sets the value of a room having the specified key.
	 * @param room
	 * @param key
	 */
	public void setRoom(R room, K key);
	/**
	 * Sets the object with the specified key to a null or default value.
	 * @param key
	 */
	public void deleteRoom(K key);
}
