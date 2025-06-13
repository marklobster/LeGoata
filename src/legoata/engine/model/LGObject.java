package legoata.engine.model;

import java.util.UUID;

/**
 * The base class for players and other game objects.
 */
public class LGObject {
	
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

}
