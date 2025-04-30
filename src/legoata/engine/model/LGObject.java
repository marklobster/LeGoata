package legoata.engine.model;

import java.util.UUID;

public class LGObject {
	
	private UUID id;

	public LGObject(UUID uuid) {
		this.id = uuid;
	}
	
	public UUID getId() {
		return this.id;
	}

}
