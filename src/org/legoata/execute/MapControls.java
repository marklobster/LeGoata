package org.legoata.execute;

import java.util.UUID;

import org.legoata.map.LGGameMap;
import org.legoata.map.ObjectLocationInfo;
import org.legoata.map.XY;
import org.legoata.map.XYZ;
import org.legoata.model.LGTrackable;
import org.legoata.state.LGObjectTracker;

/**
 * Controls for manipulating the map as well as placement of objects within the map.
 * @param <R> The type of room in the underlying map
 * @param <K> The type of location key used by the underlying map
 */
public class MapControls<R,K> {
	
	private LGObjectTracker<K> objectTracker;
	private LGGameMap<R,K> map;

	public MapControls(LGObjectTracker<K> objectTracker, LGGameMap<R,K> map) {
		this.objectTracker = objectTracker;
		this.map = map;
	}
	
	/**
	 * Returns the room identified by the x/y coordinates.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return The room identified by the x/y coordinates
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XY
	 */
	public R getLocation(int x, int y) throws ClassCastException {
		return this.map.getRoom((K)(new XY(x, y)));
	}
	
	/**
	 * Returns the room identified by the x/y/z coordinates.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @return The room identified by the x/y/z coordinates
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XYZ
	 */
	public R getLocation(int x, int y, int z) throws ClassCastException {
		return this.map.getRoom((K)(new XYZ(x, y, z)));
	}
	
	/**
	 * Returns the room identified by the location key.
	 * @param locationKey An object of type K
	 * @return The room identified by the location key
	 */
	public R getLocation(K locationKey) {
		return this.map.getRoom(locationKey);
	}
	
	/**
	 * Sets the room at the x/y coordinates.
	 * @param room The new room
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XY
	 */
	public void setLocation(R room, int x, int y) throws ClassCastException {
		this.setLocation(room, (K)(new XY(x, y)));
	}
	
	/**
	 * Sets the room at the x/y/z coordinates.
	 * @param room The new room
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XYZ
	 */
	public void setLocation(R room, int x, int y, int z) throws ClassCastException {
		this.setLocation(room, (K)(new XYZ(x, y, z)));
	}

	/**
	 * Sets the room identified by the location key
	 * @param room The new room
	 * @param locationKey The location key
	 */
	public void setLocation(R room, K locationKey) {
		this.map.setRoom(room, locationKey);
	}
	
	/**
	 * Sets the room at by the x/y coordinates and also sets its contents, deleting whatever was already there, except for any objects 
	 * found in the contents array.
	 * @param room The new room
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param contents The contents for the new room
	 * @throws ClassCastException  when map does not use location key of type org.legoata.map.XY
	 */
	public void setLocation(R room, int x, int y, LGTrackable[] contents) throws ClassCastException {
		this.setLocation(room, (K)(new XY(x, y)));
	}
	
	/**
	 * Sets the room at by the x/y/z coordinates and also sets its contents, deleting whatever was already there, except for any objects 
	 * found in the contents array.
	 * @param room The new room
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @param contents The contents for the new room
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XYZ
	 */
	public void setLocation(R room, int x, int y, int z, LGTrackable[] contents) throws ClassCastException {
		this.setLocation(room, (K)(new XYZ(x, y, z)));
	}

	/**
	 * Sets the room identified by the location key and also sets its contents, deleting whatever was already there, except for any objects 
	 * found in the contents array.
	 * @param room The new room
	 * @param locationKey The location for the new room
	 * @param contents The contents for the new room
	 */
	public void setLocation(R room, K locationKey, LGTrackable[] contents) {
		this.map.setRoom(room, locationKey);
		for (LGTrackable object : contents) {
			this.objectTracker.putObject(object);
		}
		this.objectTracker.setObjectsAtLocation(locationKey, contents);
	}
	
	/**
	 * Puts the object into the specified x/y coordinates. This will also begin tracking of the object if it is not already being tracked.
	 * @param object The object
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XY
	 */
	public void placeObject(LGTrackable object, int x, int y) throws ClassCastException {
		this.placeObject(object, (K)(new XY(x, y)));
	}
	
	/**
	 * Puts the object into the specified x/y/z coordinates. This will also begin tracking of the object if it is not already being tracked.
	 * @param object The object
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XYZ
	 */
	public void placeObject(LGTrackable object, int x, int y, int z) throws ClassCastException {
		this.placeObject(object, (K)(new XYZ(x, y, z)));
	}
	
	/**
	 * Puts the object into the specified location. This will also begin tracking of the object if it is not already being tracked.
	 * @param object The object
	 * @param locationKey The location key
	 */
	public void placeObject(LGTrackable object, K locationKey) {
		LGTrackable trackedObj = this.objectTracker.getObject(object.getId());
		if (trackedObj == null) {
			this.objectTracker.putObject(object);
		}
		this.objectTracker.setObjectLocation(object.getId(), locationKey);
	}
	
