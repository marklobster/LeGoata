package org.legoata.event;

import org.legoata.execute.ControlUnit;

/**
 * Event handler for a Game Event.
 * @param <T> extends GameEvent
 */
public interface GameEventHandler<T extends GameEvent> {
	public void consume(T event, ControlUnit controls);
}
