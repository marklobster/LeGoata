package legoata;
import java.util.ArrayList;
import java.util.Random;

import legoata.engine.equipment.Shield;
import legoata.engine.equipment.Weapon;
import legoata.engine.gamecharacter.CharacterInitializer;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.incident.Battle;
import legoata.engine.utils.Utils;

public class Main {
	
	private static int counter = 0;

	public static void main(String[] args) {
		
		System.out.println("Hello Goats!  Let's get battlin'!");
		
		// build teams
		ArrayList<GameCharacter> goodies = new ArrayList<GameCharacter>();
		goodies.add(getTestCharacter("Mark", "Aardvark"));
		goodies.add(getTestCharacter("Matt", "Oats"));
		goodies.add(getTestCharacter("Alfred", "Sauce"));
		goodies.add(getTestCharacter("Princess", "Xena"));
		ArrayList<GameCharacter> baddies = new ArrayList<GameCharacter>();
		baddies.add(getTestCharacter("Slimebag", "Fartbreath"));
		baddies.add(getTestCharacter("Rusty", "Fork"));
		baddies.add(getTestCharacter("Snappy", "Fingers"));
		baddies.add(getTestCharacter("Grunt", "Ugly"));
		
		Battle battle = new Battle(goodies, baddies);
		try {
			battle.run();
			System.out.println("");
			System.out.println("The stats:");
			System.out.println("");
			for (GameCharacter gc : goodies) {
				gc.printStats(System.out);
			}
			for (GameCharacter gc : baddies) {
				gc.printStats(System.out);
			}
		} catch (Exception e) {
			System.out.println("Here comes Tony Hawk gettin' some big error!");
			e.printStackTrace();
		}
	}

	private static GameCharacter getTestCharacter(String firstName, String lastName) {
		CharacterInitializer init = new CharacterInitializer();
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
