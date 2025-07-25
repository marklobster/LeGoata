package org.legoata.samples.gofish.model;

import org.legoata.samples.gofish.Keys;

public class User extends Player {
	
	private String name;

	public User(String name) {
		super(Keys.getUserKey());
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
