package org.legoata.equipment;

import java.io.PrintStream;

import org.legoata.gamecharacter.GameCharacter;

public class HealingItem extends Item {
	
	private int healthPoints;

	public HealingItem(int weight, String name, String description, int healthPoints) {
		super(weight, name, description, TargetType.Ally, true);
		this.healthPoints = healthPoints;
	}

	@Override
	public void useItem(GameCharacter user, GameCharacter target, PrintStream out) {
		out.println(String.format("%s gains %d health.", target.getFullName(), healthPoints));
		target.gainHealth(healthPoints);
	}

}
