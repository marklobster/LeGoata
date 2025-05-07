package legoata.engine.execute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import legoata.engine.Constants;
import legoata.engine.action.Action;
import legoata.engine.action.ActionResult;
import legoata.engine.controller.Controller;
import legoata.engine.controller.command.ChangeController;
import legoata.engine.controller.command.CompleteTurn;
import legoata.engine.controller.command.ExitGame;
import legoata.engine.controller.command.RepeatController;
import legoata.engine.controller.command.RunGameOp;
import legoata.engine.controller.command.TurnCommand;
import legoata.engine.decision.Decision;
import legoata.engine.execute.provider.action.ActionProvider;
import legoata.engine.execute.provider.controller.ControllerProvider;
import legoata.engine.execute.provider.gameop.GameOpProvider;
import legoata.engine.gameop.GameOp;
import legoata.engine.model.LGObject;

public class GameRunner {
	
	private Scanner scanner = new Scanner(System.in);
	private GameOpProvider gameOpProvider = null;
	private ControllerProvider controllerProvider = null;
	private ActionProvider actionProvider = null;
	private ArrayList<LGObject> players = new ArrayList<LGObject>();
	
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
		boolean exit = false;
		String gameOpName = Constants.INIT_GAME_OP;
		do {
			if (gameOpName != null && gameOpName != "") {
				GameOp op = this.gameOpProvider.getGameOp(gameOpName);
				exit = op.execute();
			} else {
				RoundResult roundResult = executeRound();
				switch (roundResult.getCode()) {
				case GameOpRequested:
					gameOpName = roundResult.getGameOpName();
					break;
				case ExitGame:
					exit = true;
					break;
				case RoundFinished:
					break;
				}
			}
		} while (!exit);
	}
	
	private RoundResult executeRound() {
		RoundResult roundResult = new RoundResult();
		roundResult.setCode(RoundResultCode.RoundFinished);
		for (LGObject player : players) {
			TurnResult turnResult = executeTurn(player);
			TurnResultCode code = turnResult.getCode();
			if (code == TurnResultCode.RoundFinished) {
				roundResult.setCode(RoundResultCode.RoundFinished);
				break;
			} else if (code == TurnResultCode.ExitGame) {
				roundResult.setCode(RoundResultCode.ExitGame);
				break;
			} else if (code == TurnResultCode.GameOpRequested) {
				roundResult.setCode(RoundResultCode.GameOpRequested);
				roundResult.setGameOpName(turnResult.getMessage());
				break;
			}
		}
		return roundResult;
	}
	
	private TurnResult executeTurn(LGObject player) {
		TurnResult result = new TurnResult();
		result.setCode(TurnResultCode.TurnIncomplete);
		String ctrlName = Constants.DEFAULT_CTRL;
		ArrayList<String> trackedControllerNames = new ArrayList<String>();
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
			Controller ctrl = this.controllerProvider.getController(ctrlName, scanner);
			
			// init phase
			TurnCommand tcmd = ctrl.init(player);
			if (tcmd instanceof ChangeController) {
				ChangeController changeCmd = (ChangeController)tcmd;
				ctrlName = changeCmd.getControllerName();
				continue;
			} else if (tcmd instanceof ExitGame) {
				result.setCode(TurnResultCode.ExitGame);
				break;
			} else if (tcmd instanceof RepeatController) {
				throw new IllegalStateException("Cannot use RepeatController command during init phase.");
			} else if (tcmd instanceof CompleteTurn){
				result.setCode(TurnResultCode.TurnFinished);
				break;
			} else if (tcmd instanceof RunGameOp) {
				RunGameOp gameOpCmd = (RunGameOp)tcmd;
				result.setCode(TurnResultCode.GameOpRequested);
				result.setMessage(gameOpCmd.getGameOpName());
			}
			
			// clear tracked names because we are exiting init phase
			trackedControllerNames.clear();
			
			// get decision
			Decision decision = ctrl.getDecision();
			
			// get action
			Action action = ctrl.resolveActionName(actionProvider, ctrlName, decision);
			
			// execute action
			ActionResult actionResult = ctrl.executeAction(action);
			
			// post execute
			ctrl.onExecute(actionResult);
			
			// check if turn is ending
			tcmd = ctrl.close(actionResult);
			if (tcmd instanceof ChangeController) {
				ctrlName = ((ChangeController)tcmd).getControllerName();
			} else if (tcmd instanceof ExitGame) {
				result.setCode(TurnResultCode.ExitGame);
			} else if (tcmd instanceof CompleteTurn){
				result.setCode(TurnResultCode.TurnFinished);
			} else if (tcmd instanceof RunGameOp) {
				RunGameOp gameOpCmd = (RunGameOp)tcmd;
				result.setCode(TurnResultCode.GameOpRequested);
				result.setMessage(gameOpCmd.getGameOpName());
			}
			
		} while (result.getCode() != TurnResultCode.TurnIncomplete);
		return result;
	}
	
}
