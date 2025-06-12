package legoata.engine.execute;

import java.util.UUID;

import legoata.engine.event.GameEventHandler;

class ScheduledEvent<T extends GameEventHandler<?>> implements Comparable<ScheduledEvent<T>> {
	private T eventHandler;
	private long time;
	private UUID objectId = null;
	ScheduledEvent(T eventHandler, long time) {
		this.eventHandler = eventHandler;
		this.time = time;
	}
	ScheduledEvent(T eventHandler, long time, UUID objectId) {
		this(eventHandler, time);
		this.objectId = objectId;
	}
	T getEventHandler() {
		return eventHandler;
	}
	long getTime() {
		return time;
	}
	UUID getObjectId() {
		return objectId;
	}
	@Override
	public int compareTo(ScheduledEvent<T> o) {
		return Long.compare(time, o.getTime());
	}
}
