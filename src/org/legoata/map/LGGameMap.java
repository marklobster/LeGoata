package org.legoata.map;

/**
 * A game map composed of rooms at particular locations, where each room 
 * is type T, and you can access the room using its key of type K.
 * @param <T>
 */
public interface LGGameMap<T, K> {
	/**
	 * Returns the room having the specified key.
	 * @param cookeyds
	 * @return
	 */
	public T getRoom(K key);
	/**
	 * Sets the value of a room having the specified key.
	 * @param room
	 * @param key
	 */
	public void setRoom(T room, K key);
	/**
	 * Sets the object with the specified key to a null or default value.
	 * @param key
	 */
	public void deleteRoom(K key);
}
