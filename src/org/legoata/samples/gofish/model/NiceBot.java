package org.legoata.samples.gofish.model;

import org.legoata.samples.gofish.Keys;

public class NiceBot extends Player {

	public NiceBot() {
		super(Keys.getNiceBotKey());
	}

	@Override
	public String getName() {
		return "Nice Bot";
	}

	@Override
	public String getCatchphrase() {
		return "How fortunate!";
	}

}
