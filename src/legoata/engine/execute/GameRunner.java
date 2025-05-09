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
import legoata.engine.execute.controls.TurnControls;
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
				case ExitGame:
					exit = true;
					break;
				case RoundInterrupted:
					
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
			TurnControls controls = new TurnControls();
			TurnResult turnResult = executeTurn(player, controls);
			TurnResultCode code = turnResult.getCode();
			if (code == TurnResultCode.RoundFinished) {
				roundResult.setCode(RoundResultCode.RoundFinished);
				break;
			} else if (code == TurnResultCode.TurnCancelled) {
				roundResult.setCode(RoundResultCode.RoundInterrupted);
				break;
			}
		}
		return roundResult;
	}
	
	private TurnResult executeTurn(LGObject player, TurnControls controls) {
		
		TurnResult result = new TurnResult();
		result.setCode(TurnResultCode.TurnInProgress);
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
			TurnCommand tcmd = ctrl.init(player, controls);
			
			if (tcmd instanceof CompleteTurn) {
				result.setCode(TurnResultCode.TurnFinished);
				break;
			} else if (tcmd instanceof Interrupt || checkForInterrupt(controls)) {
				result.setCode(TurnResultCode.TurnCancelled);
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
			ActionResult actionResult = ctrl.executeAction(player, action, controls);
			
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
				result.setCode(TurnResultCode.TurnFinished);
			} else if (tcmd instanceof Interrupt || checkForInterrupt(controls)) {
				result.setCode(TurnResultCode.TurnCancelled);
			} else if (tcmd instanceof ChangeController) {
				ctrlName = ((ChangeController)tcmd).getControllerName();
			}
			
		} while (result.getCode() != TurnResultCode.TurnInProgress);
		return result;
	}
	
	private boolean checkForInterrupt(TurnControls controls) {
		String immediateGameOp = controls.getImmediateGameOp();
		return controls.wasExitRequested() || (immediateGameOp != null && immediateGameOp != "");
	}
	
}
