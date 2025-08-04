package org.legoata.samples.gofish.model;

import org.legoata.samples.gofish.Keys;

public class MeanBot extends Player {

	public MeanBot() {
		super(Keys.getMeanBotKey());
	}

	@Override
	public String getName() {
		return "Mean Bot";
	}

	@Override
	public String getCatchphrase() {
		return "I'm the best!!";
	}
}
