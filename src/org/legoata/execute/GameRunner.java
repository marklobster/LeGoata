package org.legoata.execute;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.function.LongConsumer;

import org.legoata.LGConstants;
import org.legoata.action.Action;
import org.legoata.action.ActionResult;
import org.legoata.action.decision.ActionDecision;
import org.legoata.config.GameRunnerConfig;
import org.legoata.config.ReadOnlyGameConfig;
import org.legoata.controller.Controller;
import org.legoata.controller.command.ChangeController;
import org.legoata.controller.command.CompleteTurn;
import org.legoata.controller.command.RepeatController;
import org.legoata.controller.command.TurnCommand;
import org.legoata.event.ActionEvent;
import org.legoata.event.ActionEventHandler;
import org.legoata.event.GameCycleEvent;
import org.legoata.event.GameCycleEventHandler;
import org.legoata.event.TimeChangeEvent;
import org.legoata.event.TimeChangeEventHandler;
import org.legoata.execute.provider.action.ActionProvider;
import org.legoata.execute.provider.controller.ControllerProvider;
import org.legoata.model.LGObject;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.UUID;

/**
 * Configure your game, then call the run method to run the game.
 */
public class GameRunner {
	
	private InputStream input = System.in;
	private PrintStream out = System.out;
	
	private GameCycleEventHandler initializer = null;
	private ControllerProvider controllerProvider = null;
	private ActionProvider actionProvider = null;
	
	private GameRunnerConfig settings = new GameRunnerConfig();
	
	private ArrayList<GameCycleEventHandler> preRound = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> initRound = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> preTurn = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> initTurn = new ArrayList<GameCycleEventHandler>();
	private ArrayList<ActionEventHandler> onAction = new ArrayList<ActionEventHandler>();
	private ArrayList<GameCycleEventHandler> postTurn = new ArrayList<GameCycleEventHandler>();
	private ArrayList<GameCycleEventHandler> postRound = new ArrayList<GameCycleEventHandler>();
	private ArrayList<TimeChangeEventHandler> moment = new ArrayList<TimeChangeEventHandler>();
	
	public void setInitializer(GameCycleEventHandler initializer) {
		this.initializer = initializer;
	}
	
	public ControllerProvider getControllerProvider() {
		return controllerProvider;
	}

	public void setControllerProvider(ControllerProvider controllerProvider) {
		this.controllerProvider = controllerProvider;
	}
	
	public ActionProvider getActionProvider() {
		return actionProvider;
	}

	public void setActionProvider(ActionProvider actionProvider) {
		this.actionProvider = actionProvider;
	}
	
	public void addPreRoundEventHandler(GameCycleEventHandler op) {
		this.preRound.add(op);
	}
	
	public void addInitRoundEventHandler(GameCycleEventHandler op) {
		this.initRound.add(op);
	}
	
	public void addPreTurnEventHandler(GameCycleEventHandler op) {
		this.preTurn.add(op);
	}
	
	public void addInitTurnEventHandler(GameCycleEventHandler op) {
		this.initTurn.add(op);
	}
	
	public void addActionEventHandler(ActionEventHandler op) {
		this.onAction.add(op);
	}
	
	public void addPostTurnEventHandler(GameCycleEventHandler op) {
		this.postTurn.add(op);
	}
	
	public void addPostRoundEventHandler(GameCycleEventHandler op) {
		this.postRound.add(op);
	}
	
	public void addMomentEventHandler(TimeChangeEventHandler op) {
		this.moment.add(op);
	}
	
	public GameRunnerConfig getConfig() {
		return this.settings;
	}
	
	/**
	 * Run the game.
	 */
	public void run() {
		
		// set up
		MutableControlSet controls = new MutableControlSet();
		EventHandlerSet eventHandlers = this.initializeEventHandlerSet();
		Clock gameClock = new Clock();
		gameClock.setMoment(0);
		gameClock.setNextIncrement(0); // first increment should be 0
		gameClock.setOnMomentStrike(new LongConsumer() {
			@Override
			public void accept(long moment) {
				fireTimeChangeEvent(moment, controls, eventHandlers);
			}
		});
		Game game = new Game();
		game.setClock(gameClock);
		game.setScanner(new Scanner(this.input));
		game.setOutStream(this.out);
		game.setSettings(new ReadOnlyGameConfig(this.settings));

		controls.setGameControls(new GameControls(game));
		controls.setClockControls(new ClockControls(gameClock));
		controls.setSchedulingControls(new SchedulingControls(eventHandlers));
		
		// run the INIT_GAME event handler
		this.initializer.consume(new GameCycleEvent(Phase.INIT_GAME), controls);
		
		// execute game loop
		while (!game.getExitFlag()) {
			
			executeRound(game, controls, eventHandlers);
		}
	}
	
