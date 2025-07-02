package org.legoata.samples.gofish.model;

import java.util.ArrayList;

import org.legoata.utils.Utils;

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
		return cards.remove(0);
	}
	
	public void shuffle() {
		this.cards = Utils.shuffle(this.cards);
	}
}
