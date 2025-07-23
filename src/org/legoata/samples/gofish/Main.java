package org.legoata.samples.gofish;

import org.legoata.event.GameCycleEvent;
import org.legoata.event.GameCycleEventHandler;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameRunner;
import org.legoata.samples.gofish.eventhandler.InitGame;

public class Main {

	public static void main(String[] args) {
		
		try {
			// configure game
			GameRunner game = new GameRunner();
			game.setInitializer(new InitGame());
			game.addPreRoundEventHandler(new GameCycleEventHandler() {

				@Override
				public void consume(GameCycleEvent event, ControlSet controls) {
					controls.getGameControls().getOutStream().print("Exiting game before first round starts!");
					controls.getGameControls().setExitFlag(true);
				}
				
			});
			
			// start game
			game.run();
			
		} catch (Exception e) {
			System.out.println("Here comes Tony Hawk gettin' some big error!");
			e.printStackTrace();
		}

	}

}