	private void executeRound(
			Game game,
			MutableControlSet controls,
			EventHandlerSet eventHandlers) {
		
		// PRE_ROUND
		game.setPhase(Phase.PRE_ROUND);
		
		// update clock for PER_ROUND configuration
		if (game.getSettings().isAutoClockTickEnabled()) {
			game.getClock().increment();
		}
		
		fireGameCycleEvent(Phase.PRE_ROUND, controls, eventHandlers);
		
		if (game.getExitFlag()) {
			return;
		}
		
		// INIT_ROUND
		game.setPhase(Phase.INIT_ROUND);
		Round round = new Round();
		game.setRound(round);
		controls.setRoundControls(new RoundControls(round));
		fireGameCycleEvent(Phase.INIT_ROUND, controls, eventHandlers);
		
		if (game.getExitFlag()) {
			return;
		}
		
		// execute turns
		while (!game.getExitFlag() && !round.isComplete() && round.getIndex() < game.getPlayers().size()) {
			
			UUID playerKey = game.getTurnOrder().get(round.getIndex());
			LGObject player = game.getPlayers().get(playerKey);
			executeTurn(player, game, controls, eventHandlers);
			round.incrementIndex();

		}
		
		if (game.getExitFlag()) {
			return;
		}
		
		// POST_ROUND
		game.getRound().complete(); // mark round complete
		game.setPhase(Phase.POST_ROUND);
		fireGameCycleEvent(Phase.POST_ROUND, controls, eventHandlers);
		
		// delete round
		game.setRound(null);
		controls.setRoundControls(null);
	}
	
	private TurnResultCode executeTurn(LGObject player, Game game, MutableControlSet controls, EventHandlerSet eventHandlers) {

		// PRE_TURN
		game.setPhase(Phase.PRE_TURN);
		fireGameCycleEvent(Phase.PRE_TURN, controls, eventHandlers);
		
		if (isTurnCancelled(game)) {
			return TurnResultCode.TurnCancelled;
		}
		
		// INIT_TURN
		game.setPhase(Phase.INIT_TURN);
		Turn turn = new Turn(player);
		game.setTurn(turn);
		controls.setTurnControls(new TurnControls(turn));
		if (game.getSettings().isActionCountingEnabled()) {
			turn.setActionLimit(game.getSettings().getDefaultActionLimit());
		}
		fireGameCycleEvent(Phase.INIT_TURN, controls, eventHandlers);
		
		if (isTurnCancelled(game)) {
			return TurnResultCode.TurnCancelled;
		}

		// ACTION
		game.setPhase(Phase.ACTION);
		
		String ctrlName = LGConstants.DEFAULT_CTRL;
		ArrayList<String> trackedControllerNames = new ArrayList<String>();
		TurnResultCode code = TurnResultCode.TurnInProgress;
		
		do {
			// check for infinite loop changing controllers
			if (trackedControllerNames.contains(ctrlName)) {
				String names = String.join(",", trackedControllerNames);
				throw new IllegalStateException(
					String.format(
						"Infinite loop detected. Controller '%s' already exists in list of controllers visited during this init phase:'%s'.",
						ctrlName,
						names));
			} else {
				trackedControllerNames.add(ctrlName);
			}
			
			// look up controller
			Controller ctrl = this.controllerProvider.getController(ctrlName, controls);
			
			// pre-action phase
			TurnCommand tcmd = ctrl.preActionCommand();
			
			if (isTurnCancelled(game)) {
				code = TurnResultCode.TurnCancelled;
				return code;
			} else if (tcmd instanceof CompleteTurn) {
				code = TurnResultCode.TurnFinished;
				break;
			} else if (tcmd instanceof RepeatController) {
				throw new IllegalStateException("Cannot use RepeatController command during pre-action phase.");
			} else if (tcmd instanceof ChangeController) {
				ChangeController changeCmd = (ChangeController)tcmd;
				ctrlName = changeCmd.getControllerName();
				continue;
			}
			
			// clear tracked names because we are exiting pre-action phase
			trackedControllerNames.clear();
			
			// get decision
			ActionDecision decision = ctrl.getDecision();
			
			// get action
			Action action = ctrl.resolveActionName(actionProvider, decision);
			
			// execute action
			ActionResult actionResult = ctrl.executeAction(action, decision.getData());
			fireActionEvent(
					decision.getAction(),
					decision.getData(),
					actionResult,
					controls,
					eventHandlers);
			
			// post execute
			ctrl.onExecute(actionResult);

			// check if turn is ending
			tcmd = ctrl.postActionCommand(actionResult);
			if (game.getExitFlag()) {
				code = TurnResultCode.TurnCancelled;
			} else if (game.getRound().isComplete() || tcmd instanceof CompleteTurn) {
				code = TurnResultCode.TurnFinished;
			} else if (tcmd instanceof ChangeController) {
				ctrlName = ((ChangeController)tcmd).getControllerName();
			} else if (tcmd == null) {
				ctrlName = LGConstants.DEFAULT_CTRL;
			}
			
		} while (code == TurnResultCode.TurnInProgress);
		
		// POST_TURN
		game.setPhase(Phase.POST_TURN);
		fireGameCycleEvent(Phase.POST_TURN, controls, eventHandlers);

		// delete turn
		game.setTurn(null);
		controls.setTurnControls(null);
		return code;
	}
	
