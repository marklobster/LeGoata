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
import legoata.engine.execute.controls.GameControls;
import legoata.engine.execute.controls.RoundControls;
import legoata.engine.execute.controls.TurnControls;
import legoata.engine.execute.provider.action.ActionProvider;
import legoata.engine.execute.provider.controller.ControllerProvider;
import legoata.engine.execute.provider.gameop.GameOpProvider;
import legoata.engine.gameop.GameOp;
import legoata.engine.model.LGObject;

public class GameRunner {
	
	private Scanner scanner = new Scanner(System.in);
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
		ArrayList<LGObject> players = new ArrayList<LGObject>();
		RoundControls[] rounds = new RoundControls[2];
		rounds[1] = new RoundControls();
		GameControls controls = new GameControls(players, rounds);
		this.initializer.execute(controls);
		while (!controls.getExitFlag()) {
			rounds[0] = rounds[1];
			rounds[1] = null;
			executeRound(controls);
		}
	}
	
	private void executeRound(GameControls controls) {
		
		while (!controls.getExitFlag() && !controls.getCurrentRound().isRoundComplete()) {
			if (controls.getCurrentRound().getIndex() >= controls.getPlayers().size()) {
				controls.getCurrentRound().finishRound();
				break;
			}
			LGObject player = controls.getPlayers().get(controls.getCurrentRound().getIndex());
			TurnControls turn = new TurnControls();
			TurnResultCode code = executeTurn(player, turn);
			
			if (code == TurnResultCode.TurnCancelled) {
				if (turn.wasExitRequested()) {
					controls.setExitFlag(true);
					break;
				}
				executeGameOp(turn.getImmediateGameOp(), controls);
			} else {
				controls.getCurrentRound().incrementIndex();
				if (turn.wasExitRequested()) {
					controls.setExitFlag(true);
					break;
				}
				String gameOp = turn.getImmediateGameOp();
				if (gameOp != null && gameOp != "") {
					executeGameOp(gameOp, controls);
				}
			}
		}
	}
	
	private TurnResultCode executeTurn(LGObject player, TurnControls controls) {
		
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
			Controller ctrl = this.controllerProvider.getController(ctrlName, scanner);
			
			// init phase
			TurnCommand tcmd = ctrl.init(player, controls);
			
			if (tcmd instanceof CompleteTurn) {
				code = TurnResultCode.TurnFinished;
				break;
			} else if (tcmd instanceof Interrupt || checkForInterrupt(controls)) {
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
			Decision decision = ctrl.getDecision(player, controls);
			
			// get action
			Action action = ctrl.resolveActionName(player, actionProvider, decision, controls);
			
			// execute action
			ActionResult actionResult = ctrl.executeAction(player, action, decision.getData(), controls);
			
			// post execute
			ctrl.onExecute(player, actionResult, controls);
//			if (checkIfTurnOver(controls)) {
//				result.setCode(
//					actionResult.getCode() == ActionResultCode.Consequential ? TurnResultCode.TurnFinished : TurnResultCode.TurnCancelled);
//				break;
//			}
			
			// check if turn is ending
			tcmd = ctrl.close(player, actionResult, controls);
			if (tcmd instanceof CompleteTurn){
				code = TurnResultCode.TurnFinished;
			} else if (tcmd instanceof Interrupt || checkForInterrupt(controls)) {
				code = TurnResultCode.TurnCancelled;
			} else if (tcmd instanceof ChangeController) {
				ctrlName = ((ChangeController)tcmd).getControllerName();
			}
			
		} while (code != TurnResultCode.TurnInProgress);
		return code;
	}
	
	private void executeGameOp(String name, GameControls controls) {
		this.gameOpProvider.getGameOp(name).execute(controls);
	}
	
	private boolean checkForInterrupt(TurnControls controls) {
		String immediateGameOp = controls.getImmediateGameOp();
		return controls.wasExitRequested() || (immediateGameOp != null && immediateGameOp != "");
	}
	
}
