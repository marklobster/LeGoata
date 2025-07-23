package org.legoata.samples.gofish.model;

import org.legoata.model.LGObject;
import org.legoata.samples.gofish.Keys;
import org.legoata.samples.gofish.asset.Deck;

public class GoFishGame extends LGObject {
	
	public final int INITIAL_HAND_SIZE = 5;
	
	private Deck deck = new Deck();

	public GoFishGame() {
		super(Keys.getGameKey());
	}

	public Deck getDeck() {
		return deck;
	}

}