	private EventHandlerSet initializeEventHandlerSet() {
		
		EventHandlerSet eventHandlers = new EventHandlerSet();
		
		eventHandlers.getPermanentPreRoundHandlers().addAll(this.preRound);
		eventHandlers.getPermanentInitRoundHandlers().addAll(this.initRound);
		eventHandlers.getPermanentPreTurnHandlers().addAll(this.preTurn);
		eventHandlers.getPermanentInitTurnHandlers().addAll(this.initTurn);
		eventHandlers.getPermanentActionHandlers().addAll(this.onAction);
		eventHandlers.getPermanentPostTurnHandlers().addAll(this.postTurn);
		eventHandlers.getPermanentPostRoundHandlers().addAll(this.postRound);
		eventHandlers.getPermanentMomentHandlers().addAll(this.moment);
		
		return eventHandlers;
	}
	
	private void fireGameCycleEvent(
			Phase phase,
			ControlSet controls,
			EventHandlerSet eventHandlers) {
		
		ArrayList<GameCycleEventHandler> permanentEventHandlers = null;
		TreeSet<ScheduledEvent<GameCycleEventHandler>> scheduledEventHandlers = null;
		UUID turnTaker = null;
		
		// determine event handlers to use and identity of turnTaker (for some phases)
		switch (phase) {
		case PRE_ROUND:
			permanentEventHandlers = eventHandlers.getPermanentPreRoundHandlers();
			scheduledEventHandlers = eventHandlers.getScheduledPreRoundHandlers();
			break;
		case INIT_ROUND:
			permanentEventHandlers = eventHandlers.getPermanentInitRoundHandlers();
			scheduledEventHandlers = eventHandlers.getScheduledInitRoundHandlers();
			break;
		case PRE_TURN:
			permanentEventHandlers = eventHandlers.getPermanentPreTurnHandlers();
			scheduledEventHandlers = eventHandlers.getScheduledPreTurnHandlers();
			break;
		case INIT_TURN:
			turnTaker = controls.getTurnControls().getTurnTaker().getId();
			permanentEventHandlers = eventHandlers.getPermanentInitTurnHandlers();
			scheduledEventHandlers = eventHandlers.getScheduledInitTurnHandlers();
			break;
		case POST_TURN:
			turnTaker = controls.getTurnControls().getTurnTaker().getId();
			permanentEventHandlers = eventHandlers.getPermanentPostTurnHandlers();
			scheduledEventHandlers = eventHandlers.getScheduledPostTurnHandlers();
			break;
		case POST_ROUND:
			permanentEventHandlers = eventHandlers.getPermanentPostRoundHandlers();
			scheduledEventHandlers = eventHandlers.getScheduledPostRoundHandlers();
			break;
		default:
			return;
		}
		
		GameCycleEvent event = new GameCycleEvent(phase);
		
		// run permanent event handlers
		for (GameCycleEventHandler handler : permanentEventHandlers) {
			handler.consume(event, controls);
		}
		
		// run scheduled event handlers
		long currentMoment = controls.getClockControls().getMoment();
		ArrayList<ScheduledEvent<GameCycleEventHandler>> discards = new ArrayList<ScheduledEvent<GameCycleEventHandler>>();
		for (ScheduledEvent<GameCycleEventHandler> scheduledEvent : scheduledEventHandlers) {
			long scheduledTime = scheduledEvent.getTime();
			if (scheduledTime < currentMoment) {
				// discard past event handlers
				discards.add(scheduledEvent);
			} else if (scheduledTime == currentMoment && scheduledEvent.getObjectId() == turnTaker) {
				// run and then discard scheduled events which correspond to turnTaker, even if it is null
				scheduledEvent.getEventHandler().consume(event, controls);
				discards.add(scheduledEvent);
			} else if (scheduledTime > currentMoment) {
				// stop once we get to future event handlers
				break;
			}
		}
		scheduledEventHandlers.removeAll(discards);
	}
	
