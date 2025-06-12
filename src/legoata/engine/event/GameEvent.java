package legoata.engine.event;

import java.time.LocalDateTime;

public abstract class GameEvent {
	private LocalDateTime timeStamp;
	public GameEvent() {
		this.timeStamp = LocalDateTime.now();
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
}
