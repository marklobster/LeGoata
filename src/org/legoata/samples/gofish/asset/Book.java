package org.legoata.samples.gofish.asset;

public class Book {
	private Card[] cards;
	public Book(Card c0, Card c1, Card c2, Card c3) {
		cards = new Card[] {c0, c1, c2, c3};
	}
	public Rank getRank() {
		return cards[0].getRank();
	}
}
