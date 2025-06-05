package legoata.engine.execute;

import java.util.ArrayList;
import java.util.Scanner;

import legoata.engine.Constants;
import legoata.engine.action.Action;
import legoata.engine.action.ActionResult;
import legoata.engine.controller.Controller;
import legoata.engine.controller.command.ChangeController;
import legoata.engine.controller.command.CompleteTurn;
import legoata.engine.controller.command.Interrupt;
import legoata.engine.controller.command.RepeatController;
import legoata.engine.controller.command.TurnCommand;
import legoata.engine.decision.Decision;
import legoata.engine.execute.provider.action.ActionProvider;
import legoata.engine.execute.provider.controller.ControllerProvider;
import legoata.engine.gameop.GameOp;
import legoata.engine.model.LGObject;

public class GameRunner {
	
	private GameOp initializer = null;
	private ControllerProvider controllerProvider = null;
	private ActionProvider actionProvider = null;
	private ArrayList<GameOp> preRound = new ArrayList<GameOp>();
	private ArrayList<GameOp> initRound = new ArrayList<GameOp>();
	private ArrayList<GameOp> preTurn = new ArrayList<GameOp>();
	private ArrayList<GameOp> initTurn = new ArrayList<GameOp>();
	private ArrayList<GameOp> onAction = new ArrayList<GameOp>();
	private ArrayList<GameOp> postTurn = new ArrayList<GameOp>();
	private ArrayList<GameOp> postRound = new ArrayList<GameOp>();
	
	public void setInitializer(GameOp initializer) {
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
	
	public void addPreRoundEventHandler(GameOp op) {
		this.preRound.add(op);
	}
	
	public void addInitRoundEventHandler(GameOp op) {
		this.initRound.add(op);
	}
	
	public void addPreTurnEventHandler(GameOp op) {
		this.preTurn.add(op);
	}
	
	public void addInitTurnEventHandler(GameOp op) {
		this.initTurn.add(op);
	}
	
	public void addActionEventHandler(GameOp op) {
		this.onAction.add(op);
	}
	
	public void addPostTurnEventHandler(GameOp op) {
		this.postTurn.add(op);
	}
	
	public void addPostRoundEventHandler(GameOp op) {
		this.postRound.add(op);
	}
	
	public void run() {
		Game game = new Game();
		game.setScanner(new Scanner(System.in));
		game.setOutStream(System.out);
		
		MutableControlSet controls = new MutableControlSet();
		controls.setGameControls(new GameControls(game));
		
		this.initializer.execute(controls);
		
		while (!game.getExitFlag()) {
			// execute round
			executeRound(game, controls);
		}
	}
	
	private void executeRound(Game game, MutableControlSet controls) {
		
		for (GameOp op : this.preRound) {
			op.execute(controls);
		}
		
		// create round
		Round round = new Round();
		game.setRound(round);
		controls.setRoundControls(new RoundControls(round));
		
		for (GameOp op : this.initRound) {
			op.execute(controls);
		}
		
		while (!game.getExitFlag() && game.getRound().getIndex() < game.getPlayers().size()) {
			
			LGObject player = game.getPlayers().get(game.getRound().getIndex());
			executeTurn(player, game, controls);
			game.getRound().incrementIndex();

		}
		
		for (GameOp op : this.postRound) {
			op.execute(controls);
		}
		
		// delete round
		game.setRound(null);
		controls.setRoundControls(null);
	}
	
	private TurnResultCode executeTurn(LGObject player, Game game, MutableControlSet controls) {
		
		for (GameOp op : this.preTurn) {
			op.execute(controls);
		}
		
		// create turn
		Turn turn = new Turn();
		game.setTurn(turn);
		controls.setTurnControls(new TurnControls(turn));
		
		for (GameOp op : this.initTurn) {
			op.execute(controls);
		}
		
		String ctrlName = Constants.DEFAULT_CTRL;
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
			Controller ctrl = this.controllerProvider.getController(ctrlName, player, controls);
			
			// init phase
			TurnCommand tcmd = ctrl.init();
			
			if (tcmd instanceof CompleteTurn) {
				code = TurnResultCode.TurnFinished;
				break;
			} else if (tcmd instanceof Interrupt) {
				code = TurnResultCode.TurnCancelled;
				break;
			} else if (tcmd instanceof RepeatController) {
				throw new IllegalStateException("Cannot use RepeatController command during init phase.");
			} else if (tcmd instanceof ChangeController) {
				ChangeController changeCmd = (ChangeController)tcmd;
				ctrlName = changeCmd.getControllerName();
				continue;
			}
			
			// clear tracked names because we are exiting init phase
			trackedControllerNames.clear();
			
			// get decision
			Decision decision = ctrl.getDecision();
			
			// get action
			Action action = ctrl.resolveActionName(actionProvider, decision);
			
			// execute action
			ActionResult actionResult = ctrl.executeAction(action, decision.getData());
			
			for (GameOp op : this.onAction) {
				op.execute(controls);
			}
			
			// post execute
			ctrl.onExecute(actionResult);
			
			// check if turn is ending
			tcmd = ctrl.close(actionResult);
			if (tcmd instanceof CompleteTurn){
				code = TurnResultCode.TurnFinished;
			} else if (tcmd instanceof Interrupt) {
				code = TurnResultCode.TurnCancelled;
			} else if (tcmd instanceof ChangeController) {
				ctrlName = ((ChangeController)tcmd).getControllerName();
			}
			
		} while (code != TurnResultCode.TurnInProgress);
		
		for (GameOp op : this.postTurn) {
			op.execute(controls);
		}
		
		// delete turn
		game.setTurn(null);
		controls.setTurnControls(null);
		return code;
	}
	
}
