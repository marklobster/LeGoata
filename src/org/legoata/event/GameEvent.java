package org.legoata.event;

import java.time.LocalDateTime;

/**
 * An event fired by the Le Goata framework.
 */
public abstract class GameEvent {
	private LocalDateTime timestamp;
	public GameEvent() {
		this.timestamp = LocalDateTime.now();
	}
	/**
	 * The event's time of instantiation.
	 * @return
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
