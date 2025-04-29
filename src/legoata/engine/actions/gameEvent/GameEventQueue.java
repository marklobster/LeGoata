package legoata.engine.actions.gameEvent;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import legoata.engine.game.clock.ReadOnlyClock;

public class GameEventQueue {
	
	private PriorityQueue<MomentWrapper> queue;
	
	private ReadOnlyClock clock;

	public GameEventQueue(ReadOnlyClock clock) {
		this.queue = new PriorityQueue<MomentWrapper>();
		this.clock = clock;
	}
	
	public void queueEvent(GameEvent gameEvent, int momentsFromNow) {
		int calculatedMoment = this.clock.time() + momentsFromNow;
		boolean found = false;
		for (MomentWrapper item : this.queue) {
			int itemMoment = item.getMoment();
			if (itemMoment == calculatedMoment) {
				item.queueGameEvent(gameEvent);
				found = true;
			} else if (itemMoment > calculatedMoment) {
				break;
			}
		}
		if (!found) {
			this.queue.add(new MomentWrapper(gameEvent, calculatedMoment));
		}
	}
	
//	public Queue<GameEvent> getCurrent() {
//		Queue<GameEvent> eventQueue = null;
//		MomentWrapper current = this.queue.peek();
//		if (current != null && current.getMoment() == this.clock.time()) {
//			eventQueue = current.getEvents();
//		}
//		return eventQueue;
//	}
	
	public int getNextEventTime() {
		return this.queue.peek().getMoment();
	}
	
	public Queue<GameEvent> pop() {
		return this.queue.poll().getEvents();
	}
	
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
	
//	public void cycle() {
//		MomentWrapper current = this.queue.peek();
//		if (current != null && current.getMoment() == this.clock.time()) {
//			this.queue.remove();
//		}
//	}
//	
//	public boolean hasEventsForMoment() {
//		boolean hasEvent = false;
//		MomentWrapper momentWrapper = this.queue.peek();
//		return momentWrapper != null && this.clock.time() == momentWrapper.getMoment();
//	}

	private class MomentWrapper implements Comparable<MomentWrapper> {
		
		private Queue<GameEvent> gameEventQueue;
		
		private int moment;
		
		private int getMoment() {
			return this.moment;
		}
		
		private MomentWrapper(GameEvent gameEvent, int moment) {
			this.gameEventQueue = new LinkedList<GameEvent>();
			this.moment = moment;
		}
		
		private void queueGameEvent(GameEvent gameEvent) {
			this.gameEventQueue.add(gameEvent);
		}
		
		private Queue<GameEvent> getEvents() {
			return this.gameEventQueue;
		}
		
		@Override
		public int compareTo(MomentWrapper o) {
			return Integer.compare(this.getMoment(), o.getMoment());
		}
	}
}
