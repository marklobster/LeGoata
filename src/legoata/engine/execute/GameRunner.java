package legoata.engine.execute;

import java.util.ArrayList;
import java.util.Scanner;

import legoata.engine.Constants;
import legoata.engine.action.Action;
import legoata.engine.action.ActionResult;
import legoata.engine.action.ActionResultCode;
import legoata.engine.controller.Controller;
import legoata.engine.controller.command.ChangeController;
import legoata.engine.controller.command.CompleteTurn;
import legoata.engine.controller.command.ExitGame;
import legoata.engine.controller.command.Interrupt;
import legoata.engine.controller.command.RepeatController;
import legoata.engine.controller.command.TurnCommand;
import legoata.engine.decision.Decision;
import legoata.engine.execute.provider.action.ActionProvider;
import legoata.engine.execute.provider.controller.ControllerProvider;
import legoata.engine.execute.provider.gameop.GameOpProvider;
import legoata.engine.gameop.GameOp;
import legoata.engine.model.LGObject;

public class GameRunner {
	
	private GameOp initializer = null;
	private GameOpProvider gameOpProvider = null;
	private ControllerProvider controllerProvider = null;
	private ActionProvider actionProvider = null;
	
	public void setInitializer(GameOp initializer) {
		this.initializer = initializer;
	}
	
	public GameOpProvider getGameOpProvider() {
		return gameOpProvider;
	}

	public void setGameOpProvider(GameOpProvider gameOpProvider) {
		this.gameOpProvider = gameOpProvider;
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
	
	public void run() {
		Game game = new Game();
		game.setScanner(new Scanner(System.in));
		game.setOutStream(System.out);
		
		this.initializer.execute(new GameControls(game));
		
		while (!game.getExitFlag()) {
			// execute round
			executeRound(game);
		}
	}
	
	private void executeRound(Game grc) {
		
		// create round
		Round round = new Round();
		grc.setRound(round);
		GameControls gameControls = new GameControls(grc);
		
		while (!gameControls.getExitFlag() && !gameControls.getRoundControls().isRoundFinished()) {
			if (gameControls.getRoundControls().getIndex() >= gameControls.getPlayers().size()) {
				gameControls.getRoundControls().finishRound();
				break;
			}
			
			LGObject player = gameControls.getPlayers().get(gameControls.getRoundControls().getIndex());
			TurnResultCode code = executeTurn(player, grc);
			
			if (code == TurnResultCode.TurnCancelled) {
				if (gameControls.getExitFlag()) {
					break;
				}
			} else {
				gameControls.getRoundControls().incrementIndex();
				if (gameControls.getExitFlag()) {
					break;
				}
			}
		}
		
		// delete round
		grc.setRound(null);
	}
	
	private TurnResultCode executeTurn(LGObject player, Game grc) {
		
		// create turn
		grc.setTurn(new Turn());
		GameControls gameControls = new GameControls(grc);
		
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
			Controller ctrl = this.controllerProvider.getController(ctrlName, player, gameControls);
			
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
			
			// post execute
			ctrl.onExecute(actionResult);
//			if (checkIfTurnOver(controls)) {
//				result.setCode(
//					actionResult.getCode() == ActionResultCode.Consequential ? TurnResultCode.TurnFinished : TurnResultCode.TurnCancelled);
//				break;
//			}
			
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
		
		// delete turn
		grc.setTurn(null);
		return code;
	}
	
}
