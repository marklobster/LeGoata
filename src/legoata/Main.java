package legoata;

import legoata.engine.equipment.Shield;
import legoata.engine.equipment.Weapon;
import legoata.engine.game.GameRunner;
import legoata.engine.gamecharacter.CharacterInitializer;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.utils.Utils;

public class Main {
	
	public static void main(String[] args) {
		try {
			System.out.println("Hello Goats!  Let's get battlin'!");
			System.out.println();
			
			// configure game
			GameRunner game = new GameRunner();

			// build teams
			int counter = 0;
			game.addTestHero(getTestCharacter("Mark", "Aardvark", counter++));
			game.addTestHero(getTestCharacter("Matt", "Oats", counter++));
			game.addTestHero(getTestCharacter("Alfred", "Sauce", counter++));
			game.addTestHero(getTestCharacter("Princess", "Xena", counter++));
			game.addTestEnemy(getTestCharacter("Slimebag", "Fartbreath", counter++));
			game.addTestEnemy(getTestCharacter("Rusty", "Fork", counter++));
			game.addTestEnemy(getTestCharacter("Snappy", "Fingers", counter++));
			game.addTestEnemy(getTestCharacter("Grunt", "Ugly", counter++));
			
			// start game
			game.run();
			
		} catch (Exception e) {
			System.out.println("Here comes Tony Hawk gettin' some big error!");
			e.printStackTrace();
		}
	}
	
	private static GameCharacter getTestCharacter(String firstName, String lastName, int counter) {
		CharacterInitializer init = new CharacterInitializer();
		if (firstName.equals("Mark")) {
			init.setUserControlled(true);
		}
		init.setFirstName(firstName);
		init.setLastName(lastName);
		init.setAgility(getStatValue());
		init.setAwareness(getStatValue());
		init.setDexterity(getStatValue());
		init.setMaxHealth(50);
		init.setQuickness(getStatValue());
		init.setResolve(getStatValue());
		init.setStrength(getStatValue());
		if (counter++ % 2 == 0) {
			init.setShield(new Shield(4, 4, "Wood Shield", "Not for real combat."));
			init.setWeapon(new Weapon(5, false, 4, "Rusty Sword", "Not for real combat."));
		} else {
			init.setWeapon(new Weapon(6, true, 5, "Heavy Sword", "Maybe for real combat."));
		}
		init.setDefaultWeapon(new Weapon(1, false, 0, "Fists", "Punch"));
		return new GameCharacter(init);
	}
	
	private static int getStatValue() {
		return 7 + Utils.getRandom(-4, 4);
	}
}
