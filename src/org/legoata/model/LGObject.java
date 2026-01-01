package org.legoata.model;

import java.util.UUID;

import org.legoata.tracking.LGTrackable;

/**
 * The base class for players and other game objects.
 */
public class LGObject implements LGTrackable {
	
	private UUID id;

	public LGObject(UUID uuid) {
		this.id = uuid;
	}
	
	/**
	 * A unique ID for the object.
	 * @return
	 */
	public UUID getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof LGObject) {
			LGObject other = (LGObject)obj;
			return this.id.equals(other.getId());
		}
		return false;
	}

}
