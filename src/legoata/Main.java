package legoata;

import legoata.engine.execute.GameRunner;

public class Main {
	
	public static void main(String[] args) {
		try {
			System.out.println("Hello Goats!  Let's get battlin'!");
			System.out.println();
			
			// configure game
			GameRunner game = new GameRunner();
			
			// start game
			game.run();
			
		} catch (Exception e) {
			System.out.println("Here comes Tony Hawk gettin' some big error!");
			e.printStackTrace();
		}
	}

}
