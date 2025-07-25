package org.legoata.samples.gofish.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

import org.legoata.model.LGObject;
import org.legoata.samples.gofish.asset.Book;
import org.legoata.samples.gofish.asset.Card;
import org.legoata.samples.gofish.asset.Rank;

public abstract class Player extends LGObject {
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Book> books = new ArrayList<Book>();

	public Player(UUID uuid) {
		super(uuid);
	}
	
	public void acceptCard(Card card) {
		this.hand.add(card);
	}
	
	public void acceptCards(Card[] cards) {
		for (Card card : cards) {
			this.hand.add(card);
		}
	}

	public Card[] giveAwayCards(Rank rank) {
		LinkedList<Card> list = new LinkedList<Card>();
		for (Card card : hand) {
			if (card.getRank() == rank) {
				list.add(card);
			}
		}
		for (Card card : list) {
			hand.remove(card);
		}
		return (Card[]) list.toArray();
	}
	
	public int getHandSize() {
		return this.hand.size();
	}
	
	public Book[] getBooksAsImmutable() {
		return (Book[]) this.books.toArray();
	}
	
	public abstract String getName();
	
}
