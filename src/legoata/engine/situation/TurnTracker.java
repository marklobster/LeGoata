package legoata.engine.situation;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import legoata.engine.gamecharacter.GameCharacter;

public class TurnTracker {
	
	private ArrayList<CharacterTracker> trackerList = new ArrayList<CharacterTracker>();
	
	public void addGameCharacter(GameCharacter gChar) {
		CharacterTracker tracker = new CharacterTracker(gChar);
		tracker.initialize();
		trackerList.add(tracker);
		Collections.sort(trackerList);
	}
	
	public boolean removeGameCharacter(GameCharacter gChar) {
		CharacterTracker removal = null;
		for (CharacterTracker tracker : trackerList) {
			if (tracker.getGameCharacter() == gChar) {
				removal = tracker;
				break;
			}
		}
		if (trackerList.remove(removal)) {
			Collections.sort(trackerList);
			return true;
		}
		return false;
	}
	
	public GameCharacter getNextUp() {
		CharacterTracker lowest = trackerList.get(0);
		int delayState = lowest.getDelayState();
		if (delayState > 0) {
			for (CharacterTracker ct : trackerList) {
				ct.recover(delayState);
			}
		}
		return lowest.getGameCharacter();
	}
	
	public void payActionCost(GameCharacter gChar, int cost) {
		// find character's tracker
		CharacterTracker target = null;
		for (CharacterTracker tracker : trackerList) {
			if (tracker.getGameCharacter() == gChar) {
				target = tracker;
				break;
			}
		}
		
		// pay cost
		target.payActionCost(cost);
		
		// move to end of list so that they do not line-jump when there is a tie
		trackerList.remove(target);
		trackerList.add(target);
		Collections.sort(trackerList);
	}
	
	public void displayTrackerInfo(PrintStream stream) {
		int i = 0;
		for (CharacterTracker tracker : trackerList) {
			stream.println(String.format("%d %d %s",
				i++,
				tracker.getDelayState(),
				tracker.getGameCharacter().getFullName()
			));
		}
	}

	private class CharacterTracker implements Comparable<CharacterTracker>{

		private final int INITIAL_DELAY = 25;
		
		private int delayState;
		
		private GameCharacter gameChar;
		
		public CharacterTracker(GameCharacter gameChar) {
			this.gameChar = gameChar;
		}
		
		public void initialize() {
			delayState = INITIAL_DELAY - gameChar.getInitiative();
			if (delayState < 0) {
				delayState = 0;
			}
		}
		
		public GameCharacter getGameCharacter() {
			return gameChar;
		}
		
		public int getDelayState() {
			return delayState;
		}
		
		public int payActionCost(int cost) {
			delayState += cost;
			return delayState;
		}
		
		public int recover(int time) {
			delayState -= time;
			if (delayState < 0) {
				delayState = 0;
			}
			return delayState;
		}
		
		@Override
		public int compareTo(CharacterTracker o) {
			// lowest delay state goes first
			if (this.getDelayState() > o.getDelayState()) {
				return 1;
			}
			if (this.getDelayState() < o.getDelayState()) {
				return -1;
			}
			return 0;
		}
	}
}
