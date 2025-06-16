package org.legoata.execute;

import java.util.ArrayList;
import java.util.TreeSet;

import org.legoata.event.ActionEventHandler;
import org.legoata.event.GameCycleEventHandler;
import org.legoata.event.TimeChangeEventHandler;

/**
 * Contains every active Game Event Handler.
 */
class EventHandlerSet {
	
	private ArrayList<GameCycleEventHandler> preRound = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> initRound = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> preTurn = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> initTurn = new ArrayList<GameCycleEventHandler>();
	private ArrayList<ActionEventHandler> action = new ArrayList<ActionEventHandler>();
	private ArrayList<GameCycleEventHandler> postTurn = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> postRound = new ArrayList<GameCycleEventHandler>();
	private ArrayList<TimeChangeEventHandler> moment = new ArrayList<TimeChangeEventHandler>();
	
	private TreeSet<ScheduledEvent<GameCycleEventHandler>> scheduledPreRound = new TreeSet<ScheduledEvent<GameCycleEventHandler>>();
	private TreeSet<ScheduledEvent<GameCycleEventHandler>> scheduledInitRound = new TreeSet<ScheduledEvent<GameCycleEventHandler>>();
	private TreeSet<ScheduledEvent<GameCycleEventHandler>> scheduledPreTurn = new TreeSet<ScheduledEvent<GameCycleEventHandler>>();
	private TreeSet<ScheduledEvent<GameCycleEventHandler>> scheduledInitTurn = new TreeSet<ScheduledEvent<GameCycleEventHandler>>();
	private TreeSet<ScheduledEvent<ActionEventHandler>> scheduledAction = new TreeSet<ScheduledEvent<ActionEventHandler>>();
	private TreeSet<ScheduledEvent<GameCycleEventHandler>> scheduledPostTurn = new TreeSet<ScheduledEvent<GameCycleEventHandler>>();
	private TreeSet<ScheduledEvent<GameCycleEventHandler>> scheduledPostRound = new TreeSet<ScheduledEvent<GameCycleEventHandler>>();
	private TreeSet<ScheduledEvent<TimeChangeEventHandler>> scheduledMoment = new TreeSet<ScheduledEvent<TimeChangeEventHandler>>();
	
	ArrayList<GameCycleEventHandler> getPermanentPreRoundHandlers() {
		return preRound;
	}
	
	ArrayList<GameCycleEventHandler> getPermanentInitRoundHandlers() {
		return initRound;
	}
	
	ArrayList<GameCycleEventHandler> getPermanentPreTurnHandlers() {
		return preTurn;
	}
	
	ArrayList<GameCycleEventHandler> getPermanentInitTurnHandlers() {
		return initTurn;
	}
	
	ArrayList<ActionEventHandler> getPermanentActionHandlers() {
		return action;
	}
	
	ArrayList<GameCycleEventHandler> getPermanentPostTurnHandlers() {
		return postTurn;
	}
	
	ArrayList<GameCycleEventHandler> getPermanentPostRoundHandlers() {
		return postRound;
	}
	
	ArrayList<TimeChangeEventHandler> getPermanentMomentHandlers() {
		return moment;
	}
	
	TreeSet<ScheduledEvent<GameCycleEventHandler>> getScheduledPreRoundHandlers() {
		return scheduledPreRound;
	}
	
	TreeSet<ScheduledEvent<GameCycleEventHandler>> getScheduledInitRoundHandlers() {
		return scheduledInitRound;
	}
	
	TreeSet<ScheduledEvent<GameCycleEventHandler>> getScheduledPreTurnHandlers() {
		return scheduledPreTurn;
	}
	
	TreeSet<ScheduledEvent<GameCycleEventHandler>> getScheduledInitTurnHandlers() {
		return scheduledInitTurn;
	}
	
	TreeSet<ScheduledEvent<ActionEventHandler>> getScheduledActionHandlers() {
		return scheduledAction;
	}
	
	TreeSet<ScheduledEvent<GameCycleEventHandler>> getScheduledPostTurnHandlers() {
		return scheduledPostTurn;
	}
	
	TreeSet<ScheduledEvent<GameCycleEventHandler>> getScheduledPostRoundHandlers() {
		return scheduledPostRound;
	}
	
	TreeSet<ScheduledEvent<TimeChangeEventHandler>> getScheduledMomentHandlers() {
		return scheduledMoment;
	}
	
}
