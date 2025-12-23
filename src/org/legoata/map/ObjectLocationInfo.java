package org.legoata.map;

import java.util.UUID;

public class ObjectLocationInfo<K> {
	
	private UUID objectId;
	private UUID immediateParent;
	private UUID parentAtLocation;
	private K locationKey;

	public ObjectLocationInfo(UUID objectId, UUID immediateParent, UUID parentAtLocation, K locationKey) {
		this.objectId = objectId;
		this.immediateParent = immediateParent;
		this.parentAtLocation = parentAtLocation;
		this.locationKey = locationKey;
	}

	public UUID getObjectId() {
		return objectId;
	}

	public UUID getImmediateParent() {
		return immediateParent;
	}

	public UUID getParentAtLocation() {
		return parentAtLocation;
	}

	public K getLocationKey() {
		return locationKey;
	}
	
	public boolean isLocationInherited() {
		return parentAtLocation != null;
	}

}
