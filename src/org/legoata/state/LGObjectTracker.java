package org.legoata.state;

import java.util.UUID;

import org.legoata.model.LGTrackable;

/**
 * Tracks LGObjects, whether on a map, belonging to another object, or are independent.
 */
public interface LGObjectTracker<K> {
	/**
	 * Returns the LGObject with the id.
	 * @param id
	 * @return
	 */
	public LGTrackable getObject(UUID id);
	/**
	 * Stores an LGObject. Neither location nor ownership are assigned.
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
	 * Returns all LGObjects at particular coordinates.
	 * @param locationKey
	 * @return
	 */
	public LGTrackable[] getObjectsAtLocation(K locationKey);
	
	/**
	 * Removes all items at the specified location.
	 * @param locationKey
	 * @return
	 */
	public void clearLocation(K locationKey);
}
