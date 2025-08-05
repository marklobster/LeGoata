package org.legoata.samples.gofish;

import org.legoata.LGConstants;
import org.legoata.action.Action;
import org.legoata.config.GameConfig;
import org.legoata.controller.Controller;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameRunner;
import org.legoata.execute.provider.action.ActionRegistry;
import org.legoata.execute.provider.action.SingleActionProvider;
import org.legoata.execute.provider.controller.ControllerRegistry;
import org.legoata.execute.provider.controller.SingleControllerProvider;
import org.legoata.samples.gofish.action.AskForRank;
import org.legoata.samples.gofish.action.ShowStatus;
import org.legoata.samples.gofish.controller.BotController;
import org.legoata.samples.gofish.controller.DefaultController;
import org.legoata.samples.gofish.controller.UserController;
import org.legoata.samples.gofish.eventhandler.CheckForWin;
import org.legoata.samples.gofish.eventhandler.InitGame;

public class Main {

	public static void main(String[] args) {
		
		try {
			// configure game
			GameRunner game = new GameRunner();
			
			// configure default 1 action per turn; increasing action limit during a turn allows for additional actions
			GameConfig config = game.getConfig();
			config.setActionCountingEnabled(true);
			config.setDefaultActionLimit(1);
			
			// register actions
			ActionRegistry actions = new ActionRegistry();
			actions.registerAction(AskForRank.LABEL, new SingleActionProvider() {
				@Override
				public Action constructAction() {
					return new AskForRank();
				}
			});
			actions.registerAction(ShowStatus.LABEL, new SingleActionProvider() {
				@Override
				public Action constructAction() {
					return new ShowStatus();
				}
			});
			game.setActionProvider(actions);
			
			// register controllers
			ControllerRegistry controllers = new ControllerRegistry();
			controllers.registerController(LGConstants.DEFAULT_CTRL, new SingleControllerProvider() {
				@Override
				public Controller constructController(ControlSet controls) {
					return new DefaultController(controls);
				}
			});
			controllers.registerController(UserController.LABEL, new SingleControllerProvider() {
				@Override
				public Controller constructController(ControlSet controls) {
					return new UserController(controls);
				}
			});
			controllers.registerController(BotController.LABEL, new SingleControllerProvider() {
				@Override
				public Controller constructController(ControlSet controls) {
					return new BotController(controls);
				}
			});
			game.setControllerProvider(controllers);
			
			// event handlers
			game.setInitializer(new InitGame());
			game.addActionEventHandler(new CheckForWin());
			
			// start game
			game.run();
			
		} catch (Exception e) {
			System.out.println("Here comes Tony Hawk gettin' some big error!");
			e.printStackTrace();
		}

	}

}
