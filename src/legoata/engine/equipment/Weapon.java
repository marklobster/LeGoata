package legoata.engine.equipment;

import java.io.PrintStream;

import legoata.engine.gamecharacter.GameCharacter;

public class Weapon extends Item {

	public Weapon(int power, boolean twoHanded, int weight, String name, String description) {
		super(weight, name, description, TargetType.Self, false);
		this.power = power;
	}
	
	private int power;
	private boolean twoHanded;
	
	public int getPower() {
		return power;
	}
	
	public boolean isTwoHanded() {
		return twoHanded;
	}

	@Override
	public void useItem(GameCharacter user, GameCharacter target, PrintStream out) {
		if (!user.getEquipment().contains(this)) {
			throw new IllegalArgumentException("GameCharacter does not possess Weapon.");
		}
		if (user.equipWeapon(this)) {
			out.println(this.getName() + " equipped!");
		} else {
			out.println("Unable to equip weapon.");
		}
	}
	
}
