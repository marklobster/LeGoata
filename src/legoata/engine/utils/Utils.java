package legoata.engine.utils;

import java.util.ArrayList;
import java.util.Random;

public class Utils {
	private static Random rand = new Random();
	
	public static int getRandom(int from, int to) {
		return rand.nextInt(to - from + 1) + from;
	}
	
	public static <T> T pickRandom(ArrayList<T> list) {
		int i = rand.nextInt(list.size());
		return list.get(i);
	}
}
