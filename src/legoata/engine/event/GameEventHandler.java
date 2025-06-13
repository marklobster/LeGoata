package legoata.engine.event;

import legoata.engine.execute.ControlSet;

/**
 * Event handler for a Game Event.
 * @param <T> extends GameEvent
 */
public interface GameEventHandler<T extends GameEvent> {
	public void consume(T event, ControlSet controls);
}
