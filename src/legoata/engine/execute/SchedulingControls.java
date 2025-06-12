package legoata.engine.execute;

import java.util.UUID;

import legoata.engine.event.ActionEventHandler;
import legoata.engine.event.GameCycleEventHandler;
import legoata.engine.event.TimeChangeEventHandler;

public class SchedulingControls {
	private EventHandlerSet eventHandlers;
	SchedulingControls(EventHandlerSet eventHandlers) {
		this.eventHandlers = eventHandlers;
	}
	public void schedulePreRoundEvent(long moment, GameCycleEventHandler event) {
		this.eventHandlers.getScheduledPreRoundHandlers().add(new ScheduledEvent<GameCycleEventHandler>(event, moment));
	}
	public void scheduleInitRoundEvent(long moment, GameCycleEventHandler event) {
		this.eventHandlers.getScheduledInitRoundHandlers().add(new ScheduledEvent<GameCycleEventHandler>(event, moment));
	}
	public void schedulePreTurnEvent(long moment, GameCycleEventHandler event) {
		this.eventHandlers.getScheduledPreTurnHandlers().add(new ScheduledEvent<GameCycleEventHandler>(event, moment));
	}
	public void scheduleInitTurnEvent(long moment, UUID turnTaker, GameCycleEventHandler event) {
		this.eventHandlers.getScheduledInitTurnHandlers().add(new ScheduledEvent<GameCycleEventHandler>(event, moment, turnTaker));
	}
	public void scheduleActionEvent(long moment, UUID turnTaker, ActionEventHandler event) {
		this.eventHandlers.getScheduledActionHandlers().add(new ScheduledEvent<ActionEventHandler>(event, moment, turnTaker));
	}
	public void schedulePostTurnEvent(long moment, UUID turnTaker, GameCycleEventHandler event) {
		this.eventHandlers.getScheduledPostTurnHandlers().add(new ScheduledEvent<GameCycleEventHandler>(event, moment, turnTaker));
	}
	public void schedulePostRoundEvent(long moment, GameCycleEventHandler event) {
		this.eventHandlers.getScheduledPostRoundHandlers().add(new ScheduledEvent<GameCycleEventHandler>(event, moment));
	}
	public void scheduleTimeChangeEvent(long moment, TimeChangeEventHandler event) {
		this.eventHandlers.getScheduledMomentHandlers().add(new ScheduledEvent<TimeChangeEventHandler>(event, moment));
	}
}
