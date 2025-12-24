package org.legoata.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.legoata.model.LGTrackable;

public class InMemoryObjectTracker<K> implements LGObjectTracker<K> {
	
	private Map<UUID, ObjectContainer> objectMap = new HashMap<UUID, ObjectContainer>();
	private Map<UUID, List<UUID>> ownerMap = new HashMap<UUID, List<UUID>>();
	private Map<K, List<UUID>> locationMap = new HashMap<K, List<UUID>>();

	@Override
	public LGTrackable getObject(UUID id) {
		return this.objectMap.get(id).obj;
	}

	@Override
	public void putObject(LGTrackable object) {
		ObjectContainer container = this.objectMap.get(object.getId());
		if (container == null)
			this.objectMap.put(object.getId(), new ObjectContainer(object));
		else
			container.obj = object;
	}

	@Override
	public void deleteObject(UUID id, boolean recursiveDelete) {
		ObjectContainer container = this.objectMap.remove(id);
		if (container == null) {
			return;
		}
		
		this.removeFromOwner(container);
		
		if (recursiveDelete)
			this.recursivelyDeleteChildren(container);
		else
			this.removeChildren(container);
		
		this.removeFromLocation(container);
	}

	@Override
	public LGTrackable getObjectOwner(UUID id) {
		UUID owner = this.objectMap.get(id).owner;
		if (owner != null) {
			return this.objectMap.get(owner).obj;
		}
		return null;
	}

	@Override
	public void setObjectOwner(UUID objectId, UUID ownerId) {
		ObjectContainer container = this.objectMap.get(objectId);
		this.removeFromOwner(container);
		this.removeFromLocation(container);
		List<UUID> siblings = this.ownerMap.get(ownerId);
		if (siblings == null) {
			siblings = new ArrayList<UUID>();
			this.ownerMap.put(ownerId, siblings);
		}
		siblings.add(objectId);
		container.owner = ownerId;
	}

	@Override
	public LGTrackable[] getObjectsOwnedBy(UUID id) {
		// use the ownerMap to track down the owned objects
		List<UUID> idList = this.ownerMap.get(id);
		if (idList != null) {
			LGTrackable[] objects = new LGTrackable[idList.size()];
			for (int i = 0; i < objects.length; i++) {
				objects[i] = this.objectMap.get(idList.get(i)).obj;
			}
			return objects;
		}
		
		// if list not found, see if the id even exists in objectMap
		if (this.objectMap.containsKey(id)) {
			return new LGTrackable[] { }; // return empty array as long as object is being tracked
		}
		
		// if id not even being tracked, return null
		return null;
	}

	@Override
	public K getObjectLocation(UUID id) {
		return this.objectMap.get(id).location;
	}

	@Override
	public void setObjectLocation(UUID id, K locationKey) {
		
		ObjectContainer objectContainer = this.objectMap.get(id);
		
		// an object cannot have an owner and a location
		this.removeFromOwner(objectContainer);
		
		// remove from previous location
		this.removeFromLocation(objectContainer);
		
		if (locationKey != null) {
			// add to new location
			List<UUID> idsAtLocation = this.locationMap.get(locationKey);
			if (idsAtLocation == null) {
				idsAtLocation = new ArrayList<UUID>();
				this.locationMap.put(locationKey, idsAtLocation);
			}
			idsAtLocation.add(id);
		}
		
		objectContainer.location = locationKey;
	}

	@Override
	public LGTrackable[] getObjectsAtLocation(K locationKey) {
		List<UUID> objectIds = this.locationMap.get(locationKey);
		if (objectIds == null) {
			return new LGTrackable[] { };
		}
		LGTrackable[] objects = new LGTrackable[objectIds.size()];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = this.objectMap.get(objectIds.get(i)).obj;
		}
		return objects;
	}
	
	@Override
	public void setObjectsAtLocation(K locationKey, LGTrackable[] objects) {
		// if objects array is empty, just delete contents at the location
		if (objects == null || objects.length == 0) {
			clearLocation(locationKey);
			return;
		}
		
		// remove objects currently at the location which are not in the objects array
		List<UUID> objectIds = this.locationMap.get(locationKey);
		List<UUID> removals = new ArrayList<UUID>();
		HashSet<UUID> idSet = new HashSet<UUID>(objectIds);
		for (UUID id : objectIds) {
			if (!idSet.contains(id)) {
				removals.add(id);
			}
		}
		for (UUID id : removals) {
			deleteObject(id, true);
		}
		
		// add contents of objects array to location
		for (LGTrackable obj : objects) {
			setObjectLocation(obj.getId(), locationKey);
		}
	}
	
	@Override
	public void clearLocation(K locationKey) {
		List<UUID> ids = this.locationMap.remove(locationKey);
		if (ids == null) {
			return;
		}
		
		for (UUID id : ids) {
			ObjectContainer container = this.objectMap.remove(id);
			this.removeFromOwner(container);
			this.recursivelyDeleteChildren(container);
		}
	}
	
	@Override
	public void makeOrphan(UUID id) {
		ObjectContainer container = this.objectMap.get(id);
		this.removeFromLocation(container);
		this.removeFromOwner(container);
	}
	
	public Iterator<LGTrackable> getObjectIterator() {
		Collection<ObjectContainer> values = this.objectMap.values();
		LGTrackable[] array = new LGTrackable[values.size()];
		int i = 0;
		for (ObjectContainer container : values) {
			array[i++] = container.obj;
		}
		return new ObjectIterator<LGTrackable>(array);
	}
	
	private void removeFromOwner(ObjectContainer container) {
		if (container.owner != null) {
			List<UUID> idList = this.ownerMap.get(container.owner);
			if (idList != null) {
				idList.remove(container.obj.getId());
			}
		}
	}
	
	private void removeChildren(ObjectContainer container) {
		List<UUID> idList = this.ownerMap.remove(container.obj.getId());
		if (idList != null) {
			for (UUID ownedItemId : idList) {
				ObjectContainer owned = this.objectMap.get(ownedItemId);
				if (owned != null) {
					owned.owner = null;
				}
			}
		}
	}
	
	private void removeFromLocation(ObjectContainer container) {
		if (container.location != null) {
			List<UUID> idList = this.locationMap.get(container.location);
			if (idList != null) {
				idList.remove(container.obj.getId());
			}
		}
	}
	
	private void recursivelyDeleteChildren(ObjectContainer container) {
		List<UUID> childIds = this.ownerMap.remove(container.obj.getId());
		if (childIds != null) {
			for (UUID childId : childIds) {
				ObjectContainer child = this.objectMap.get(childId);
				this.recursivelyDeleteChildren(child);
			}
		}
		
	}
	
	/**
	 * Contains an LGTrackable, its location, and its owner.
	 */
	private class ObjectContainer {
		private LGTrackable obj;
		private UUID owner;
		private K location;
		private ObjectContainer(LGTrackable obj) {
			this.obj = obj;
		}
	}
	
	private class ObjectIterator<T extends LGTrackable> implements Iterator<T> {
		
		private T[] array;
		int index = 0;
		
		public ObjectIterator(T[] array) {
			this.array = array;
		}

		@Override
		public boolean hasNext() {
			return index < array.length;
		}

		@Override
		public T next() {
			return this.array[index++];
		}
		
	}

}
