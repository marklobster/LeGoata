package org.legoata.utils;

import java.util.ArrayList;
import java.util.Random;

public class Utils {
	private static Random rand = new Random();
	
	/**
	 * Returns pseudo-random int between the two values, inclusive.
	 * @param from lower bound
	 * @param to upper bound
	 * @return
	 */
	public static int getRandom(int from, int to) {
		return rand.nextInt(to - from + 1) + from;
	}
	
	/**
	 * Selects a pseudo-random item from the ArrayList.
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> T pickRandom(ArrayList<T> list) {
		int i = rand.nextInt(list.size());
		return list.get(i);
	}
	
	/**
	 * Returns a new ArrayList with the same items but in (pseudo) randomized order. Original 
	 * list is cleared.
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> ArrayList<T> shuffle(ArrayList<T> list) {
		ArrayList<T> shuffled = new ArrayList<T>();
		while (!list.isEmpty()) {
			int index = rand.nextInt(list.size());
			shuffled.add(list.remove(index));
		}
		return shuffled;
	}
}
