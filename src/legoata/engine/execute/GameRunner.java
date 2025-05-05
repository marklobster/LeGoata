package legoata.engine.execute;

import java.util.Scanner;

import legoata.engine.Constants;
import legoata.engine.controller.Controller;
import legoata.engine.execute.provider.action.ActionProvider;
import legoata.engine.execute.provider.controller.ControllerProvider;
import legoata.engine.execute.provider.gameop.GameOpProvider;
import legoata.engine.gameop.GameOp;

public class GameRunner {
	
	private Scanner scanner = new Scanner(System.in);
	private GameOpProvider gameOpProvider = null;
	private ControllerProvider controllerProvider = null;
	private ActionProvider actionProvider = null;
	
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
		GameOp init = this.gameOpProvider.getGameOp(Constants.INIT_GAME_OP);
		init.execute();
	}

}
