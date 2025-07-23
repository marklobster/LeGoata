package org.legoata.samples.gofish.util;

import org.legoata.samples.gofish.asset.Rank;
import org.legoata.samples.gofish.asset.Suit;

public class GoFishUtils {
	public static String getString(Rank rank) {
		switch (rank) {
		case KING:
			return "King";
		case QUEEN:
			return "Queen";
		case JACK:
			return "Jack";
		case TEN:
			return "Ten";
		case NINE:
			return "Nine";
		case EIGHT:
			return "Eight";
		case SEVEN:
			return "Seven";
		case SIX:
			return "Six";
		case FIVE:
			return "Five";
		case FOUR:
			return "Four";
		case THREE:
			return "Three";
		case TWO:
			return "Two";
		default:
			return "Ace";
		}
	}
	public static String getPluralString(Rank rank) {
		return getString(rank) + "s";
	}
	public static String getString(Suit suit) {
		switch (suit) {
		case CLUB:
			return "Clubs";
		case DIAMOND:
			return "Diamonds";
		case HEART:
			return "Hearts";
		default:
			return "Spades";
		}
	}
}
