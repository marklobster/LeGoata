package org.legoata.state;

import java.util.UUID;

import org.legoata.map.Coordinates;
import org.legoata.model.LGObject;

/**
 * Tracks LGObjects, whether on a map, belonging to another object, or are independent.
 */
public interface LGObjectTracker {
	/**
	 * Returns the LGObject with the id.
	 * @param id
	 * @return
	 */
	public LGObject getObject(UUID id);
	/**
	 * Stores an LGObject. Neither location nor ownership are assigned.
	 * @param object
	 */
	public void putObject(LGObject object);
	/**
	 * Removes an object from storage, deleting ownership and location associations as well.
	 * @param id
	 */
	public void deleteObject(UUID id);
	/**
	 * Returns the owner of an LGObject.
	 * @param id
	 * @return
	 */
	public LGObject getObjectOwner(UUID id);
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
	public LGObject[] getObjectsOwnedBy(UUID id);
	/**
	 * Returns the Coordinates of an LGObject.
	 * @param id
	 * @return
	 */
	public Coordinates getObjectLocation(UUID id);
	/**
	 * Sets the map location for an LGObject. This removes association to its owner.
	 * @param id
	 * @param coords
	 */
	public void setObjectLocation(UUID id, Coordinates coords);
	/**
	 * Returns all LGObjects at particular coordinates.
	 * @param coords
	 * @return
	 */
	public LGObject[] getObjectsAtLocation(Coordinates coords);
}