	/**
	 * Delete a room at the specified coordinates, settings its value to null or a default value.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param clearContents True to recursively delete all objects in the room, or false to leave them alone.
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XY
	 */
	public void deleteRoom(int x, int y, boolean clearContents) {
		this.deleteRoom((K)(new XY(x, y)), clearContents);
	}
	
	/**
	 * Delete a room at the specified coordinates, settings its value to null or a default value.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @param clearContents True to recursively delete all objects in the room, or false to leave them alone.
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XYZ
	 */
	public void deleteRoom(int x, int y, int z, boolean clearContents) throws ClassCastException {
		this.deleteRoom((K)(new XYZ(x, y, z)), clearContents);
	}
	
	/**
	 * Delete a room at the specified location, settings its value to null or a default value.
	 * @param locationKey The location's key
	 * @param clearContents True to recursively delete all objects in the room, or false to leave them alone.
	 */
	public void deleteRoom(K locationKey, boolean clearContents) {
		if (clearContents) {
			this.objectTracker.clearLocation(locationKey);
		}
		this.map.deleteRoom(locationKey);
	}
	
	/**
	 * Returns all top-level objects at the location. Children of these objects are not included.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return Array of objects
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XY
	 */
	public LGTrackable[] getObjectsAtLocation(int x, int y) throws ClassCastException {
		return this.objectTracker.getObjectsAtLocation((K)(new XY(x, y)));
	}
	
	/**
	 * Returns all top-level objects at the location. Children of these objects are not included.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @return Array of objects
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XYZ
	 */
	public LGTrackable[] getObjectsAtLocation(int x, int y, int z) throws ClassCastException {
		return this.objectTracker.getObjectsAtLocation((K)(new XYZ(x, y, z)));
	}
	
	/**
	 * Returns all top-level objects at the location. Children of these objects are not included.
	 * @param locationKey The key for the location
	 * @return Array of objects
	 */
	public LGTrackable[] getObjectsAtLocation(K locationKey) {
		return this.objectTracker.getObjectsAtLocation(locationKey);
	}
	
	/**
	 * Sets the objects at the specified location, recursively deleting what is already there, unless its 
	 * contained in the objects array parameter.  If objects are not tracked yet, they are put into tracking.
	 * @param x The x coordinate of the location
	 * @param y The y coordinate of the location
	 * @param objects The objects to put in the location
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XY
	 */
	public void setObjectsAtLocation(int x, int y, LGTrackable[] objects) throws ClassCastException {
		this.setObjectsAtLocation((K)(new XY(x, y)), objects);
	}
	
	/**
	 * Sets the objects at the specified location, recursively deleting what is already there, unless its 
	 * contained in the objects array parameter.  If objects are not tracked yet, they are put into tracking.
	 * @param x The x coordinate of the location
	 * @param y The y coordinate of the location
	 * @param z The z coordinate of the location
	 * @param objects The objects to put in the location
	 * @throws ClassCastException when map does not use location key of type org.legoata.map.XYZ
	 */
	public void setObjectsAtLocation(int x, int y, int z, LGTrackable[] objects) throws ClassCastException {
		this.setObjectsAtLocation((K)(new XYZ(x, y, z)), objects);
	}

	/**
	 * Sets the objects at the specified location, recursively deleting what is already there, unless its 
	 * contained in the objects array parameter.  If objects are not tracked yet, they are put into tracking.
	 * @param locationKey The key for the specified location
	 * @param objects The objects to put in the location
	 */
	public void setObjectsAtLocation(K locationKey, LGTrackable[] objects) {
		for (LGTrackable obj : objects) {
			this.objectTracker.putObject(obj);
		}
		this.objectTracker.setObjectsAtLocation(locationKey, objects);
	}
	
	/**
	 * Get information about an object's location, deriving it from the parent hierarchy if needed.
	 * @param objectId
	 * @return
	 */
	public ObjectLocationInfo<K> getObjectLocationInfo(UUID objectId) {
		K locationKey = this.objectTracker.getObjectLocation(objectId);
		LGTrackable immediateParent = this.objectTracker.getObjectOwner(objectId);
		LGTrackable parentAtLocation = null;
		
		// navigate up to find topmost parent
		if (immediateParent != null) {
			LGTrackable current = immediateParent;
			boolean topFound = false;
			while (!topFound) {
				LGTrackable next = this.objectTracker.getObjectOwner(current.getId());
				if (next == null) {
					topFound = true;
					
					// use locationKey of topmost parent
					locationKey = this.objectTracker.getObjectLocation(current.getId());
					
					// only set the parentAtLocation value if that object does in fact have a location
					if (locationKey != null) {
						parentAtLocation = current;
					}
				}
			}
		}

		return new ObjectLocationInfo<K>(
				objectId,
				immediateParent == null ? null : immediateParent.getId(),
				parentAtLocation == null ? null : parentAtLocation.getId(),
				locationKey);
	}

}