	private void fireActionEvent(
			String actionName,
			Object actionData,
			ActionResult result,
			ControlSet controls,
			EventHandlerSet eventHandlers) {
		
		ActionEvent event = new ActionEvent(actionName, actionData, result);
		
		// run permanent event handlers
		for (ActionEventHandler handler : eventHandlers.getPermanentActionHandlers()) {
			handler.consume(event, controls);
		}
		
		// run the scheduled event handlers
		long currentMoment = controls.getClockControls().getMoment();
		UUID turnTaker = controls.getTurnControls().getTurnTaker().getId();
		ArrayList<ScheduledEvent<ActionEventHandler>> discards = new ArrayList<ScheduledEvent<ActionEventHandler>>();
		for (ScheduledEvent<ActionEventHandler> scheduledEvent : eventHandlers.getScheduledActionHandlers()) {
			long scheduledTime = scheduledEvent.getTime();
			if (scheduledTime < currentMoment) {
				// remove any past events which are still in the queue
				discards.add(scheduledEvent);
			} else if (scheduledTime == currentMoment && scheduledEvent.getObjectId() == turnTaker) {
				// run and then discard events for the active player at this moment
				scheduledEvent.getEventHandler().consume(event, controls);
				discards.add(scheduledEvent);
			} else if (scheduledTime > currentMoment) {
				// stop when future events are reached
				break;
			}
		}
		eventHandlers.getScheduledActionHandlers().removeAll(discards);
	}
	
	private void fireTimeChangeEvent(
			long currentMoment,
			ControlSet controls,
			EventHandlerSet eventHandlers) {
		
		TimeChangeEvent event = new TimeChangeEvent(currentMoment);
		
		// run permanent event handlers
		ArrayList<TimeChangeEventHandler> permanentEventHandlers = eventHandlers.getPermanentMomentHandlers();
		for (TimeChangeEventHandler handler : permanentEventHandlers) {
			handler.consume(event, controls);
		}
		
		// run the scheduled event handlers
		TreeSet<ScheduledEvent<TimeChangeEventHandler>> scheduledEventHandlers = eventHandlers.getScheduledMomentHandlers();
		ArrayList<ScheduledEvent<TimeChangeEventHandler>> discards = new ArrayList<ScheduledEvent<TimeChangeEventHandler>>();
		for (ScheduledEvent<TimeChangeEventHandler> scheduledEvent : scheduledEventHandlers) {
			long scheduledTime = scheduledEvent.getTime();
			if (scheduledTime < currentMoment) {
				// remove any past events which are still in the queue
				discards.add(scheduledEvent);
			} else if (scheduledTime == currentMoment) {
				// run and then discard events for this moment
				scheduledEvent.getEventHandler().consume(event, controls);
				discards.add(scheduledEvent);
			} else {
				// stop when future events are reached
				break;
			}
		}
		scheduledEventHandlers.removeAll(discards);
	}
	
	private boolean isTurnCancelled(Game game) {
		return game.getExitFlag() || game.getRound().isComplete();
	}
}
