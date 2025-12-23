package org.legoata.state;

import java.util.UUID;

import org.legoata.model.LGTrackable;

/**
 * Tracks LGObjects, whether on a map, belonging to another object, or independent.
 * 
 * Objects cannot belong to a location and another object.
 * 
 * Performing operations on an object which is not being tracked will throw exceptions. Always track an object by adding it via the puObject method.
 */
public interface LGObjectTracker<K> {
	/**
	 * Returns the tracked object with the id, or null if no such object is being tracked.
	 * @param id
	 * @return
	 */
	public LGTrackable getObject(UUID id);
	/**
	 * Stores an LGTrackable object. Neither location nor ownership are assigned.
	 * If the object's id is already being tracked, the object will be replaced, but location, children, and ownership will not change.
	 * @param object
	 */
	public void putObject(LGTrackable object);
	/**
	 * Removes an object from storage, deleting ownership and location associations as well.
	 * @param id - id of the object to delete
	 * @param recursiveDelete - set true to also recursively delete objects owned by the object
	 */
	public void deleteObject(UUID id, boolean recursiveDelete);
	/**
	 * Returns the owner of an LGObject.
	 * @param id
	 * @return
	 */
	public LGTrackable getObjectOwner(UUID id);
	/**
	 * Sets the owner of the specified LGObject. This removes association to coordinates.
	 * @param objectId
	 * @param ownerId
	 */
	public void setObjectOwner(UUID objectId, UUID ownerId);
	/**
	 * Returns all LGObjects owned by the specified LGObject.
	 * @param id
	 * @return
	 */
	public LGTrackable[] getObjectsOwnedBy(UUID id);
	/**
	 * Returns the Coordinates of an LGObject.
	 * @param id
	 * @return
	 */
	public K getObjectLocation(UUID id);
	/**
	 * Sets the map location for an LGObject. This removes association to its owner.
	 * @param id
	 * @param locationKey
	 */
	public void setObjectLocation(UUID id, K locationKey);
	/**
	 * Returns all LGObjects with a particular location, but not their owned objects.
	 * @param locationKey
	 * @return
	 */
	public LGTrackable[] getObjectsAtLocation(K locationKey);
	/**
	 * Sets the contents of a location, deleting anything which is not found in the objects array.
	 * @param locationKey
	 * @param objects
	 */
	public void setObjectsAtLocation(K locationKey, LGTrackable[] objects);
	/**
	 * Removes all items at the specified location.
	 * @param locationKey
	 * @return
	 */
	public void clearLocation(K locationKey);
	/**
	 * Remove an object's parent and its location.
	 * @param id
	 */
	public void makeOrphan(UUID id);
}
