package legoata.engine.event;

import legoata.engine.execute.ControlSet;

public interface GameEventHandler<T extends GameEvent> {
	public void consume(T event, ControlSet controls);
}
