package org.legoata.samples.gofish.asset;

import java.util.ArrayList;

import org.legoata.utils.LGUtils;

public class Deck {
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	public Deck() {
		this.reset();
	}
	
	public void reset() {
		this.cards.clear();
		Rank[] allRanks = new Rank[] {
				Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX,
				Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN,
				Rank.KING};
		for (Rank rank : allRanks) {
			for (Suit suit : new Suit[] {Suit.CLUB, Suit.DIAMOND, Suit.HEART, Suit.SPADE}) {
				cards.add(new Card(suit, rank));
			}
		}
	}
	
	public int count() {
		return cards.size();
	}
	
	public Card draw() {
		return cards.size() > 0 ? cards.remove(0) : null;
	}
	
	public void shuffle() {
		this.cards = (ArrayList<Card>)LGUtils.shuffle(this.cards);
	}
}
