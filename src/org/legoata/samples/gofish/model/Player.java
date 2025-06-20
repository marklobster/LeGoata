package org.legoata.samples.gofish.model;

import java.util.UUID;

import org.legoata.model.LGObject;

public class Player extends LGObject {
	
	private Personality personality;

	public Player(UUID uuid) {
		super(uuid);
	}

	public Personality getPersonality() {
		return personality;
	}

}
