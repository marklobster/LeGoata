package org.legoata.samples.gofish.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

import org.legoata.model.LGObject;
import org.legoata.samples.gofish.GoFishConstants;
import org.legoata.samples.gofish.asset.Book;
import org.legoata.samples.gofish.asset.Card;
import org.legoata.samples.gofish.asset.Rank;

public abstract class Player extends LGObject {
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Book> books = new ArrayList<Book>();

	public Player(UUID uuid) {
		super(uuid);
	}
	
	public Book[] acceptCard(Card card) {
		return this.acceptCards(new Card[] { card });
	}
	
	public Book[] acceptCards(Card[] cards) {
		// data structure for tracking new books
		Card[][] ranks = new Card[GoFishConstants.RANKS][];
		for (int i = 0; i < ranks.length; i++) {
			ranks[i] = new Card[GoFishConstants.BOOK_SIZE];
		}
		// insert cards into hand
		for (Card card : cards) {
			this.hand.add(card);
		}
		// insert everything in hand into ranks data structure
		for (Card card : this.hand) {
			this.insertCardInPlace(card, ranks[card.getRank().ordinal()]);
		}
		// check for books
		ArrayList<Book> newBooks = new ArrayList<Book>();
		for (Card[] array : ranks) {
			// check if there are 4 cards of that rank
			if (array[GoFishConstants.BOOK_SIZE - 1] != null) {
				// add book to collections
				Book book = new Book(array[0], array[1], array[2], array[3]);
				this.books.add(book);
				newBooks.add(book);
				// remove those cards from hand
				for (int i = 0; i < GoFishConstants.BOOK_SIZE; i++) {
					this.hand.remove(array[i]);
				}
			}
		}
		return newBooks.toArray(new Book[newBooks.size()]);
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
	
	public Book[] getBooksArrayCopy() {
		return (Book[]) this.books.toArray();
	}
	
	public int getNumberBooks() {
		return this.books.size();
	}
	
	public abstract String getName();
	
	public abstract String getCatchphrase();
	
	private void insertCardInPlace(Card card, Card[] place) {
		for (int i = 0; i < GoFishConstants.BOOK_SIZE; i++) {
			if (place[i] == null) {
				place[i] = card;
				break;
			}
		}
	}
	
}
