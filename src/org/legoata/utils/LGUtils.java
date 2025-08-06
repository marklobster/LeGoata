package org.legoata.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LGUtils {
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
	public static <T> T pickRandom(List<T> list) {
		int i = rand.nextInt(list.size());
		return list.get(i);
	}
	
	/**
	 * Shuffles an ArrayList into (pseudo) randomized order. Original list is returned.
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> List<T> shuffle(List<T> list) {
		ArrayList<T> shuffled = new ArrayList<T>();
		while (!list.isEmpty()) {
			int index = rand.nextInt(list.size());
			shuffled.add(list.remove(index));
		}
		Collections.copy(list, shuffled);
		return list;
	}
}
