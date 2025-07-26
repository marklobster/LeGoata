package org.legoata.samples.gofish.model;

import org.legoata.model.LGObject;
import org.legoata.samples.gofish.Keys;
import org.legoata.samples.gofish.asset.Deck;

public class GoFishGame extends LGObject {
	
	private Deck deck = new Deck();

	public GoFishGame() {
		super(Keys.getGameKey());
	}

	public Deck getDeck() {
		return deck;
	}

}
