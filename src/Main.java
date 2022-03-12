import java.util.ArrayList;
import java.util.Random;

import Engine.Equipment.Shield;
import Engine.Equipment.Weapon;
import Engine.GameCharacter.CharacterInitializer;
import Engine.GameCharacter.GameCharacter;

public class Main {
	
	private static int counter = 0;

	public static void main(String[] args) {
		System.out.print("Hello Goats!");
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
		return new GameCharacter(init);
	}
	
	private static int getStatValue() {
		Random rand = new Random();
		return rand.nextInt(11) + 4;
	}
}
